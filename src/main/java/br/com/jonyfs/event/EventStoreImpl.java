
package br.com.jonyfs.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventStoreImpl implements EventStore {

    @Autowired
    EventRepository eventRepository;

    @Override
    public void insert(Event event) {
        eventRepository.save(event);
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
