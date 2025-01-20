package com.airport.airport_management.controller;

import com.airport.airport_management.dto.AircraftDto;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.service.AircraftService;
import com.airport.airport_management.service.AirlineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AircraftController.class)
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AircraftService aircraftService;

    @MockitoBean
    private AirlineService airlineService;

    @Test
    public void addAircraft() throws Exception {
        AircraftDto aircraftDto = new AircraftDto("HA-LJK", "Airbus A320", 180, 1);

        when(aircraftService.addAircraft(any())).thenReturn(new Aircraft("HA-LJK", "Airbus A320", 180, new Airline(1, "Wizz Air", "WZZ", "Hungary", 0)));

        mockMvc.perform(
                        post("/aircrafts/add")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(aircraftDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registration").value(aircraftDto.getRegistration()))
                .andExpect(jsonPath("$.model").value(aircraftDto.getModel()))
                .andExpect(jsonPath("$.capacity").value(aircraftDto.getCapacity()));
    }

    @Test
    public void getAircraftByRegistration() throws Exception {
        Aircraft aircraft = new Aircraft("HA-LJK", "Airbus A320", 180, new Airline(1, "Wizz Air", "WZZ", "Hungary", 0));

        when(aircraftService.getAircraftByRegistration("HA-LJK")).thenReturn(aircraft);

        mockMvc.perform(
                        get("/aircrafts/HA-LJK")
                                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value(aircraft.getRegistration()))
                .andExpect(jsonPath("$.model").value(aircraft.getModel()))
                .andExpect(jsonPath("$.capacity").value(aircraft.getCapacity()));
    }
}
