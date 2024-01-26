package andreamarchica.U5W3L5.controllers;

import andreamarchica.U5W3L5.entities.Booking;
import andreamarchica.U5W3L5.entities.Event;
import andreamarchica.U5W3L5.entities.User;
import andreamarchica.U5W3L5.exceptions.BadRequestException;
import andreamarchica.U5W3L5.payloads.bookings.NewBookingDTO;
import andreamarchica.U5W3L5.payloads.bookings.NewBookingResponseDTO;
import andreamarchica.U5W3L5.payloads.events.NewEventDTO;
import andreamarchica.U5W3L5.payloads.events.NewEventResponseDTO;
import andreamarchica.U5W3L5.services.BookingsService;
import andreamarchica.U5W3L5.services.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    EventsService eventsService;
    @Autowired
    BookingsService bookingsService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventResponseDTO saveEvent(@RequestBody @Validated NewEventDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Event newEvent = eventsService.save(body);
        return new NewEventResponseDTO(newEvent.getId());
    }
    @GetMapping("")
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return eventsService.getEvents(page, size, sortBy);
    }
    @GetMapping("/{eventId}")
    public Event findById(@PathVariable UUID eventId) {
        return eventsService.findById(eventId);
    }
    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event findAndUpdate(@PathVariable UUID eventId, @RequestBody Event body) {
        return eventsService.findByIdAndUpdate(eventId, body);
    }
    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID eventId) {
        eventsService.findByIdAndDelete(eventId);
    }
    @PatchMapping("/{eventId}/eventimage")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event uploadEventImage(@RequestParam("eventimage") MultipartFile file, @PathVariable UUID eventId) {
        try {
            return eventsService.uploadEventImage(eventId, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*@PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public NewBookingResponseDTO getBooking(@RequestBody @Validated NewBookingDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Booking newBooking = bookingsService.save(body);
        return new NewBookingResponseDTO(newBooking.getId());
    }*/

}

