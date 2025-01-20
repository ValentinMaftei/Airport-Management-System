package com.airport.airport_management.utils;

import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightUtil {

    private final AircraftRepository aircraftRepository;

    @Autowired
    public FlightUtil(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    public Flight fromDtoToFlight(FlightDto flightDto){
        Aircraft aircraft = aircraftRepository.findByRegistration(flightDto.getAircraft_id()).orElse(null);

        return Flight.builder()
                .flight_number(flightDto.getFlightNumber())
                .origin(flightDto.getOrigin())
                .destination(flightDto.getDestination())
                .departure_time(flightDto.getDeparture_time())
                .arrival_time(flightDto.getArrival_time())
                .type(FlightType.valueOf(flightDto.getType()))
                .status(FlightStatus.SCHEDULED)
                .aircraft(aircraft)
                .build();

    }
}
