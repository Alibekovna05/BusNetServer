package kz.busnet.busnetserver.booking;

import kz.busnet.busnetserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Id(Integer userId);

    List<Booking> findBookingsByUser(User user);
}
