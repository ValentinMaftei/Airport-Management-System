package com.airport.airport_management.utils;

import com.airport.airport_management.dto.FlightCrewDto;
import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.FlightCrew;
import com.airport.airport_management.model.FlightCrewPK;
import com.airport.airport_management.repository.CrewMemberRepository;
import com.airport.airport_management.repository.FlightCrewRepository;
import com.airport.airport_management.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightCrewUtil {

    private final CrewMemberRepository crewMemberRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public FlightCrewUtil(CrewMemberRepository crewMemberRepository, FlightRepository flightRepository) {
        this.crewMemberRepository = crewMemberRepository;
        this.flightRepository = flightRepository;
    }

    public FlightCrew fromDtoToFlightCrew(FlightCrewDto flightCrewDto){
        Flight flight = flightRepository.findByFlightNumber(flightCrewDto.getFlight_number()).orElse(null);
        CrewMember crewMember = crewMemberRepository.findById(flightCrewDto.getCrew_id()).orElse(null);

        FlightCrewPK flightCrewPK = new FlightCrewPK();
        flightCrewPK.setCrew_id(flightCrewDto.getCrew_id());
        flightCrewPK.setFlight_number(flightCrewDto.getFlight_number());

        return FlightCrew.builder()
                .flight_crew_id(flightCrewPK)
                .flight(flight)
                .crewMember(crewMember)
                .build();
    }
}
