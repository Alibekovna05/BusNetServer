package kz.busnet.busnetserver.busstation;

import jakarta.persistence.*;
import kz.busnet.busnetserver.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bus_stations")
public class BusStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city")
    private String city;
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ElementCollection
    @CollectionTable(name = "bus_station_contact_numbers", joinColumns = @JoinColumn(name = "bus_station_id"))
    @Column(name = "contact_number")
    private List<String> contactNumbers;

//    @Lob
//    @Column(name = "photo")
//    private byte[] photo;

    @Column(name = "contact_email")
    private String contactEmail;
}
