package Repositories;

import entity.Event;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */
public interface EventRepository extends CrudRepository<Event,Long> {
}
