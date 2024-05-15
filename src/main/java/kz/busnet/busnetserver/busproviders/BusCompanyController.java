package kz.busnet.busnetserver.busproviders;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import kz.busnet.busnetserver.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("buscompany")
@RequiredArgsConstructor
@Tag(name = "Bus_Company")
public class BusCompanyController  {
    private final BusCompanyService busCompanyService;

    @PostMapping("/registerBusCompany")
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid BusCompanyRequest request
    ) throws MessagingException {
        busCompanyService.registerBusCompany(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping
    public ResponseEntity<PageResponse<BusCompanyResponse>> findAllBusCompanies(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(busCompanyService.findAllBusCompany(page, size));
    }
}
