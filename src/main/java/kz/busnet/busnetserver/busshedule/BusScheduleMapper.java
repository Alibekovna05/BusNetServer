package kz.busnet.busnetserver.busshedule;

import jakarta.persistence.EntityNotFoundException;
import kz.busnet.busnetserver.bus.BusRepository;
import kz.busnet.busnetserver.busstation.BusStation;
import kz.busnet.busnetserver.busstation.BusStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusScheduleMapper {
    private final BusStationRepository busStationRepository;
    private final BusRepository busRepository;

    public BusScheduleDTO toDto(BusSchedule busSchedule) {
        if (busSchedule == null) {
            return null;
        }

        return new BusScheduleDTO(
                busSchedule.getDepartTime(),
                busSchedule.getArrivalTime(),
                busSchedule.getDepartStation().getId(),
                busSchedule.getArrivalStation().getId(),
                busSchedule.getBus().getId(),
                busSchedule.getBusCompany().getId(),
                busSchedule.getTotalSeats(),
                busSchedule.getAvailableSeats(),
                busSchedule.getPrice(),
                busSchedule.getStatus(),
                busSchedule.getOperationalDays());
    }

    public BusScheduleResponse toResponseDto(BusSchedule busSchedule) {
        if (busSchedule == null) {
            return null;
        }

        return new BusScheduleResponse(
                busSchedule.getId(),
                busSchedule.getDepartTime(),
                busSchedule.getArrivalTime(),
                busStationRepository.getReferenceById(busSchedule.getDepartStation().getId()).getName(),
                busStationRepository.getReferenceById(busSchedule.getArrivalStation().getId()).getName(),
                busRepository.getReferenceById(busSchedule.getBus().getId()).getLicensePlate(),
                busSchedule.getBusCompany().getId(),
                busSchedule.getTotalSeats(),
                busSchedule.getAvailableSeats(),
                busSchedule.getPrice());
    }
    public BusSchedule toEntity(BusScheduleDTO busScheduleDTO) {
        if (busScheduleDTO == null) {
            return null;
        }

        BusSchedule busSchedule = new BusSchedule();
        busSchedule.setDepartTime(busScheduleDTO.getDepartTime());
        busSchedule.setArrivalTime(busScheduleDTO.getArrivalTime());
        busSchedule.setDepartStation(getBusStation(busScheduleDTO.getDepartStationId()));
        busSchedule.setArrivalStation(getBusStation(busScheduleDTO.getArrivalStationId()));
        busSchedule.setTotalSeats(busScheduleDTO.getTotalSeats());
        busSchedule.setAvailableSeats(busScheduleDTO.getAvailableSeats());
        busSchedule.setPrice(busScheduleDTO.getPrice());
        return busSchedule;
    }

    private BusStation getBusStation(Long id) {
        return busStationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusStation not found with id: " + id));
    }
}
