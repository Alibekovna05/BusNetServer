package kz.busnet.busnetserver.busshedule;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import java.time.DayOfWeek;

@RestController
@RequestMapping("/bus-schedules")
@RequiredArgsConstructor
public class BusScheduleController {

    private final BusScheduleService busScheduleService;
    private final BusScheduleMapper busScheduleMapper;

    @GetMapping
    public ResponseEntity<List<BusScheduleResponse>> getAllBusSchedules() {
        List<BusScheduleResponse> busScheduleDTOs = busScheduleService.findAll();
        return ResponseEntity.ok(busScheduleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusScheduleDTO> getBusScheduleById(@PathVariable Long id) {
        BusScheduleDTO busScheduleDTO = busScheduleService.findById(id);
        return ResponseEntity.ok(busScheduleDTO);
    }

    @PostMapping
    public ResponseEntity<BusScheduleDTO> createBusSchedule(@RequestBody @Valid BusScheduleDTO busScheduleDTO) {
        BusScheduleDTO createdBusSchedule = busScheduleService.create(busScheduleDTO);
        return new ResponseEntity<>(createdBusSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusScheduleDTO> updateBusSchedule(@PathVariable Long id, @RequestBody @Valid BusScheduleDTO busScheduleDTO) {
        BusScheduleDTO updatedBusSchedule = busScheduleService.update(id, busScheduleDTO);
        return ResponseEntity.ok(updatedBusSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusSchedule(@PathVariable Long id) {
        busScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/book-seat")
    public ResponseEntity<BusScheduleDTO> bookSeat(@PathVariable Long id) {
        BusScheduleDTO busScheduleDTO = busScheduleService.bookSeat(id);
        return ResponseEntity.ok(busScheduleDTO);
    }

    @PatchMapping("/{id}/release-seat")
    public ResponseEntity<BusScheduleDTO> releaseSeat(@PathVariable Long id) {
        BusScheduleDTO busScheduleDTO = busScheduleService.releaseSeat(id);
        return ResponseEntity.ok(busScheduleDTO);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<BusScheduleDTO>> getUpcomingBusSchedules() {
        List<BusScheduleDTO> upcomingSchedules = busScheduleService.findUpcoming();
        return ResponseEntity.ok(upcomingSchedules);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<BusScheduleDTO>> getCompletedBusSchedules() {
        List<BusScheduleDTO> completedSchedules = busScheduleService.findCompleted();
        return ResponseEntity.ok(completedSchedules);
    }

    @GetMapping("/in-transit")
    public ResponseEntity<List<BusScheduleDTO>> getBusSchedulesInTransit() {
        List<BusScheduleDTO> inTransitSchedules = busScheduleService.findInTransit();
        return ResponseEntity.ok(inTransitSchedules);
    }

    @GetMapping("/by-day/{dayOfWeek}")
    public ResponseEntity<List<BusScheduleDTO>> getBusSchedulesByDay(@PathVariable DayOfWeek dayOfWeek) {
        List<BusScheduleDTO> schedulesByDay = busScheduleService.findByDayOfWeek(dayOfWeek);
        return ResponseEntity.ok(schedulesByDay);
    }


    @GetMapping("/sorted")
    public ResponseEntity<List<BusScheduleDTO>> getBusSchedulesSorted(@RequestParam String sortBy, @RequestParam String direction) {
        List<BusScheduleDTO> sortedSchedules = busScheduleService.findAllSorted(sortBy, direction);
        return ResponseEntity.ok(sortedSchedules);
    }


    @GetMapping("/search")
    public ResponseEntity<List<BusScheduleDTO>> searchBusSchedules(@RequestParam String keyword) {
        List<BusScheduleDTO> searchResults = busScheduleService.search(keyword);
        return ResponseEntity.ok(searchResults);
    }
}
