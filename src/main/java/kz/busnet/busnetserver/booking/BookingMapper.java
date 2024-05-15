package kz.busnet.busnetserver.booking;

import kz.busnet.busnetserver.busshedule.BusSchedule;
import kz.busnet.busnetserver.busshedule.BusScheduleRepository;
import kz.busnet.busnetserver.busstation.BusStationRepository;
import kz.busnet.busnetserver.user.User;
import kz.busnet.busnetserver.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class BookingMapper {

    private final BusScheduleRepository busScheduleRepository;
    private final UserRepository userRepository;

    public BookingDTO toDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setBusScheduleId(booking.getBusSchedule().getId());
        dto.setUserId(booking.getUser().getId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setQrCodeDataImg(booking.getQrCodeDataImg());
        dto.setQrCodeData(booking.getQrCodeData());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    public Booking toEntity(BookingDTO dto) {
        if (dto == null) {
            return null;
        }
        Booking booking = new Booking();
        BusSchedule busSchedule = busScheduleRepository.findById(dto.getBusScheduleId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        booking.setId(dto.getId());
        booking.setBusSchedule(busSchedule);
        booking.setUser(user);
        booking.setBookingDate(dto.getBookingDate());
        booking.setStatus(dto.getStatus());


        booking.setQrCodeDataImg(dto.getQrCodeDataImg());
        booking.setQrCodeData(dto.getQrCodeData());
        return booking;
    }
}
