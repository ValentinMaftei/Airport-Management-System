package com.airport.airport_management.service;

import com.airport.airport_management.dto.GateDto;
import com.airport.airport_management.exception.GateNotFoundException;
import com.airport.airport_management.exception.GateOccupiedException;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.Gate;
import com.airport.airport_management.repository.FlightRepository;
import com.airport.airport_management.repository.GateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.airport.airport_management.utils.GateUtil.fromDtoToGate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
public class GateServiceTest {

    @InjectMocks
    private GateService gateService;

    @Mock
    private GateRepository gateRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightService flightService;

    @Test
    void addGate() {
        GateDto gateDto = new GateDto();
        gateDto.setName("105A");
        gateDto.setOccupancy_status(true);

        Gate gate = new Gate();
        gate.setName("105A");
        gate.setOccupancy_status(true);

        when(gateRepository.save(fromDtoToGate(gateDto))).thenReturn(fromDtoToGate(gateDto));

        Gate result = gateService.addGate(gateDto);

        assertNotNull(result);
        assertEquals("105A", result.getName());
        assertTrue(result.isOccupancy_status());

        verify(gateRepository, Mockito.times(1)).save(fromDtoToGate(gateDto));
    }

    @Test
    void getGateByName(){
        Gate gate = new Gate();
        gate.setName("105A");
        gate.setOccupancy_status(true);

        when(gateRepository.findByName("105A")).thenReturn(Optional.of(gate));

        Gate result = gateService.getGateByName("105A");

        assertNotNull(result);
        assertEquals("105A", result.getName());
        assertTrue(result.isOccupancy_status());

        verify(gateRepository, Mockito.times(1)).findByName("105A");
    }

    @Test
    void gateNotFoundException(){
        GateNotFoundException exception = assertThrows(GateNotFoundException.class, () -> gateService.deleteGateByName("105A"));
        assertEquals("Gate with name 105A not found", exception.getMessage());
    }

    @Test
    void changeGateIsCleared() {
        Gate gate = new Gate();
        gate.setName("105A");
        gate.setOccupancy_status(true);

        Flight flight = new Flight();
        flight.setGate(gate);
        flight.setFlight_number("W12345");

        when(gateRepository.findByName("105A")).thenReturn(Optional.of(gate));
        when(flightRepository.findByGateName("105A")).thenReturn(Optional.of(flight));
        when(gateRepository.save(gate)).thenReturn(gate);

        Gate result = gateService.changeGateIsCleared("105A");

        assertNotNull(result);
        assertEquals("105A", result.getName());
        assertFalse(result.isOccupancy_status());

        verify(gateRepository, Mockito.times(1)).findByName("105A");
    }

    @Test
    void getAllAircraftAtGate(){
        Gate gate = new Gate();
        gate.setName("105A");
        gate.setOccupancy_status(true);

        Flight flight = new Flight();
        flight.setGate(gate);
        flight.setFlight_number("W12345");

        Aircraft aircraft = new Aircraft();
        aircraft.setRegistration("HA-LKJ");
        flight.setAircraft(aircraft);

        when(gateRepository.findAllAircraftAtGates()).thenReturn(List.of(aircraft));

        List<Aircraft> result = gateService.getAllAircraftAtGates();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("HA-LKJ", result.getFirst().getRegistration());

        verify(gateRepository, Mockito.times(1)).findAllAircraftAtGates();
    }
}
