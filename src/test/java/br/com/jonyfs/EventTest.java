package br.com.jonyfs;

import br.com.jonyfs.event.Event;
import br.com.jonyfs.event.EventIterator;
import br.com.jonyfs.event.EventStore;
import br.com.jonyfs.event.EventStoreImpl;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinitionBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

@Slf4j
public class EventTest {

    @Test
    public void givenEvent_whenSaveItAndFindIt_thenSameEvent() {

        EventStore eventStore = new EventStoreImpl();

        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .dateRange(LocalDate.now().minusDays(1), LocalDate.now())
            .exclude(FieldDefinitionBuilder.field().named("id").get())
            .exclude(Serializable.class)
            .randomize(FieldDefinitionBuilder.field().named("type").ofType(String.class).get(), new EventTypeRandomizer())
            .build();

        LocalDateTime now = LocalDateTime.now();
        Event event = enhancedRandom.nextObject(Event.class);

        eventStore.insert(event);

        assertThat(eventStore.getEvents()).isNotNull();

        Event savedEvent = eventStore.getEvents().get(0);

        assertThat(savedEvent).isNotNull();
        assertThat(event.getMoment()).isEqualTo(savedEvent.getMoment());
        assertThat(event.getType()).isEqualTo(savedEvent.getType());
    }

    @Test
    public void givenSomeEvents_whenDeleteAllEventsByType_thenOnlyOthers() {

        EventStore eventStore = new EventStoreImpl();

        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .dateRange(LocalDate.now().minusDays(1), LocalDate.now())
            .exclude(FieldDefinitionBuilder.field().named("id").get())
            .exclude(Serializable.class)
            .randomize(FieldDefinitionBuilder.field().named("type").ofType(String.class).get(), new EventTypeRandomizer())
            .build();

        for (int i = 0; i < 100; i++) {
            eventStore.insert(enhancedRandom.nextObject(Event.class));
        }

        assertThat(eventStore.getEvents().size()).isEqualTo(100);

        eventStore.removeAll(EventType.CHANGE_STATUS);

        assertThat(eventStore.getEvents().size()).isLessThan(100);

    }

    @Test
    public void givenALargeDatabaseEvents_whenSearchEventsByTypeBetweenDates_thenEventIteratorIsNotNullAndIsPossibleRemoveElements() throws Exception {

        EventStore eventStore = new EventStoreImpl();

        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .dateRange(LocalDate.now().minusDays(1), LocalDate.now())
            .exclude(FieldDefinitionBuilder.field().named("id").get())
            .exclude(Serializable.class)
            .randomize(FieldDefinitionBuilder.field().named("type").ofType(String.class).get(), new EventTypeRandomizer())
            .build();

        Stream<Event> events = enhancedRandom.objects(Event.class, 10000);

        events.forEach(event -> eventStore.insert(event));

        assertThat(eventStore.getEvents()).isNotNull();

        assertThat(eventStore.getEvents().size()).isEqualTo(10000);

        EventIterator eventIterator = eventStore.query(EventType.LOCK_STATUS, LocalDateTime.now().minusHours(12), LocalDateTime.now());

        assertThat(eventIterator).isNotNull();

        while (eventIterator.moveNext()) {
            Event event = eventIterator.current();
            LOGGER.debug("Removing {}...", event);
            eventIterator.remove();
        }

        eventIterator.close();

        eventIterator.remove();

        assertThat(eventIterator.moveNext()).isFalse();

    }
}
