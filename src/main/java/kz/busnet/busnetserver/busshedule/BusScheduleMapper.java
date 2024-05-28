package kz.busnet.busnetserver.busshedule;

import jakarta.persistence.EntityNotFoundException;
import kz.busnet.busnetserver.bus.BusRepository;
import kz.busnet.busnetserver.busstation.BusStation;
import kz.busnet.busnetserver.busstation.BusStationRepository;
import kz.busnet.busnetserver.busproviders.BusCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BusScheduleMapper {
    private final BusStationRepository busStationRepository;
    private final BusRepository busRepository;
    private final BusCompanyRepository busCompanyRepository;

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

    public BusScheduleResponse toDtoResponse(BusScheduleDTO busScheduleDTO) {
        if (busScheduleDTO == null) {
            return null;
        }

        return new BusScheduleResponse(
                null,  //  there's no id in DTO
                busScheduleDTO.getDepartTime(),
                busScheduleDTO.getArrivalTime(),
                busStationRepository.getReferenceById(busScheduleDTO.getDepartStationId()).getName(),
                busStationRepository.getReferenceById(busScheduleDTO.getArrivalStationId()).getName(),
                busRepository.getReferenceById(busScheduleDTO.getBusId()).getLicensePlate(),
                busCompanyRepository.getReferenceById(busScheduleDTO.getBusCompanyId()).getName(),
                busScheduleDTO.getTotalSeats(),
                busScheduleDTO.getAvailableSeats(),
                busScheduleDTO.getPrice()
        );
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
                busCompanyRepository.getReferenceById(busSchedule.getBusCompany().getId()).getName(),
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
        busSchedule.setBus(busRepository.getReferenceById(busScheduleDTO.getBusId()));
        busSchedule.setBusCompany(busCompanyRepository.getReferenceById(busScheduleDTO.getBusCompanyId()));
        busSchedule.setTotalSeats(busScheduleDTO.getTotalSeats());
        busSchedule.setAvailableSeats(busScheduleDTO.getAvailableSeats());
        busSchedule.setPrice(busScheduleDTO.getPrice());
        busSchedule.setOperationalDays(busScheduleDTO.getOperationalDays());
        setStatus(busSchedule);
        return busSchedule;
    }

    private BusStation getBusStation(Long id) {
        return busStationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusStation not found with id: " + id));
    }

    protected void setStatus(BusSchedule busSchedule) {
        LocalDateTime now = LocalDateTime.now();
        if (busSchedule.getDepartTime().isAfter(now)) {
            busSchedule.setStatus(ScheduleStatus.UPCOMING);
        } else if (busSchedule.getArrivalTime().isBefore(now)) {
            busSchedule.setStatus(ScheduleStatus.COMPLETED);
        } else {
            busSchedule.setStatus(ScheduleStatus.IN_PROGRESS);
        }
    }
}
