package com.airport.airport_management.service;

import com.airport.airport_management.dto.GateDto;
import com.airport.airport_management.exception.GateNotFoundException;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.Gate;
import com.airport.airport_management.repository.FlightRepository;
import com.airport.airport_management.repository.GateRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.airport.airport_management.utils.GateUtil.fromDtoToGate;

@Service
public class GateService {
    private final GateRepository gateRepository;
    private final FlightRepository flightRepository;

    public GateService(GateRepository gateRepository, FlightRepository flightRepository) {
        this.gateRepository = gateRepository;
        this.flightRepository = flightRepository;
    }

    public Gate getGateByName(String name) {
        Optional<Gate> gateOptional = gateRepository.findByName(name);
        if (gateOptional.isPresent()) {
            return gateOptional.get();
        } else {
            throw new GateNotFoundException(name);
        }
    }

    public Gate addGate(@Valid GateDto gate) {
        return gateRepository.save(fromDtoToGate(gate));
    }

    public void deleteGateByName(String name) {
        Optional<Gate> gateOptional = gateRepository.findByName(name);
        if (gateOptional.isPresent()) {
            gateRepository.delete(gateOptional.get());
        } else {
            throw new GateNotFoundException(name);
        }
    }

    public Gate changeGateIsCleared(String name) {
        Optional<Gate> gateOptional = gateRepository.findByName(name);
        Optional<Flight> flightOptional = flightRepository.findByGateName(name);
        if (gateOptional.isEmpty()) {
            throw new GateNotFoundException(name);
        }
        flightOptional.ifPresent(flight -> flight.setGate(null));
        gateOptional.get().setOccupancy_status(false);
        return gateRepository.save(gateOptional.get());
    }

    public List<Aircraft> getAllAircraftAtGates() {
        return gateRepository.findAllAircraftAtGates();
    }
}
