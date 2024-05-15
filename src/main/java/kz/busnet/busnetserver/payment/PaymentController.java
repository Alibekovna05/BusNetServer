package kz.busnet.busnetserver.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
}
