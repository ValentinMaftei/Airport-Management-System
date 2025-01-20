package com.airport.airport_management.service;

import com.airport.airport_management.dto.FlightCrewDto;
import com.airport.airport_management.exception.FlightCrewNotFoundException;
import com.airport.airport_management.model.FlightCrew;
import com.airport.airport_management.repository.FlightCrewRepository;
import com.airport.airport_management.utils.FlightCrewUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightCrewService {
    private final FlightCrewRepository flightCrewRepository;
    private final FlightCrewUtil flightCrewUtil;

    public FlightCrewService(FlightCrewRepository flightCrewRepository, FlightCrewUtil flightCrewUtil) {
        this.flightCrewRepository = flightCrewRepository;
        this.flightCrewUtil = flightCrewUtil;
    }

    public FlightCrew addFlightCrew(FlightCrewDto flightCrewDto) {
        return flightCrewRepository.save(flightCrewUtil.fromDtoToFlightCrew(flightCrewDto));
    }

    public Optional<List<FlightCrew>> getFlightCrewByFlightNumber(String flight_number) {
        Optional<List<FlightCrew>> flightCrewOptional = flightCrewRepository.findFlightCrewByFlightNumber(flight_number);
        if (flightCrewOptional.isPresent()) {
            return flightCrewOptional;
        } else {
            throw new FlightCrewNotFoundException(flight_number);
        }
    }

    public void deleteCrewMemberFromFlight(int crew_id, String flight_number) {
        flightCrewRepository.deleteCrewMemberFromFlight(crew_id, flight_number);
    }
}
