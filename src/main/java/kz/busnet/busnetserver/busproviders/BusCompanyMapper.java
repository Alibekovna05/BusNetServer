package kz.busnet.busnetserver.busproviders;

import org.springframework.stereotype.Service;

@Service
public class BusCompanyMapper {
    public BusCompany toBusCompany(BusCompanyRequest request) {
        return BusCompany.builder()
                .name(request.getName())
                .address(request.getAddress())
                .contactNumber(request.getContactNumber())
                .email(request.getEmail())
                .owner(request.getOwner())
                .build();
    }
    public BusCompanyResponse toBusCompanyResponse(BusCompany busCompany) {
        return BusCompanyResponse.builder()
                .id(busCompany.getId())
                .name(busCompany.getName())
                .email(busCompany.getEmail())
                .address(busCompany.getAddress())
                .contactNumber(busCompany.getContactNumber())
                .owner(busCompany.getOwner().fullName())
                .build();
    }

}
