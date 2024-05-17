package kz.busnet.busnetserver.busstation;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class BusStationMapper {
    public BusStationDTO convertToDTO(BusStation busStation) {
        BusStationDTO busStationDTO = new BusStationDTO();
        busStationDTO.setName(busStation.getName());
        busStationDTO.setAddress(busStation.getAddress());
        busStationDTO.setCity(busStation.getCity());
        busStationDTO.setLatitude(busStation.getLatitude());
        busStationDTO.setLongitude(busStation.getLongitude());
        busStationDTO.setContactEmail(busStation.getCity());

        return busStationDTO;
    }

    public BusStation convertToEntity(BusStationDTO busStationDTO) {
        BusStation busStation = new BusStation();
        busStation.setName(busStationDTO.getName());
        busStation.setAddress(busStationDTO.getAddress());
        busStation.setCity(busStationDTO.getCity());
        busStation.setLatitude(busStationDTO.getLatitude());
        busStation.setLongitude(busStationDTO.getLongitude());
        busStation.setContactEmail(busStationDTO.getCity());

        return busStation;
    }
}
