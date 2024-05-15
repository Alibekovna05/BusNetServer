package kz.busnet.busnetserver.busshedule;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusScheduleResponse {
    private Long id;
    private LocalDateTime departTime;
    private LocalDateTime arrivalTime;
    private String departStation;
    private String arrivalStation;
    private String busNumber;
    private Long busCompanyId;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;
}
