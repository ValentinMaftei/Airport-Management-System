package com.airport.airport_management.service;

import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.exception.CrewMemberNotFoundException;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.repository.CrewMemberRepository;
import com.airport.airport_management.utils.CrewMemberUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrewMemberServiceTest {

    @InjectMocks
    private CrewMemberService crewMemberService;

    @Mock
    private CrewMemberRepository crewMemberRepository;

    @Mock
    private CrewMemberUtil crewMemberUtil;

    @Test
    void addCrewMember() {
        CrewMemberDto crewMemberDto = new CrewMemberDto();
        crewMemberDto.setAirline_id(1);
        crewMemberDto.setRole("Pilot");
        crewMemberDto.setFirst_name("John");
        crewMemberDto.setLast_name("Ionica");
        crewMemberDto.setRegistration_number("W15241");

        CrewMember crewMember = new CrewMember();
        crewMember.setAirline(new Airline());
        crewMember.getAirline().setId(1);
        crewMember.setRole("Pilot");
        crewMember.setFirst_name("John");
        crewMember.setLast_name("Ionica");
        crewMember.setRegistration_number("W15241");

        when(crewMemberUtil.fromDtoToCrewMember(crewMemberDto)).thenReturn(crewMember);
        when(crewMemberRepository.save(crewMember)).thenReturn(crewMember);

        CrewMember result = crewMemberService.addCrewMember(crewMemberDto);

        assertNotNull(result);
        assertEquals(1, result.getAirline().getId());
        assertEquals("Pilot", result.getRole());
        assertEquals("John", result.getFirst_name());
        assertEquals("Ionica", result.getLast_name());
        assertEquals("W15241", result.getRegistration_number());

        verify(crewMemberRepository).save(crewMemberUtil.fromDtoToCrewMember(crewMemberDto));
    }

    @Test
    void crewMemberNotFoundException(){
        CrewMemberNotFoundException exception = assertThrows(CrewMemberNotFoundException.class, () -> crewMemberService.findByRegistrationNumber("W15241"));
        assertEquals("Crew member with registration number W15241 not found", exception.getMessage());
    }

    @Test
    void findCrewByRegistrationNumber(){
        CrewMember crewMember = new CrewMember();
        crewMember.setRegistration_number("W15241");

        when(crewMemberRepository.findByRegistrationNumber("W15241")).thenReturn(Optional.of(crewMember));

        CrewMember result = crewMemberService.findByRegistrationNumber("W15241");

        assertNotNull(result);
        assertEquals("W15241", result.getRegistration_number());

        verify(crewMemberRepository).findByRegistrationNumber("W15241");
    }

    @Test
    void getAllCrewByAirline(){
        CrewMember crewMember = new CrewMember();
        crewMember.setAirline(new Airline());
        crewMember.getAirline().setName("Airline1");
        crewMember.setRole("Pilot");
        crewMember.setFirst_name("John");
        crewMember.setLast_name("Ionica");
        crewMember.setRegistration_number("W15241");

        when(crewMemberRepository.findAllCrewMembersForAnAirline("Airline1")).thenReturn(List.of(crewMember));

        List<CrewMember> result = crewMemberService.getAllCrewMembersForAnAirline("Airline1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Airline1", result.getFirst().getAirline().getName());
        assertEquals("Pilot", result.getFirst().getRole());
        assertEquals("John", result.getFirst().getFirst_name());
        assertEquals("Ionica", result.getFirst().getLast_name());
        assertEquals("W15241", result.getFirst().getRegistration_number());

        verify(crewMemberRepository).findAllCrewMembersForAnAirline("Airline1");
    }
}
