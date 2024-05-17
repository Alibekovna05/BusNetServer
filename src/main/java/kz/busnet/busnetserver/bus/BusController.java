package kz.busnet.busnetserver.bus;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;
    @GetMapping
    public ResponseEntity<List<Bus>> getAllBuses() {
        List<Bus> buses = busService.findAll();
        return ResponseEntity.ok(buses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        Bus bus = busService.findById(id);
        return ResponseEntity.ok(bus);
    }
    @PostMapping
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) {
        Bus savedBus = busService.save(bus);
        return new ResponseEntity<>(savedBus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus busDetails) {
        Bus updatedBus = busService.update(id, busDetails);
        return ResponseEntity.ok(updatedBus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{id}/photo")
//    public ResponseEntity<Bus> uploadBusPhoto(@PathVariable Long id, @RequestParam("photo") MultipartFile photo) {
//        Bus bus = busService.findById(id);
//        try {
//            bus.setPhoto(photo.getBytes());
//            busService.save(bus);
//            return ResponseEntity.ok(bus);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/{id}/photo")
//    public ResponseEntity<byte[]> getBusPhoto(@PathVariable Long id) {
//        Bus bus = busService.findById(id);
//        byte[] photo = bus.getPhoto();
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo);
//    }
}
