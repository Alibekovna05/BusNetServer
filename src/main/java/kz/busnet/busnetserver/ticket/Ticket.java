package kz.busnet.busnetserver.ticket;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passengerName;
    private String source;
    private String destination;

    @Column(length = 2048) // Increase length to accommodate longer QR codes
    private String qrCode;
    // Getters and Setters
}
