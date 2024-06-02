package kz.busnet.busnetserver.ticket;

import lombok.Data;

@Data
public class TicketRequest {
    private String passengerName;
    private String source;
    private String destination;
}
