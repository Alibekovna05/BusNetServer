package kz.busnet.busnetserver.admin;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import kz.busnet.busnetserver.auth.AuthenticationService;
import kz.busnet.busnetserver.auth.RegistrationRequest;
import kz.busnet.busnetserver.busproviders.BusCompany;
import kz.busnet.busnetserver.busproviders.BusCompanyMapper;
import kz.busnet.busnetserver.busproviders.BusCompanyRequest;
import kz.busnet.busnetserver.busproviders.BusCompanyRepository;
import kz.busnet.busnetserver.email.EmailService;
import kz.busnet.busnetserver.email.EmailTemplateName;
import kz.busnet.busnetserver.role.Role;
import kz.busnet.busnetserver.role.RoleRepository;
import kz.busnet.busnetserver.security.JwtService;
import kz.busnet.busnetserver.user.TokenRepository;
import kz.busnet.busnetserver.user.User;
import kz.busnet.busnetserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BusCompanyRepository busCompanyRepository;
    private final BusCompanyMapper busCompanyMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    public List<BusCompany> getAllBusCompanies() {
        return busCompanyRepository.findAll();
    }

    public BusCompany getBusCompanyById(Long id) {
        return busCompanyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus Company not found"));
    }

    public BusCompany registerBusCompany(BusCompanyRequest request) throws MessagingException {
        Role userRole = roleRepository.findByName("BUS_COMPANY")
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

        BusCompany busCompany = busCompanyMapper.toBusCompany(request);
        busCompany.setOwner(user);
        return busCompanyRepository.save(busCompany);
    }

    public BusCompany updateBusCompany(Long id, BusCompanyRequest request) {
        BusCompany busCompany = busCompanyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus Company not found"));

        busCompany.setName(request.getName());
        busCompany.setAddress(request.getAddress());
        busCompany.setContactNumber(request.getContactNumber());
        busCompany.setEmail(request.getEmail());

        return busCompanyRepository.save(busCompany);
    }

    public void deleteBusCompany(Long id) {
        busCompanyRepository.deleteById(id);
    }
}
