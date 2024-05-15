package kz.busnet.busnetserver.busshedule;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusScheduleDTO {
    private LocalDateTime departTime;
    private LocalDateTime arrivalTime;
    private Long departStationId;
    private Long arrivalStationId;
    private Long busId;
    private Long busCompanyId;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double price;


}
