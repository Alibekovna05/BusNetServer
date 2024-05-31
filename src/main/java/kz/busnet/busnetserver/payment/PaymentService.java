package kz.busnet.busnetserver.payment;

import kz.busnet.busnetserver.payment.PaymentRepository;
import kz.busnet.busnetserver.payment.PaymentStatus;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import kz.busnet.busnetserver.booking.Booking;
import kz.busnet.busnetserver.booking.BookingRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentDTO processPayment(Long bookingId, BigDecimal amount) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);
        payment = paymentRepository.save(payment);
        payment.setStatus(PaymentStatus.COMPLETED);
        payment = paymentRepository.save(payment);

        return mapToDTO(payment);
    }

    public PaymentDTO confirmPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.COMPLETED);
        payment = paymentRepository.save(payment);

        return mapToDTO(payment);
    }

    public PaymentDTO refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.REFUNDED);
        payment = paymentRepository.save(payment);

        return mapToDTO(payment);
    }

    public PaymentDTO getPaymentDetails(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        return mapToDTO(payment);
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PaymentDTO updatePayment(Long paymentId, PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        payment.setAmount(paymentDTO.getAmount());
        payment.setStatus(paymentDTO.getStatus());
        payment = paymentRepository.save(payment);

        return mapToDTO(payment);
    }

    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        paymentRepository.delete(payment);
    }

    private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setBookingId(payment.getBooking().getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setStatus(payment.getStatus());
        return dto;
    }

    private Payment mapToEntity(PaymentDTO paymentDto) {
        Payment payment = new Payment();
        payment.setBooking(bookingRepository.findById(paymentDto.getBookingId()).orElseThrow());
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setStatus(paymentDto.getStatus());
        return payment;
    }
}
