package kz.busnet.busnetserver.booking;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long busScheduleId;
    private String passengerName;
    private String passengerEmail;
    private String passengerLastName;
}
