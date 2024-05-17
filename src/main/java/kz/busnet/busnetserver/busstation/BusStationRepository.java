package kz.busnet.busnetserver.busstation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStationRepository extends JpaRepository<BusStation, Long> {

    BusStation getBusStationByName(String name);
}
