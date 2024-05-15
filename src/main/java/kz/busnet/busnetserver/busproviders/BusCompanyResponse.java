package kz.busnet.busnetserver.busproviders;


import kz.busnet.busnetserver.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusCompanyResponse {
    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private String email;
    private String owner;
}
