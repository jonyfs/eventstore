package br.com.jonyfs.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventStoreImpl implements EventStore {

    // Creating a thread-safe Event List
    @Getter
    final List<Event> events = Collections.synchronizedList(new ArrayList());

    @Override
    public void insert(Event event) {
        LOGGER.debug("Inserting {}...", event);
        events.add(event);
        LOGGER.debug("Inserting {} OK!", event);
    }

    @Override
    public void removeAll(String type) {
        LOGGER.debug("Searching by event type {}...", type);
        int size = events.size();
        // Using Collection.removeIf, java 8 solution, to remove all references equals type
        // The iime complexity is O(n)
        events.removeIf(event -> event.getType().equals(type));
        LOGGER.debug("{} event are removed.", size - events.size());
    }

    @Override
    public EventIterator query(String type, LocalDateTime startTime, LocalDateTime endTime) {
        LOGGER.debug("Searching by event type {} and moment between {} and {}...", type, startTime, endTime);

        // Using Streams to filter by fields
        return new EventIteratorImpl(events
            .stream()
            .filter(event -> event.getType().equals(type))
            .filter(event -> event.getMoment().isEqual(startTime) || event.getMoment().isAfter(startTime))
            .filter(event -> event.getMoment().isBefore(endTime))
            .iterator());
    }
}
