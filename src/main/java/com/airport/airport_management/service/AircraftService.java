package com.airport.airport_management.service;

import com.airport.airport_management.dto.AircraftDto;
import com.airport.airport_management.exception.AircraftNotFoundException;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.repository.AircraftRepository;
import com.airport.airport_management.repository.AirlineRepository;
import com.airport.airport_management.utils.AircraftUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftUtil aircraftUtil;
    private final AirlineRepository airlineRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository, AircraftUtil aircraftUtil, AirlineRepository airlineRepository) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftUtil = aircraftUtil;
        this.airlineRepository = airlineRepository;
    }

    public Aircraft getAircraftByRegistration(String registration){
        Optional<Aircraft> aircraftOptional = aircraftRepository.findByRegistration(registration);
        if (aircraftOptional.isPresent()){
            return aircraftOptional.get();
        } else {
            throw new AircraftNotFoundException(registration);
        }
    }

    public Aircraft addAircraft(AircraftDto aircraft){
        var airline = aircraft.getAirline_id();
        var airlineOptional = airlineRepository.findById(airline);
        airlineOptional.ifPresent(value -> value.setFleetSize(value.getFleetSize() + 1));
        return aircraftRepository.save(aircraftUtil.fromDtoToAircraft(aircraft));
    }
}
