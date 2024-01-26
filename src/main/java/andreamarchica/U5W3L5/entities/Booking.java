package andreamarchica.U5W3L5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate bookingDate;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
