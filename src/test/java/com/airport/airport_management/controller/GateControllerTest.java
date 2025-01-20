package com.airport.airport_management.controller;

import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.dto.FlightCrewDto;
import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.dto.GateDto;
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

@WebMvcTest(controllers = GateController.class)
public class GateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GateService gateService;

    @Test
    public void addGate() throws Exception {
        GateDto gateDto = new GateDto("105A", false);
        Gate gate = new Gate(1, "105A", false);

        when(gateService.addGate(any())).thenReturn(gate);

        mockMvc.perform(
                        post("/gates/add")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(gateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(gateDto.getName()));
    }

    @Test
    public void getGateByGateName() throws Exception {
        Gate gate = new Gate(1, "105A", false);

        when(gateService.getGateByName("105A")).thenReturn(gate);

        mockMvc.perform(get("/gates/105A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(gate.getName()));
    }

    @Test
    public void deleteGateByGateName() throws Exception {
        Gate gate = new Gate(1, "105A", false);

        mockMvc.perform(delete("/gates/105A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Gate deleted successfully"));
    }

    @Test
    public void changeIsCleared() throws Exception {
        Gate gate = new Gate(1, "105A", false);

        when(gateService.changeGateIsCleared("105A")).thenReturn(gate);

        mockMvc.perform(put("/gates/change_is_cleared/105A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(gate.getName()));
    }
}
