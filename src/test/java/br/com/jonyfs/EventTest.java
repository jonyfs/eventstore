package br.com.jonyfs;

import br.com.jonyfs.event.Event;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EventTest {
    @Test
    public void thisIsAWarning() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Event event = new Event("some_type", now);

        //THIS IS A WARNING:
        //Some of us (not everyone) are coverage freaks.
        assertEquals(now, event.getMoment());
        assertEquals("some_type", event.getType());
    }
}