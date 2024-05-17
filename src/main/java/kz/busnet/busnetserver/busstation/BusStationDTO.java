package kz.busnet.busnetserver.busstation;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusStationDTO {
    private Long id;
    private String name;
    private String city;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<String> contactNumbers;
//    private byte[] photo;
    private String contactEmail;

    // Конструкторы, геттеры и сеттеры
}
