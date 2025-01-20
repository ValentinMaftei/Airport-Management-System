package com.airport.airport_management.controller;

import com.airport.airport_management.dto.AirlineDto;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.service.AirlineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AirlineController.class)
public class AirlineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AirlineService airlineService;

    @Test
    public void addAirline() throws Exception {
        AirlineDto airlineDto = new AirlineDto("Wizz Air", "WZZ", "Hungary", 100);

        when(airlineService.addAirline(any())).thenReturn(new Airline(1, "Wizz Air", "WZZ", "Hungary", 100));

        mockMvc.perform(
                post("/airlines/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(airlineDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(airlineDto.getName()))
                .andExpect(jsonPath("$.icao").value(airlineDto.getIcao()))
                .andExpect(jsonPath("$.country").value(airlineDto.getCountry()))
                .andExpect(jsonPath("$.fleetSize").value(airlineDto.getFleetSize()));
    }

    @Test
    public void getAirlineByIcao() throws Exception{
        Airline airline = new Airline(1, "Wizz Air", "WZZ", "Hungary", 100);

        when(airlineService.getAirlineByIcao("WZZ")).thenReturn(airline);

        mockMvc.perform(
                get("/airlines/WZZ")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(airline.getName()))
                .andExpect(jsonPath("$.icao").value(airline.getIcao()))
                .andExpect(jsonPath("$.country").value(airline.getCountry()))
                .andExpect(jsonPath("$.fleetSize").value(airline.getFleetSize()));
    }
}
