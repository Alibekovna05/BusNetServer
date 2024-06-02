package kz.busnet.busnetserver.busproviders;

import jakarta.persistence.*;
import kz.busnet.busnetserver.booking.Booking;
import kz.busnet.busnetserver.busshedule.BusSchedule;
import kz.busnet.busnetserver.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bus_company")
@EntityListeners(AuditingEntityListener.class)
public class BusCompany {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(unique = true)
    private String email;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @ManyToMany
    @JoinTable(
            name = "bus_provider_user",
            joinColumns = @JoinColumn(name = "bus_provider_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> managers;

    @OneToMany(mappedBy = "busCompany")
    private List<BusSchedule> busScheduleList;

}
