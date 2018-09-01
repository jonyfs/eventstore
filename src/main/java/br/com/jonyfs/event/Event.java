package br.com.jonyfs.event;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends AbstractPersistable implements Serializable {

    private static final long serialVersionUID = -2374058943929395722L;

    private String type;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime moment;

}
