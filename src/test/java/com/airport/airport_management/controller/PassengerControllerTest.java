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

@WebMvcTest(controllers = PassengerController.class)
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PassengerService passengerService;

    @Test
    public void addPassenger() throws Exception {
        PassengerDto passengerDto = new PassengerDto("Maftei", "Valentin", "0731224214", "5121321112312");
        Passenger passenger = new Passenger(1, "Maftei", "Valentin", "0731224214", "5121321112312");

        when(passengerService.savePassenger(any())).thenReturn(passenger);

        mockMvc.perform(
                        post("/passengers/add")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(passengerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name").value(passengerDto.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(passengerDto.getLast_name()))
                .andExpect(jsonPath("$.phone_number").value(passengerDto.getPhone_number()))
                .andExpect(jsonPath("$.identity_number").value(passengerDto.getIdentity_number()));
    }

    @Test
    public void getPassengerByIdentityNumber() throws Exception {
        Passenger passenger = new Passenger(1, "Maftei", "Valentin", "0731224214", "5121321112312");

        when(passengerService.getPassengerByIdentity_number("5121321112312")).thenReturn(passenger);

        mockMvc.perform(
                        get("/passengers/5121321112312")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(passenger)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(passenger.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(passenger.getLast_name()))
                .andExpect(jsonPath("$.phone_number").value(passenger.getPhone_number()))
                .andExpect(jsonPath("$.identity_number").value(passenger.getIdentity_number()));
    }
}
