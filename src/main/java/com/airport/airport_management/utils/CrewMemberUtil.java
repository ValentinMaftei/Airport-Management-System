package com.airport.airport_management.utils;

import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrewMemberUtil {

    private final AirlineRepository airlineRepository;

    @Autowired
    public CrewMemberUtil(AirlineRepository airlineRepository){
        this.airlineRepository = airlineRepository;
    }

    public CrewMember fromDtoToCrewMember(CrewMemberDto crewMemberDto){
        Airline airline = airlineRepository.findById(crewMemberDto.getAirline_id()).orElse(null);

        return CrewMember.builder()
                .first_name(crewMemberDto.getFirst_name())
                .last_name(crewMemberDto.getLast_name())
                .role(crewMemberDto.getRole())
                .airline(airline)
                .registration_number(crewMemberDto.getRegistration_number())
                .build();
    }
}
