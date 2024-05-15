package kz.busnet.busnetserver.busproviders;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kz.busnet.busnetserver.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusCompanyRequest {

        Long id;
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String name;

        String address;
        @Email(message = "Email is not well formatted")
        @NotEmpty(message = "Email is mandatory")
        @NotNull(message = "Email is mandatory")
        String email;

        User  owner;

        String contactNumber;

        String description;


}
