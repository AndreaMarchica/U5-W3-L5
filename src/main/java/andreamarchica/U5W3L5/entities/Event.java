package andreamarchica.U5W3L5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private String location;
    private int availablePlaces;
    private String eventImage;
    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Booking> bookingList;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

}
