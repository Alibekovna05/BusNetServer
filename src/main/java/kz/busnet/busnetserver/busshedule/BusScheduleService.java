package kz.busnet.busnetserver.busshedule;

import jakarta.persistence.EntityNotFoundException;
import kz.busnet.busnetserver.bus.BusRepository;
import kz.busnet.busnetserver.busproviders.BusCompanyRepository;
import kz.busnet.busnetserver.busstation.BusStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class BusScheduleService {

    private final BusScheduleRepository busScheduleRepository;
    private final BusScheduleMapper busScheduleMapper;
    private final BusRepository busRepository;
    private final BusStationRepository busStationRepository;
    private final BusCompanyRepository busCompanyRepository;

    public List<BusScheduleResponse> findAll() {
        return busScheduleRepository.findAll().stream()
                .map(busScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public BusScheduleDTO findById(Long id) {
        BusSchedule busSchedule = busScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusSchedule not found"));
        return busScheduleMapper.toDto(busSchedule);
    }

    public BusScheduleResponse create(BusScheduleDTO busScheduleDTO) {
        BusSchedule busSchedule = busScheduleMapper.toEntity(busScheduleDTO);
        busSchedule = busScheduleRepository.save(busSchedule);
        return busScheduleMapper.toResponseDto(busSchedule);
    }

    public BusScheduleResponse update(Long id, BusScheduleDTO busScheduleDTO) {
        BusSchedule busSchedule = busScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusSchedule not found"));
        updateBusSchedule(busScheduleDTO, busSchedule);
        busSchedule = busScheduleRepository.save(busSchedule);
        return busScheduleMapper.toResponseDto(busSchedule);
    }

    public void delete(Long id) {
        BusSchedule busSchedule = busScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusSchedule not found"));
        busScheduleRepository.delete(busSchedule);
    }

    public BusScheduleResponse bookSeat(Long id) {
        BusSchedule busSchedule = busScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusSchedule not found"));
        busSchedule.bookSeat();
        busSchedule = busScheduleRepository.save(busSchedule);
        return busScheduleMapper.toResponseDto(busSchedule);
    }

    public BusScheduleResponse releaseSeat(Long id) {
        BusSchedule busSchedule = busScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BusSchedule not found"));
        busSchedule.releaseSeat();
        busSchedule = busScheduleRepository.save(busSchedule);
        return busScheduleMapper.toResponseDto(busSchedule);
    }

    public List<BusScheduleResponse> findUpcoming() {
        return busScheduleRepository.findAll().stream()
                .filter(BusSchedule::isUpcoming)
                .map(busScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BusScheduleResponse> findCompleted() {
        return busScheduleRepository.findAll().stream()
                .filter(BusSchedule::isCompleted)
                .map(busScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BusScheduleResponse> findInTransit() {
        return busScheduleRepository.findAll().stream()
                .filter(BusSchedule::isInTransit)
                .map(busScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BusScheduleResponse> findByDayOfWeek(DayOfWeek dayOfWeek) {
        return busScheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getOperationalDays().contains(dayOfWeek))
                .map(busScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    private void updateBusSchedule(BusScheduleDTO busScheduleDTO, BusSchedule busSchedule) {
        busSchedule.setDepartTime(busScheduleDTO.getDepartTime());
        busSchedule.setArrivalTime(busScheduleDTO.getArrivalTime());
        setBusScheduleReferences(busScheduleDTO, busSchedule);
        busSchedule.setTotalSeats(busScheduleDTO.getTotalSeats());
        busSchedule.setAvailableSeats(busScheduleDTO.getAvailableSeats());
        busSchedule.setPrice(busScheduleDTO.getPrice());
        busSchedule.setOperationalDays(busScheduleDTO.getOperationalDays());
        busScheduleMapper.setStatus(busSchedule);
    }

    private void setBusScheduleReferences(BusScheduleDTO busScheduleDTO, BusSchedule busSchedule) {
        busSchedule.setBus(busRepository.getReferenceById(busScheduleDTO.getBusId()));
        busSchedule.setDepartStation(busStationRepository.getReferenceById(busScheduleDTO.getDepartStationId()));
        busSchedule.setArrivalStation(busStationRepository.getReferenceById(busScheduleDTO.getArrivalStationId()));
        busSchedule.setBusCompany(busCompanyRepository.getReferenceById(busScheduleDTO.getBusCompanyId()));
    }

    public List<BusScheduleResponse> search(String keyword) {
        return busScheduleRepository.search(keyword).stream()
                .map(busScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BusScheduleResponse> findAllSorted(String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return busScheduleRepository.findAll(sort).stream()
                .map(busScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
