package kz.busnet.busnetserver.ticket;

// TicketService.java
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;


    public Ticket bookTicket(String passengerName, String source, String destination) throws WriterException, IOException {
        Ticket ticket = new Ticket();
        ticket.setPassengerName(passengerName);
        ticket.setSource(source);
        ticket.setDestination(destination);

        String qrCode = generateQRCode(ticket);
        ticket.setQrCode(qrCode);

        return ticketRepository.save(ticket);
    }

    private String generateQRCode(Ticket ticket) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        var bitMatrix = qrCodeWriter.encode(ticket.toString(), BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
         MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(pngData);
    }
}
