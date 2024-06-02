package kz.busnet.busnetserver.booking;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.persistence.EntityNotFoundException;
import kz.busnet.busnetserver.busshedule.BusScheduleRepository;

import kz.busnet.busnetserver.payment.PaymentService;
import kz.busnet.busnetserver.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final String imagePath = "src/main/resources/qrcodes/QRCode.png";

    private final BookingRepository bookingRepository;
    private final BusScheduleRepository busScheduleRepository;
    private final BookingMapper bookingMapper;
    private final PaymentService paymentService;
    @Qualifier("taskScheduler")
    private final TaskScheduler taskScheduler;
    public List<BookingDTO> findAllMyBookings(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        List<Booking> booking = bookingRepository.findByUser_Id(user.getId());
        List<BookingDTO> bookingResponses = booking.stream()
                .map(bookingMapper::toDto)
                .toList();
        return bookingResponses;

    }
    public BookingDTO createMyBooking(BookingRequest bookingRequest, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Booking booking = new Booking();
        booking.setBusSchedule(busScheduleRepository.findById(bookingRequest.getBusScheduleId()).orElseThrow());
        booking.setUser(user);

        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING_PAYMENT);
        generateAndSaveQRCode(booking);

        schedulePaymentCheck(booking.getId());
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }


    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking.setStatus(BookingStatus.PENDING_PAYMENT);

        generateAndSaveQRCode(booking);

        schedulePaymentCheck(booking.getId());
        booking = bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        // Update the existing booking with new details
        existingBooking = bookingMapper.toEntity(bookingDTO);
        existingBooking = bookingRepository.save(existingBooking);
        return bookingMapper.toDto(existingBooking);
    }

    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        bookingRepository.delete(booking);
    }

    public BookingDTO findBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        return bookingMapper.toDto(booking);
    }

    public List<BookingDTO> findAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public void schedulePaymentCheck(Long bookingId) {
        Runnable paymentCheckTask = () -> {
            Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
            bookingOptional.ifPresent(booking -> {
                if (booking.getStatus() == BookingStatus.PENDING_PAYMENT) {
                    booking.setStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(booking);
                }
            });
        };

        // Convert LocalDateTime to Instant with the system's default time zone
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(30);
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        Instant instantToRun = zonedDateTime.toInstant();

        // Schedule the task to run after 30 minutes
        taskScheduler.schedule(paymentCheckTask, instantToRun);
    }
    public BookingDTO returnTicket(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            booking.setStatus(BookingStatus.CANCELLED);
            booking = bookingRepository.save(booking);
            // Handle the business logic for ticket return, like refunding the payment
            paymentService.refundPayment(bookingId);
        } else {
            throw new IllegalStateException("Ticket cannot be returned");
        }
        return bookingMapper.toDto(booking);
    }

    public List<BookingDTO> getBookingsByUser(Integer userId) {
        List<Booking> bookings = bookingRepository.findByUser_Id(userId);
        return bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    // Method to generate QR code

    public void generateAndSaveQRCode(Booking booking) {
        String qrCodeData = UUID.randomUUID().toString();
        try {
            byte[] qrCodeImage = generateQRCodeImage(qrCodeData, 250, 250);
            booking.setQrCodeData(qrCodeData);
            booking.setQrCodeDataImg(qrCodeImage);
            bookingRepository.save(booking);
        } catch (WriterException | IOException e) {
            log.error("Error generating QR code for booking: {}", booking.getId(), e);

        }
    }

    private byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        }
    }


}
