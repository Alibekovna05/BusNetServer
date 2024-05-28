package kz.busnet.busnetserver.admin;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import kz.busnet.busnetserver.auth.AuthenticationService;
import kz.busnet.busnetserver.busproviders.BusCompany;
import kz.busnet.busnetserver.busproviders.BusCompanyRequest;
import kz.busnet.busnetserver.role.Role;
import kz.busnet.busnetserver.role.RoleRepository;
import kz.busnet.busnetserver.user.User;
import kz.busnet.busnetserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;


import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;
    private final AdminService adminService;

    // User CRUD Operations

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody User userUpdates) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstname(userUpdates.getFirstname());
        user.setLastname(userUpdates.getLastname());
        user.setAccountLocked(userUpdates.isAccountLocked());
        user.setEnabled(userUpdates.isEnabled());
        user.setPassword(passwordEncoder.encode(userUpdates.getPassword()));
        user.setLastModifiedDate(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // BusCompany CRUD Operations

    @GetMapping("/buscompanies")
    public List<BusCompany> getAllBusCompanies() {
        return adminService.getAllBusCompanies();
    }

    @GetMapping("/buscompany/{id}")
    public BusCompany getBusCompany(@PathVariable Long id) {
        return adminService.getBusCompanyById(id);
    }

    @PostMapping("/buscompany")
    public ResponseEntity<BusCompany> addBusCompany(@Valid @RequestBody BusCompanyRequest request) throws MessagingException {
        BusCompany savedBusCompany = adminService.registerBusCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBusCompany);
    }

    @PutMapping("/buscompany/{id}")
    public ResponseEntity<BusCompany> updateBusCompany(@PathVariable Long id, @Valid @RequestBody BusCompanyRequest request) {
        BusCompany updatedBusCompany = adminService.updateBusCompany(id, request);
        return ResponseEntity.ok(updatedBusCompany);
    }

    @DeleteMapping("/buscompany/{id}")
    public ResponseEntity<String> deleteBusCompany(@PathVariable Long id) {
        adminService.deleteBusCompany(id);
        return ResponseEntity.ok("Bus Company deleted successfully");
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


}

