package kz.busnet.busnetserver.busshedule;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
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
        List<BusScheduleResponse> busScheduleResponses = busScheduleService.findAll();
        return ResponseEntity.ok(busScheduleResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusScheduleResponse> getBusScheduleById(@PathVariable Long id) {
        BusScheduleDTO busScheduleDTO = busScheduleService.findById(id);
        BusScheduleResponse busScheduleResponse = busScheduleMapper.toDtoResponse(busScheduleDTO);
        return ResponseEntity.ok(busScheduleResponse);
    }

    @PostMapping
    public ResponseEntity<BusScheduleResponse> createBusSchedule(@RequestBody @Valid BusScheduleDTO busScheduleDTO) {
        BusScheduleResponse createdBusSchedule = busScheduleService.create(busScheduleDTO);
        return new ResponseEntity<>(createdBusSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusScheduleResponse> updateBusSchedule(@PathVariable Long id, @RequestBody @Valid BusScheduleDTO busScheduleDTO) {
        BusScheduleResponse updatedBusSchedule = busScheduleService.update(id, busScheduleDTO);
        return ResponseEntity.ok(updatedBusSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusSchedule(@PathVariable Long id) {
        busScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/book-seat")
    public ResponseEntity<BusScheduleResponse> bookSeat(@PathVariable Long id) {
        BusScheduleResponse busScheduleResponse = busScheduleService.bookSeat(id);
        return ResponseEntity.ok(busScheduleResponse);
    }

    @PatchMapping("/{id}/release-seat")
    public ResponseEntity<BusScheduleResponse> releaseSeat(@PathVariable Long id) {
        BusScheduleResponse busScheduleResponse = busScheduleService.releaseSeat(id);
        return ResponseEntity.ok(busScheduleResponse);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<BusScheduleResponse>> getUpcomingBusSchedules() {
        List<BusScheduleResponse> upcomingSchedules = busScheduleService.findUpcoming();
        return ResponseEntity.ok(upcomingSchedules);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<BusScheduleResponse>> getCompletedBusSchedules() {
        List<BusScheduleResponse> completedSchedules = busScheduleService.findCompleted();
        return ResponseEntity.ok(completedSchedules);
    }

    @GetMapping("/in-transit")
    public ResponseEntity<List<BusScheduleResponse>> getBusSchedulesInTransit() {
        List<BusScheduleResponse> inTransitSchedules = busScheduleService.findInTransit();
        return ResponseEntity.ok(inTransitSchedules);
    }

    @GetMapping("/by-day/{dayOfWeek}")
    public ResponseEntity<List<BusScheduleResponse>> getBusSchedulesByDay(@PathVariable DayOfWeek dayOfWeek) {
        List<BusScheduleResponse> schedulesByDay = busScheduleService.findByDayOfWeek(dayOfWeek);
        return ResponseEntity.ok(schedulesByDay);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<BusScheduleResponse>> getBusSchedulesSorted(@RequestParam String sortBy, @RequestParam String direction) {
        List<BusScheduleResponse> sortedSchedules = busScheduleService.findAllSorted(sortBy, direction);
        return ResponseEntity.ok(sortedSchedules);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusScheduleResponse>> searchBusSchedules(@RequestParam String keyword) {
        List<BusScheduleResponse> searchResults = busScheduleService.search(keyword);
        return ResponseEntity.ok(searchResults);
    }
}
