
package br.com.jonyfs.event;

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
        eventRepository.deleteAll();
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
