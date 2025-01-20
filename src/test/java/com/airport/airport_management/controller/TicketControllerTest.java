package com.airport.airport_management.controller;

import com.airport.airport_management.dto.*;
import com.airport.airport_management.model.*;
import com.airport.airport_management.service.*;
import com.airport.airport_management.utils.FlightStatus;
import com.airport.airport_management.utils.FlightType;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@WebMvcTest(controllers = TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TicketService ticketService;

    @Test
    public void addTicket() throws Exception {
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 0);
        Aircraft aircraft = new Aircraft("HA-LJK", "Boeing 737", 180, airline);
        Gate gate = new Gate(1, "A1", false);
        Flight flight = new Flight("WZZ123", "Budapest", "Bucharest",
                LocalDateTime.of(2025, 1, 1, 1, 1, 0),
                LocalDateTime.of(2025, 1, 1, 2, 1, 1),
                FlightType.DEPARTURE, FlightStatus.SCHEDULED, aircraft, gate);
        Passenger passenger = new Passenger(1, "Maftei", "Valentin", "0731224214", "5121321112312");

        TicketDto ticketDto = new TicketDto("15A", 1, "WZZ123", 100, LocalDateTime.of(2024, 12, 1, 1, 1, 0));
        Ticket ticket = new Ticket(1, "15A", LocalDateTime.of(2024, 12, 1, 1, 1, 0), 100, flight, passenger);

        when(ticketService.addTicket(any())).thenReturn(ticket);

        mockMvc.perform(
                        post("/tickets/add")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.seat_number").value(ticketDto.getSeat_number()))
                .andExpect(jsonPath("$.price").value(ticketDto.getPrice()))
                .andExpect(jsonPath("$.flight.flight_number").value(ticketDto.getFlight_number()))
                .andExpect(jsonPath("$.passenger.id").value(ticketDto.getPassenger_id()))
                .andExpect(jsonPath("$.booking_datetime").value("2024-12-01T01:01:00"));
    }
}
