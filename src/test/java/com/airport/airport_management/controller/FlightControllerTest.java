package com.airport.airport_management.controller;

import com.airport.airport_management.dto.AirlineDto;
import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.model.*;
import com.airport.airport_management.service.AirlineService;
import com.airport.airport_management.service.CrewMemberService;
import com.airport.airport_management.service.FlightService;
import com.airport.airport_management.utils.FlightStatus;
import com.airport.airport_management.utils.FlightType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FlightController.class)
public class FlightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FlightService flightService;

    @Test
    public void addFlight() throws Exception {
        FlightDto flightDto = new FlightDto("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 2, 1, 1), "HA-LJK", 1, "DEPARTURE");

        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        Gate gate = new Gate(1, "A1", false);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 2, 1, 1), FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);

        when(flightService.addFlight(any())).thenReturn(flight);

        mockMvc.perform(
                        post("/flights/add")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(flightDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flight_number").value(flightDto.getFlightNumber()))
                .andExpect(jsonPath("$.origin").value(flightDto.getOrigin()))
                .andExpect(jsonPath("$.destination").value(flightDto.getDestination()))
                .andExpect(jsonPath("$.departure_time").value("2025-01-01T01:01:00"))
                .andExpect(jsonPath("$.arrival_time").value("2025-01-01T02:01:01"))
                .andExpect(jsonPath("$.type").value(flightDto.getType()));
    }

    @Test
    public void getFlightByFlightNumber() throws Exception {
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        Gate gate = new Gate(1, "A1", false);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 2, 1, 1), FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);

        when(flightService.getFlightbyFlightNumber("WZZ123")).thenReturn(flight);

        mockMvc.perform(
                get("/flights/WZZ123")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flight_number").value("WZZ123"))
                .andExpect(jsonPath("$.origin").value("Budapest"))
                .andExpect(jsonPath("$.destination").value("Bucharest"))
                .andExpect(jsonPath("$.departure_time").value("2025-01-01T01:01:00"))
                .andExpect(jsonPath("$.arrival_time").value("2025-01-01T02:01:01"))
                .andExpect(jsonPath("$.type").value("DEPARTURE"));
    }

    @Test
    public void changeGateForFlight() throws Exception {
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        Gate gate = new Gate(1, "A1", false);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 2, 1, 1), FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);

        when(flightService.changeFlightGate("WZZ123", "A2")).thenReturn(flight);

        mockMvc.perform(
                put("/flights/change_gate/WZZ123?new_gate_name=A2")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flight_number").value("WZZ123"))
                .andExpect(jsonPath("$.origin").value("Budapest"))
                .andExpect(jsonPath("$.destination").value("Bucharest"))
                .andExpect(jsonPath("$.departure_time").value("2025-01-01T01:01:00"))
                .andExpect(jsonPath("$.arrival_time").value("2025-01-01T02:01:01"))
                .andExpect(jsonPath("$.type").value("DEPARTURE"));
    }

    @Test
    public void changeFlightStatus() throws Exception {
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        Gate gate = new Gate(1, "A1", false);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0), LocalDateTime.of(2025, 1, 1, 2, 1, 1), FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);

        when(flightService.changeFlightStatus("WZZ123", "CANCELLED")).thenReturn(flight);

        mockMvc.perform(
                put("/flights/change_status/WZZ123?status=CANCELLED")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flight_number").value("WZZ123"))
                .andExpect(jsonPath("$.origin").value("Budapest"))
                .andExpect(jsonPath("$.destination").value("Bucharest"))
                .andExpect(jsonPath("$.departure_time").value("2025-01-01T01:01:00"))
                .andExpect(jsonPath("$.arrival_time").value("2025-01-01T02:01:01"))
                .andExpect(jsonPath("$.type").value("DEPARTURE"));
    }
}
