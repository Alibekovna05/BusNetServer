package kz.busnet.busnetserver;

import kz.busnet.busnetserver.role.Role;
import kz.busnet.busnetserver.role.RoleRepository;
import kz.busnet.busnetserver.user.User;
import kz.busnet.busnetserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@RequiredArgsConstructor
public class BusNetServerApplication {

	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BusNetServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
			if (roleRepository.findByName("ADMIN").isEmpty()) {
				roleRepository.save(Role.builder().name("ADMIN").build());
			}	if (roleRepository.findByName("BUS_COMPANY").isEmpty()) {
				roleRepository.save(Role.builder().name("BUS_COMPANY").build());
			}
		};
	}

	@Bean
	public CommandLineRunner userRunner(UserRepository userRepository, RoleRepository roleRepository,
									@Value("${admin.username}") String adminUsername,
									@Value("${admin.password}") String adminPassword) {
		return args -> {
			if (userRepository.findByEmail(adminUsername).isEmpty()) {
				List<Role> adminRoles = new ArrayList<>() ;
				adminRoles.add(roleRepository.findByName("ADMIN")
						.orElseThrow(() -> new RuntimeException("Admin role not found")));
				adminRoles.add(roleRepository.findByName("USER")
						.orElseThrow(() -> new RuntimeException("User role not found")));
				User adminUser = User.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email(adminUsername)
						.password(passwordEncoder.encode(adminPassword))
						.enabled(true)
						.build();
				adminUser.setRoles(adminRoles);
				userRepository.save(adminUser);
			}
		};
	}

}

