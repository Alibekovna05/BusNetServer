package kz.busnet.busnetserver.busproviders;


import jakarta.mail.MessagingException;
import kz.busnet.busnetserver.common.PageResponse;
import kz.busnet.busnetserver.email.EmailService;
import kz.busnet.busnetserver.email.SimpleTemplate;
import kz.busnet.busnetserver.role.RoleRepository;
import kz.busnet.busnetserver.user.User;
import kz.busnet.busnetserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class BusCompanyService {

    private final BusCompanyRepository busCompanyRepository;
    private final BusCompanyMapper busCompanyMapper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public void registerBusCompany(BusCompanyRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("BUS_COMPANY")
                .orElseThrow(() -> new IllegalStateException("ROLE BUS_COMPANY was not initiated"));

        User user = User.builder()
                .firstname(request.getOwner().getFirstname())
                .lastname(request.getOwner().getLastname())
                .email(request.getOwner().getEmail())
                .password(passwordEncoder.encode(request.getOwner().getPassword()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);

        BusCompany busCompany = BusCompany.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .contactNumber(request.getContactNumber())
                .owner(user)
                .build();

        busCompanyRepository.save(busCompany);
        sendEmail(user, request.getOwner().getPassword());
    }

    private void sendEmail(User user, String password) throws MessagingException {

        emailService.sendSimpleEmail(
                user.getEmail(),
                user.getFullName(),
                SimpleTemplate.SUCCESSFUL_REGISTRATION,
                user.getEmail(),
               password,
                "Bus Company has been registered!"
        );
    }
    public PageResponse<BusCompanyResponse> findAllBusCompany(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BusCompany> busCompanies = busCompanyRepository.findAll(pageable);
        List<BusCompanyResponse> busCompanyResponse = busCompanies.stream()
                .map(busCompanyMapper::toBusCompanyResponse)
                .toList();
        return new PageResponse<>(
                busCompanyResponse,
                busCompanies.getNumber(),
                busCompanies.getSize(),
                busCompanies.getTotalElements(),
                busCompanies.getTotalPages(),
                busCompanies.isFirst(),
                busCompanies.isLast()
        );
    }

    // register bus company
    //edit bus company
    //delete bus company
    //get all bus company
    // show all buses
    // show all routes
}
