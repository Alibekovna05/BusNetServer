package kz.busnet.busnetserver.admin;

import jakarta.mail.MessagingException;
import kz.busnet.busnetserver.auth.AuthenticationService;
import kz.busnet.busnetserver.auth.RegistrationRequest;
import kz.busnet.busnetserver.email.EmailService;
import kz.busnet.busnetserver.email.EmailTemplateName;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;



}
