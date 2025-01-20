package com.airport.airport_management.service;

import com.airport.airport_management.dto.AirlineDto;
import com.airport.airport_management.exception.AirlineNotFoundException;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.repository.AirlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.airport.airport_management.utils.AirlineUtil.fromDtoToAirline;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Airline getAirlineByIcao(String icao) {
        Optional<Airline> airlineOptional = airlineRepository.findByIcao(icao);
        if (airlineOptional.isPresent()) {
            return airlineOptional.get();
        } else {
            throw new AirlineNotFoundException(icao);
        }
    }

    public Airline addAirline(AirlineDto airline) {
        return airlineRepository.save(fromDtoToAirline(airline));
    }
}
