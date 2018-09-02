package br.com.jonyfs;

import br.com.jonyfs.event.Event;
import br.com.jonyfs.event.EventRepository;
import br.com.jonyfs.event.EventStore;
import java.time.LocalDateTime;
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
        LocalDateTime now = LocalDateTime.now();
        Event event = new Event("some_type", now);

        eventStore.insert(event);

        Event savedEvent = eventRepository.findFirstByType(event.getType());

        assertThat(savedEvent).isNotNull();
        assertThat(event.getMoment()).isEqualTo(savedEvent.getMoment());
        assertThat(event.getType()).isEqualTo(savedEvent.getType());
    }

    @Transactional
    @Test
    public void givenSomeEvents_whenDeleteAllEventsByType_thenOnlyOthers() {
        String eventReadStatus = "READ_STATUS";
        String eventChangeStatus = "CHANGE_STATUS";
        String eventLockStatus = "LOCK_STATUS";


        eventStore.insert(Event.builder().type(eventReadStatus).moment(LocalDateTime.now()).build());
        eventStore.insert(Event.builder().type(eventReadStatus).moment(LocalDateTime.now().minusDays(1)).build());
        eventStore.insert(Event.builder().type(eventReadStatus).moment(LocalDateTime.now().minusHours(3)).build());

        eventStore.insert(Event.builder().type(eventChangeStatus).moment(LocalDateTime.now().minusHours(1)).build());
        eventStore.insert(Event.builder().type(eventChangeStatus).moment(LocalDateTime.now().minusMonths(3)).build());

        eventStore.insert(Event.builder().type(eventLockStatus).moment(LocalDateTime.now().minusHours(12)).build());

        assertThat(eventRepository.count()).isEqualTo(6);

        eventStore.removeAll(eventReadStatus);

        assertThat(eventRepository.count()).isEqualTo(3);

    }
}
