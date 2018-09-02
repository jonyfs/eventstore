package br.com.jonyfs.event;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends PagingAndSortingRepository<Event, String> {

    Event findFirstByType(String type);
}
