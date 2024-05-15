package kz.busnet.busnetserver.busstation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bus-stations")
@RequiredArgsConstructor
public class BusStationController {

    private final BusStationService busStationService;
    @GetMapping
    public ResponseEntity<List<BusStationDTO>> getAllBusStations() {
        List<BusStationDTO> busStations = busStationService.findAll();
        return ResponseEntity.ok(busStations);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BusStationDTO> getBusStationById(@PathVariable Long id) {
        BusStationDTO busStation = busStationService.findById(id);
        return ResponseEntity.ok(busStation);
    }
    @PostMapping
    public ResponseEntity<BusStationDTO> createBusStation(@Valid @RequestBody BusStationDTO busStationDTO) {
        BusStationDTO newBusStation = busStationService.save(busStationDTO);
        return new ResponseEntity<>(newBusStation, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BusStationDTO> updateBusStation(@PathVariable Long id, @Valid @RequestBody BusStationDTO busStationDTO) {
        BusStationDTO updatedBusStation = busStationService.update(id, busStationDTO);
        return ResponseEntity.ok(updatedBusStation);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusStation(@PathVariable Long id) {
        busStationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
