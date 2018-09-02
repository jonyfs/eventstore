
package br.com.jonyfs.event;

import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventStoreImpl implements EventStore {

    @Autowired
    EventRepository eventRepository;

    @Override
    public void insert(Event event) {
        LOGGER.debug("Inserting {}...", event);
        event = eventRepository.save(event);
        LOGGER.debug("Inserting {} OK!", event);
    }

    @Override
    public void removeAll(String type) {
        LOGGER.debug("Searching by event type {}...", type);
        List<Event> events = eventRepository.deleteByType(type);
        LOGGER.debug("{} event are removed.", events.size());
    }

    @Override
    public EventIterator query(String type, LocalDateTime startTime, LocalDateTime endTime) {
        return new EventIteratorImpl(eventRepository.findAllByTypeAndMomentGreaterThanEqualAndMomentLessThan(type, startTime, endTime));
    }

}
