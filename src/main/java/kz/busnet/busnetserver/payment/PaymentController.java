package kz.busnet.busnetserver.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentDTO> processPayment(@RequestParam Long bookingId, @RequestParam BigDecimal amount) {
        PaymentDTO payment = paymentService.processPayment(bookingId, amount);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentDTO> confirmPayment(@RequestParam Long paymentId) {
        PaymentDTO paymentDTO = paymentService.confirmPayment(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }

    @PostMapping("/refund")
    public ResponseEntity<PaymentDTO> refundPayment(@RequestParam Long paymentId) {
        PaymentDTO paymentDTO = paymentService.refundPayment(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentDetails(@PathVariable Long paymentId) {
        PaymentDTO paymentDTO = paymentService.getPaymentDetails(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }
    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> processPayment(@RequestParam Long bookingId, @RequestParam BigDecimal amount,
                                                     @RequestParam PaymentStatus status) {
        PaymentDTO payment = paymentService.createPayment(bookingId, amount, status);
        return ResponseEntity.ok(payment);
    }
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long paymentId, @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updatedPayment = paymentService.updatePayment(paymentId, paymentDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}
