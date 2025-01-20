package com.airport.airport_management.service;

import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.exception.CrewMemberNotFoundException;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.repository.CrewMemberRepository;
import com.airport.airport_management.utils.CrewMemberUtil;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CrewMemberService {
    private final CrewMemberRepository crewMemberRepository;
    private final CrewMemberUtil crewMemberUtil;

    public CrewMemberService(CrewMemberRepository crewMemberRepository, CrewMemberUtil crewMemberUtil) {
        this.crewMemberRepository = crewMemberRepository;
        this.crewMemberUtil = crewMemberUtil;
    }

    public CrewMember addCrewMember(CrewMemberDto crewMember) {
        return crewMemberRepository.save(crewMemberUtil.fromDtoToCrewMember(crewMember));
    }

    public CrewMember findByRegistrationNumber(String registration_number) {
        Optional<CrewMember> crewMemberOptional = crewMemberRepository.findByRegistrationNumber(registration_number);
        if (crewMemberOptional.isPresent()) {
            return crewMemberOptional.get();
        } else {
            throw new CrewMemberNotFoundException(registration_number);
        }
    }

    public List<CrewMember> getAllCrewMembersForAnAirline(String airline_name) {
        return crewMemberRepository.findAllCrewMembersForAnAirline(airline_name);
    }
}
