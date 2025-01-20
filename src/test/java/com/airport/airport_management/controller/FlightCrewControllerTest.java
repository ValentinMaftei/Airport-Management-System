package com.airport.airport_management.controller;

import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.dto.FlightCrewDto;
import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.model.*;
import com.airport.airport_management.service.AirlineService;
import com.airport.airport_management.service.CrewMemberService;
import com.airport.airport_management.service.FlightCrewService;
import com.airport.airport_management.service.FlightService;
import com.airport.airport_management.utils.FlightStatus;
import com.airport.airport_management.utils.FlightType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FlightCrewController.class)
public class FlightCrewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FlightCrewService flightCrewService;

    @Test
    public void addFlightCrewController() throws Exception {
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        CrewMember crewMember = new CrewMember(1, "WZZ4512", "Maftei", "Valentin", "Pilot", airline);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Gate gate = new Gate(1, "A1", false);
        FlightCrewDto flightCrewDto = new FlightCrewDto(1, "WZZ123");
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0),
                LocalDateTime.of(2025, 1, 1, 2, 1, 1),
                FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);
        FlightCrewPK flightCrewPK = new FlightCrewPK();

        when(flightCrewService.addFlightCrew(any())).thenReturn(new FlightCrew(flightCrewPK, crewMember, flight));

        mockMvc.perform(
                        post("/flight-crews/add")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(flightCrewDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.crewMember.id").value(flightCrewDto.getCrew_id()))
                .andExpect(jsonPath("$.flight.flight_number").value(flightCrewDto.getFlight_number()));
    }

    @Test
    public void getFlightCrewByFlightNumber() throws Exception {
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        CrewMember crewMember = new CrewMember(1, "WZZ4512", "Maftei", "Valentin", "Pilot", airline);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Gate gate = new Gate(1, "A1", false);
        FlightCrewDto flightCrewDto = new FlightCrewDto(1, "WZZ123");
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0),
                LocalDateTime.of(2025, 1, 1, 2, 1, 1),
                FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);
        FlightCrewPK flightCrewPK = new FlightCrewPK();

        when(flightCrewService.getFlightCrewByFlightNumber(flight.getFlight_number()))
                .thenReturn(Optional.of(List.of(new FlightCrew(flightCrewPK, crewMember, flight))));

        mockMvc.perform(
                        get("/flight-crews/WZZ123")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(flightCrewDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crewMember.id").value(flightCrewDto.getCrew_id()))
                .andExpect(jsonPath("$[0].flight.flight_number").value(flightCrewDto.getFlight_number()));
    }

    @Test
    public void deleteCrewMemberFromFlight() throws Exception {
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        CrewMember crewMember = new CrewMember(1, "WZZ4512", "Maftei", "Valentin", "Pilot", airline);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Gate gate = new Gate(1, "A1", false);
        FlightCrewDto flightCrewDto = new FlightCrewDto(1, "WZZ123");
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0),
                LocalDateTime.of(2025, 1, 1, 2, 1, 1),
                FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);
        FlightCrewPK flightCrewPK = new FlightCrewPK();

        mockMvc.perform(
                        delete("/flight-crews/1/WZZ123")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(flightCrewDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Crew member deleted successfully"));
    }
}
