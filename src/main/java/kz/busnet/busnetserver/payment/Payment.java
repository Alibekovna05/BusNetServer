package kz.busnet.busnetserver.payment;

import jakarta.persistence.*;
import kz.busnet.busnetserver.booking.Booking;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}


/**
 * public class Payment {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Long id;
 *
 *     private String name;
 *     private String number;
 *     private String email;
 *     private String address;
 *     private int billValue;
 *     private String cardNumber;
 *     private String cardHolder;
 *     private String dateValue;
 *     private String cvc;
 * }
 */