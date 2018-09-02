package br.com.jonyfs.event;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Event {

    private String type;

    private LocalDateTime moment;

}
