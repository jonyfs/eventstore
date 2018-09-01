package br.com.jonyfs.event;

import java.util.Iterator;

public class EventIteratorImpl implements EventIterator {

    Iterator<Event> events;

    Event current;

    public EventIteratorImpl(Iterator<Event> events) {
        this.events = events;
    }

    @Override
    public boolean moveNext() {
        if (events.hasNext()) {
            current = events.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Event current() {
        return this.current;
    }

    @Override
    public void remove() {
        events.remove();
    }

    @Override
    public void close() throws Exception {
        events = null;
    }
}
