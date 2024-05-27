package kz.busnet.busnetserver.busshedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {
    @Query("SELECT b FROM BusSchedule b WHERE b.departStation.name LIKE %?1% OR b.arrivalStation.name LIKE %?1% OR b.busCompany.name LIKE %?1%")
    List<BusSchedule> search(String keyword);
}
