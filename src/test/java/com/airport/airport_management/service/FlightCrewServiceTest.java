package com.airport.airport_management.service;

import com.airport.airport_management.dto.FlightCrewDto;
import com.airport.airport_management.exception.FlightCrewNotFoundException;
import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.FlightCrew;
import com.airport.airport_management.repository.FlightCrewRepository;
import com.airport.airport_management.utils.FlightCrewUtil;
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
public class FlightCrewServiceTest {

    @InjectMocks
    private FlightCrewService flightCrewService;

    @Mock
    private FlightCrewRepository flightCrewRepository;

    @Mock
    private FlightCrewUtil flightCrewUtil;

    @Test
    void flight_crew_add() {
        FlightCrewDto flightCrewDto = new FlightCrewDto();
        flightCrewDto.setCrew_id(1);
        flightCrewDto.setFlight_number("W12345");

        FlightCrew flightCrew = new FlightCrew();
        flightCrew.setCrewMember(new CrewMember());
        flightCrew.getCrewMember().setId(1);
        flightCrew.setFlight(new Flight());
        flightCrew.getFlight().setFlight_number("W12345");

        when(flightCrewUtil.fromDtoToFlightCrew(flightCrewDto)).thenReturn(flightCrew);
        when(flightCrewRepository.save(flightCrew)).thenReturn(flightCrew);

        FlightCrew result = flightCrewService.addFlightCrew(flightCrewDto);

        assertNotNull(result);
        assertEquals(1, result.getCrewMember().getId());
        assertEquals("W12345", result.getFlight().getFlight_number());

        verify(flightCrewRepository).save(flightCrewUtil.fromDtoToFlightCrew(flightCrewDto));
    }

    @Test
    void getFlightCrewByFlightNumber() {
        FlightCrew flightCrew = new FlightCrew();
        flightCrew.setCrewMember(new CrewMember());
        flightCrew.getCrewMember().setId(1);
        flightCrew.setFlight(new Flight());
        flightCrew.getFlight().setFlight_number("W12345");

        List<FlightCrew> flightCrewList = List.of(flightCrew);

        when(flightCrewRepository.findFlightCrewByFlightNumber("W12345")).thenReturn(Optional.of(flightCrewList));

        Optional<List<FlightCrew>> result = flightCrewService.getFlightCrewByFlightNumber("W12345");

        assertNotNull(result);
        result.ifPresent(flightCrews -> assertEquals(1, flightCrews.size()));
        result.ifPresent(flightCrews -> assertEquals(1, flightCrews.getFirst().getCrewMember().getId()));

        verify(flightCrewRepository).findFlightCrewByFlightNumber("W12345");
    }

    @Test
    void deleteCrewMemberFromFlight() {
        flightCrewService.deleteCrewMemberFromFlight(1, "W12345");

        verify(flightCrewRepository).deleteCrewMemberFromFlight(1, "W12345");
    }

    @Test
    void flightCrewNotFoundException() {
        FlightCrewNotFoundException exception = assertThrows(FlightCrewNotFoundException.class, () -> flightCrewService.getFlightCrewByFlightNumber("W12345"));
        assertEquals("Flight Crew for flight number W12345 not found", exception.getMessage());
    }
}
