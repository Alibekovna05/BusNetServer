package kz.busnet.busnetserver.busshedule;

import jakarta.persistence.*;
import kz.busnet.busnetserver.bus.Bus;
import kz.busnet.busnetserver.busproviders.BusCompany;
import kz.busnet.busnetserver.busstation.BusStation;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "bus_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime departTime;

    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "depart_station_id", nullable = false)
    private BusStation departStation;

    @ManyToOne
    @JoinColumn(name = "arrival_station_id", nullable = false)
    private BusStation arrivalStation;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "bus_company_id")
    private BusCompany busCompany;

    private Integer totalSeats;
    private Integer availableSeats;

    private Double price;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;


    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "operational_days", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> operationalDays;

    public boolean isScheduledForToday() {
        return operationalDays.contains(LocalDateTime.now().getDayOfWeek());
    }

    public boolean isInTransit() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(departTime) && now.isBefore(arrivalTime);
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        } else {
            throw new IllegalStateException("No available seats");
        }
    }

    public void releaseSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
        } else {
            throw new IllegalStateException("All seats are already available");
        }
    }

    public boolean isUpcoming() {
        return LocalDateTime.now().isBefore(departTime);
    }

    public boolean isCompleted() {
        return LocalDateTime.now().isAfter(arrivalTime);
    }

    public Duration getTripDuration() {
        return Duration.between(departTime, arrivalTime);
    }

    public void updateStatus(ScheduleStatus newStatus) {
        this.status = newStatus;
    }
}

