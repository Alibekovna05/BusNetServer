package kz.busnet.busnetserver.bus;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Bus findById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found"));
    }

    public Bus save(Bus bus) {
        return busRepository.save(bus);
    }

    public Bus update(Long id, Bus busDetails) {
        Bus bus = findById(id);
        bus.setLicensePlate(busDetails.getLicensePlate());
        bus.setModel(busDetails.getModel());
        bus.setCapacity(busDetails.getCapacity());
        bus.setColor(busDetails.getColor());
        bus.setPhoto(busDetails.getPhoto());
        return busRepository.save(bus);
    }

    public void delete(Long id) {
        Bus bus = findById(id);
        busRepository.delete(bus);
    }

    // Additional service methods
}
