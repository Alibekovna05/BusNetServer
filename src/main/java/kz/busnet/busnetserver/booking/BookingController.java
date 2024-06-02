package kz.busnet.busnetserver.booking;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bookings")
@RequiredArgsConstructor
@Tag(name = "Booking")
public class BookingController {

    private final BookingService bookingService;
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUser(@PathVariable Integer userId) {
        List<BookingDTO> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }



    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingDTO>> getBookingsByUser(Authentication connectedUser) {
        List<BookingDTO> bookings = bookingService.findAllMyBookings(connectedUser);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/make-booking")
    public ResponseEntity<BookingDTO> makeBooking(@RequestBody BookingRequest bookingRequest, Authentication connectedUser) {
        BookingDTO createdBooking = bookingService.createMyBooking(bookingRequest, connectedUser);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping("/cancel-booking")
    public ResponseEntity<BookingDTO> cancelBooking(@RequestParam Long bookingId) {
        BookingDTO bookings = bookingService.returnTicket(bookingId);
        return ResponseEntity.ok(bookings);
    }


    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) throws Exception {
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(createdBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.findBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.findAllBookings();
        return ResponseEntity.ok(bookings);
    }


    @PostMapping("/{id}/return")
    public ResponseEntity<BookingDTO> returnTicket(@PathVariable Long id) {
        BookingDTO returnedBooking = bookingService.returnTicket(id);
        return ResponseEntity.ok(returnedBooking);
    }


}
