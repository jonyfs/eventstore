package br.com.jonyfs.event;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Event implements Serializable {

    private static final long serialVersionUID = -2374058943929395722L;

    private String type;

    private LocalDateTime moment;

}
