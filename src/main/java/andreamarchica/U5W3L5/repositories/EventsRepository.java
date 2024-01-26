package andreamarchica.U5W3L5.repositories;

import andreamarchica.U5W3L5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventsRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByTitle(String title);
}
