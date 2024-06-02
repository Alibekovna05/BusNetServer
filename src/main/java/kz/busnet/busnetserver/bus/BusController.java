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

    private final BusRepository repository;


    @GetMapping("/buses")
    public Iterable<Bus> getBuses(){
        return repository.findAll();
    }

}
