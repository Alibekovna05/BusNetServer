package kz.busnet.busnetserver.ticket;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketService.bookTicket(ticketRequest.getPassengerName(),
                    ticketRequest.getSource(), ticketRequest.getDestination());
            return ResponseEntity.ok(ticket);
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
