package br.com.jonyfs.event;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends PagingAndSortingRepository<Event, String> {

    Event findFirstByType(String type);

    List<Event> deleteByType(String type);

    Iterator<Event> findByTypeAndMomentBetween(String type, Date start, Date stop);

    @Query("select e from Event e where e.type = :type and e.moment between :start and :end")
    Iterator<Event> findAll(@Param("type") String type, @Param("start") Date start, @Param("end") Date end);

}
