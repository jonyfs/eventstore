package br.com.jonyfs;

import br.com.jonyfs.event.Event;
import br.com.jonyfs.event.EventIterator;
import br.com.jonyfs.event.EventRepository;
import br.com.jonyfs.event.EventStore;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinitionBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventStore eventStore;

    @Test
    public void givenEvent_whenSaveItAndFindIt_thenSameEvent() {

        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .dateRange(LocalDate.now().minusDays(1), LocalDate.now())
            .exclude(FieldDefinitionBuilder.field().named("id").get())
            .exclude(Serializable.class)
            .randomize(FieldDefinitionBuilder.field().named("type").ofType(String.class).get(), new EventTypeRandomizer())
            .build();

        LocalDateTime now = LocalDateTime.now();
        Event event = enhancedRandom.nextObject(Event.class);

        eventStore.insert(event);

        Event savedEvent = eventRepository.findFirstByType(event.getType());

        assertThat(savedEvent).isNotNull();
        assertThat(event.getMoment()).isEqualTo(savedEvent.getMoment());
        assertThat(event.getType()).isEqualTo(savedEvent.getType());
    }

    @Transactional
    @Test
    public void givenSomeEvents_whenDeleteAllEventsByType_thenOnlyOthers() {

        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .dateRange(LocalDate.now().minusDays(1), LocalDate.now())
            .exclude(FieldDefinitionBuilder.field().named("id").get())
            .exclude(Serializable.class)
            .randomize(FieldDefinitionBuilder.field().named("type").ofType(String.class).get(), new EventTypeRandomizer())
            .build();

        for (int i = 0; i < 100; i++) {
            eventStore.insert(enhancedRandom.nextObject(Event.class));
        }

        assertThat(eventRepository.count()).isEqualTo(100);

        eventStore.removeAll(EventType.CHANGE_STATUS);

        assertThat(eventRepository.count()).isLessThan(100);

    }

    @Transactional
    @Test
    public void givenALargeDatabaseEvents_whenDeleteAllEventsByType_thenOnlyOthers2() {

        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .dateRange(LocalDate.now().minusDays(1), LocalDate.now())
            .exclude(FieldDefinitionBuilder.field().named("id").get())
            .exclude(Serializable.class)
            .randomize(FieldDefinitionBuilder.field().named("type").ofType(String.class).get(), new EventTypeRandomizer())
            .build();

        Stream<Event> events = enhancedRandom.objects(Event.class, 10000);

        events.forEach(event -> eventStore.insert(event));

        assertThat(eventRepository.count()).isEqualTo(10000);

        EventIterator eventIterator = eventStore.query(EventType.LOCK_STATUS, Date.from(LocalDateTime.now().minusHours(12).atZone(ZoneId.systemDefault()).toInstant()), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

    }
}
