package andreamarchica.U5W3L5.services;

import andreamarchica.U5W3L5.entities.Booking;
import andreamarchica.U5W3L5.entities.Event;
import andreamarchica.U5W3L5.entities.User;
import andreamarchica.U5W3L5.exceptions.BadRequestException;
import andreamarchica.U5W3L5.exceptions.NotFoundException;
import andreamarchica.U5W3L5.payloads.bookings.NewBookingDTO;
import andreamarchica.U5W3L5.repositories.BookingsRepository;
import andreamarchica.U5W3L5.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingsService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private BookingsRepository bookingsRepository;
    @Autowired
    private EventsRepository eventsRepository;
    public Page<Booking> getUsers(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return bookingsRepository.findAll(pageable);
    }
    public Booking save(NewBookingDTO body) throws IOException {
        Event event = eventsRepository.findByTitle(body.getTitle())
                .orElseThrow(() -> new NotFoundException("Evento non trovato con il titolo: " + body.getTitle()));
        if (bookingsRepository.existsByEvent(event)) {
            throw new BadRequestException("L'evento " + body.getTitle() + " è già stato inserito");
        }

        Booking newBooking = new Booking();
        newBooking.setEvent(event);

        //Trovata palesemente in rete dopo un pomeriggio di bestemmie
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            newBooking.setUser(currentUser);
        } else newBooking.setUser(null);
        newBooking.setBookingDate(LocalDate.now());
        return bookingsRepository.save(newBooking);
    }

    public Booking findById(UUID id) {
        return bookingsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public void findByIdAndDelete(UUID id) {
        Booking found = this.findById(id);
        bookingsRepository.delete(found);
    }
    public Booking findByIdAndUpdate(UUID id, Booking body) {

        Booking found = this.findById(id);
        found.setBookingDate(body.getBookingDate());
        found.setEvent(body.getEvent());
        found.setUser(body.getUser());
        return bookingsRepository.save(found);
    }
}
