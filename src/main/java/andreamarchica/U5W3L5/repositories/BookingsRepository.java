package andreamarchica.U5W3L5.repositories;

import andreamarchica.U5W3L5.entities.Booking;
import andreamarchica.U5W3L5.entities.Event;
import andreamarchica.U5W3L5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingsRepository extends JpaRepository <Booking, UUID> {
    List<Booking> findByUser(User user);
    boolean existsByEvent(Event event);
    Optional<Booking> findByEvent(Event event);
}
