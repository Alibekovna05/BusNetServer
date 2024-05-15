package kz.busnet.busnetserver.user;

import kz.busnet.busnetserver.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);

    Optional<User> findByRoles(List<Role> roles);



}
