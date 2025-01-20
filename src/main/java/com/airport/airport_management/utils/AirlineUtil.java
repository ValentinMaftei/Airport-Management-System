package com.airport.airport_management.utils;

import com.airport.airport_management.dto.AirlineDto;
import com.airport.airport_management.model.Airline;
import org.springframework.stereotype.Component;

@Component
public class AirlineUtil {

    public static Airline fromDtoToAirline(AirlineDto airlineDto){
        return Airline.builder()
                .name(airlineDto.getName())
                .icao(airlineDto.getIcao())
                .country(airlineDto.getCountry())
                .fleetSize(airlineDto.getFleetSize())
                .build();
    }
}
