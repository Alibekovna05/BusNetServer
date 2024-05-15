package kz.busnet.busnetserver.busstation;

import kz.busnet.busnetserver.busstation.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BusStationService {

    private final BusStationRepository busStationRepository;
    private final BusStationMapper busStationMapper;

    public List<BusStationDTO> findAll() {
        return busStationRepository.findAll().stream()
                .map(busStationMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public BusStationDTO findById(Long id) {
        BusStation busStation = busStationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus Station not found"));
        return busStationMapper.convertToDTO(busStation);
    }

    public BusStationDTO save(BusStationDTO busStationDTO) {
        BusStation busStation = busStationMapper.convertToEntity(busStationDTO);
        BusStation savedBusStation = busStationRepository.save(busStation);
        return busStationMapper.convertToDTO(savedBusStation);
    }

    public BusStationDTO update(Long id, BusStationDTO busStationDTO) {
        BusStation existingBusStation = busStationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus Station not found"));
        existingBusStation.setName(busStationDTO.getName());
        existingBusStation.setAddress(busStationDTO.getAddress());
        BusStation updatedBusStation = busStationRepository.save(existingBusStation);
        return busStationMapper.convertToDTO(updatedBusStation);
    }

    public void delete(Long id) {
        busStationRepository.deleteById(id);
    }
}
