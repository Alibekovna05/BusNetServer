package kz.busnet.busnetserver.busstation;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class BusStationMapper {
    public BusStationDTO convertToDTO(BusStation busStation) {
        BusStationDTO busStationDTO = new BusStationDTO();
        busStationDTO.setName(busStation.getName());
        busStationDTO.setAddress(busStation.getAddress());
        return busStationDTO;
    }

    public BusStation convertToEntity(BusStationDTO busStationDTO) {
        BusStation busStation = new BusStation();
        busStation.setName(busStationDTO.getName());
        busStation.setAddress(busStationDTO.getAddress());
        return busStation;
    }
}
