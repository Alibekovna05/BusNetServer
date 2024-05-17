package kz.busnet.busnetserver.busshedule;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import kz.busnet.busnetserver.booking.Booking;
import kz.busnet.busnetserver.bus.Bus;
import kz.busnet.busnetserver.busproviders.BusCompany;
import kz.busnet.busnetserver.busstation.BusStation;
import kz.busnet.busnetserver.common.BaseEntity;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bus_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusSchedule  extends BaseEntity {


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
    @OneToMany(mappedBy = "busSchedule")
    private List<Booking> bookingList;
    public boolean isScheduledForToday() {
        return departTime.isEqual(LocalDateTime.now());
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

