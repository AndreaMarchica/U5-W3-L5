package andreamarchica.U5W3L5.controllers;

import andreamarchica.U5W3L5.entities.Booking;
import andreamarchica.U5W3L5.exceptions.BadRequestException;
import andreamarchica.U5W3L5.payloads.bookings.NewBookingDTO;
import andreamarchica.U5W3L5.payloads.bookings.NewBookingResponseDTO;
import andreamarchica.U5W3L5.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    @Autowired
    BookingsService bookingsService;

    @PostMapping("/getbooking")
    @ResponseStatus(HttpStatus.CREATED)
    public NewBookingResponseDTO getBooking(@RequestBody @Validated NewBookingDTO body, BindingResult validation) throws Exception {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Booking newBooking = bookingsService.save(body);
        return new NewBookingResponseDTO(newBooking.getId());
    }
}
