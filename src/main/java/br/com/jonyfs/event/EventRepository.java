package br.com.jonyfs.event;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends PagingAndSortingRepository<Event, String> {

    Event findFirstByType(String type);

    List<Event> deleteByType(String type);

    Iterator<Event> findAllByTypeAndMomentGreaterThanEqualAndMomentLessThan(String type, LocalDateTime startTime, LocalDateTime endTime);
}
