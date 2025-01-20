package com.airport.airport_management.service;

import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.exception.FlightNotFoundException;
import com.airport.airport_management.exception.GateNotFoundException;
import com.airport.airport_management.exception.GateOccupiedException;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.Gate;
import com.airport.airport_management.model.Ticket;
import com.airport.airport_management.repository.FlightRepository;
import com.airport.airport_management.repository.GateRepository;
import com.airport.airport_management.utils.FlightStatus;
import com.airport.airport_management.utils.FlightUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightUtil flightUtil;
    private final GateRepository gateRepository;

    private FlightService(FlightRepository flightRepository, FlightUtil flightUtil, GateRepository gateRepository) {
        this.flightRepository = flightRepository;
        this.flightUtil = flightUtil;
        this.gateRepository = gateRepository;
    }

    public Flight addFlight(FlightDto flightDto){
        return flightRepository.save(flightUtil.fromDtoToFlight(flightDto));
    }

    public Flight getFlightbyFlightNumber(String flight_number){
        Optional<Flight> flightOptional = flightRepository.findByFlightNumber(flight_number);
        if (flightOptional.isPresent()){
            return flightOptional.get();
        } else {
            throw new FlightNotFoundException(flight_number);
        }
    }

    public Flight changeFlightGate(String flight_number, String new_gate_name){
        Optional<Flight> flightOptional = flightRepository.findByFlightNumber(flight_number);
        if (flightOptional.isEmpty()){
            throw new FlightNotFoundException(flight_number);
        }
        Flight flight = flightOptional.get();
        Optional<Gate> gateOptional = gateRepository.findByName(new_gate_name);
        if (gateOptional.isEmpty()){
            throw new GateNotFoundException(new_gate_name);
        }
        else if (gateOptional.get().isOccupancy_status()){
            throw new GateOccupiedException(new_gate_name);
        }
        gateOptional.get().setOccupancy_status(true);
        flight.setGate(gateOptional.get());
        return flightRepository.save(flight);
    }

    public Flight changeFlightStatus(String flight_number, String new_status){
        Optional<Flight> flightOptional = flightRepository.findByFlightNumber(flight_number);
        if (flightOptional.isEmpty()){
            throw new FlightNotFoundException(flight_number);
        }
        Flight flight = flightOptional.get();
        flight.setStatus(FlightStatus.valueOf(new_status.toUpperCase()));
        return flightRepository.save(flight);
    }
}
