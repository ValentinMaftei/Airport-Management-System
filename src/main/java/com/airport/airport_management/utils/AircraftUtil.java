package com.airport.airport_management.utils;

import com.airport.airport_management.dto.AircraftDto;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AircraftUtil {

    private final AirlineRepository airlineRepository;

    @Autowired
    public AircraftUtil(AirlineRepository airlineRepository){
        this.airlineRepository = airlineRepository;
    }

    public Aircraft fromDtoToAircraft(AircraftDto aircraftDto){
        Airline airline = airlineRepository.findById(aircraftDto.getAirline_id()).orElse(null);

        return Aircraft.builder()
                .registration(aircraftDto.getRegistration())
                .model(aircraftDto.getModel())
                .airline(airline)
                .capacity(aircraftDto.getCapacity())
                .build();
    }
}
