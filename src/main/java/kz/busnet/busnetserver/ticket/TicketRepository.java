package kz.busnet.busnetserver.ticket;

// TicketRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
