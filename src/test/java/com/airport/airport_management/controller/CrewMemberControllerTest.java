package com.airport.airport_management.controller;

import com.airport.airport_management.dto.AirlineDto;
import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.service.AirlineService;
import com.airport.airport_management.service.CrewMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CrewMemberController.class)
public class CrewMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CrewMemberService crewMemberService;

    @Test
    public void addCrewMember() throws Exception{
        CrewMemberDto crewMemberDto = new CrewMemberDto("Maftei", "Valentin", "Pilot", 1, "WZZ4512");

        when(crewMemberService.addCrewMember(any())).thenReturn(new CrewMember(1, "WZZ4512", "Maftei", "Valentin", "Pilot", new Airline(1, "Wizz Air", "WZZ", "Hungary", 0)));

        mockMvc.perform(
                post("/crew-members/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewMemberDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registration_number").value(crewMemberDto.getRegistration_number()))
                .andExpect(jsonPath("$.first_name").value(crewMemberDto.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(crewMemberDto.getLast_name()))
                .andExpect(jsonPath("$.role").value(crewMemberDto.getRole()));
    }

    @Test
    public void getCrewMemberByRegistrationNumber() throws Exception {
        CrewMemberDto crewMemberDto = new CrewMemberDto("Maftei", "Valentin", "Pilot", 1, "WZZ4512");

        when(crewMemberService.findByRegistrationNumber(crewMemberDto.getRegistration_number())).thenReturn(new CrewMember(1, "WZZ4512", "Maftei", "Valentin", "Pilot", new Airline(1, "Wizz Air", "WZZ", "Hungary", 0)));

        mockMvc.perform(
                get("/crew-members/WZZ4512")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration_number").value(crewMemberDto.getRegistration_number()))
                .andExpect(jsonPath("$.first_name").value(crewMemberDto.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(crewMemberDto.getLast_name()))
                .andExpect(jsonPath("$.role").value(crewMemberDto.getRole()));
    }

    @Test
    public void getCrewMembersByAirlineName() throws Exception {
        CrewMember crewMember = new CrewMember(1, "WZZ4512", "Maftei", "Valentin", "Pilot", new Airline(1, "Wizz Air", "WZZ", "Hungary", 0));
        CrewMember crewMember1 = new CrewMember(2, "WZZ4513", "Maftei", "Valentin", "Pilot", new Airline(1, "Wizz Air", "WZZ", "Hungary", 0));

        when(crewMemberService.getAllCrewMembersForAnAirline("Wizz Air")).thenReturn(List.of(new CrewMember[]{crewMember, crewMember1}));

        mockMvc.perform(
                get("/crew-members/all/Wizz Air")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].registration_number").value(crewMember.getRegistration_number()))
                .andExpect(jsonPath("$[0].first_name").value(crewMember.getFirst_name()))
                .andExpect(jsonPath("$[0].last_name").value(crewMember.getLast_name()))
                .andExpect(jsonPath("$[0].role").value(crewMember.getRole()))
                .andExpect(jsonPath("$[1].registration_number").value(crewMember1.getRegistration_number()))
                .andExpect(jsonPath("$[1].first_name").value(crewMember1.getFirst_name()))
                .andExpect(jsonPath("$[1].last_name").value(crewMember1.getLast_name()))
                .andExpect(jsonPath("$[1].role").value(crewMember1.getRole()));
    }
}
