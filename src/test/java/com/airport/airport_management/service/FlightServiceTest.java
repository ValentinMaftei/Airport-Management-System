package com.airport.airport_management.service;

import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.exception.FlightNotFoundException;
import com.airport.airport_management.exception.GateOccupiedException;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.Gate;
import com.airport.airport_management.repository.FlightRepository;
import com.airport.airport_management.repository.GateRepository;
import com.airport.airport_management.utils.FlightStatus;
import com.airport.airport_management.utils.FlightType;
import com.airport.airport_management.utils.FlightUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightUtil flightUtil;

    @Mock
    private GateRepository gateRepository;

    void addFlight() {
        FlightDto flightDto = new FlightDto();
        flightDto.setFlightNumber("W12345");
        flightDto.setType("DEPARTURE");
        flightDto.setAircraft_id("HA-LKJ");
        flightDto.setOrigin("Budapest");
        flightDto.setDestination("London");
        flightDto.setDeparture_time(LocalDateTime.of(2021, 12, 12, 12, 0, 0));
        flightDto.setArrival_time(LocalDateTime.of(2021, 12, 12, 14, 0, 0));

        Flight flight = new Flight();
        flight.setFlight_number("W12345");
        flight.setType(FlightType.DEPARTURE);
        flight.setAircraft(new Aircraft());
        flight.getAircraft().setRegistration("HA-LKJ");
        flight.setOrigin("Budapest");
        flight.setDestination("London");
        flight.setDeparture_time(LocalDateTime.of(2021, 12, 12, 12, 0, 0));
        flight.setArrival_time(LocalDateTime.of(2021, 12, 12, 14, 0, 0));

        when(flightUtil.fromDtoToFlight(flightDto)).thenReturn(flight);
        when(flightRepository.save(flight)).thenReturn(flight);

        Flight result = flightService.addFlight(flightDto);

        assertNotNull(result);
        assertEquals("W12345", result.getFlight_number());
        assertEquals(FlightType.DEPARTURE, result.getType());
        assertEquals("HA-LKJ", result.getAircraft().getRegistration());
        assertEquals("Budapest", result.getOrigin());
        assertEquals("London", result.getDestination());
        assertEquals(LocalDateTime.of(2025, 1, 12, 12, 0, 0), result.getDeparture_time());
        assertEquals(LocalDateTime.of(2025, 1, 12, 14, 0, 0), result.getArrival_time());

        verify(flightRepository).save(flightUtil.fromDtoToFlight(flightDto));
    }

    @Test
    void getFlightbyFlightNumber() {
        Flight flight = new Flight();
        flight.setFlight_number("W12345");

        when(flightRepository.findByFlightNumber("W12345")).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlightbyFlightNumber("W12345");

        assertNotNull(result);
        assertEquals("W12345", result.getFlight_number());

        verify(flightRepository).findByFlightNumber("W12345");
    }

    @Test
    void changeFlightGate() {
        Flight flight = new Flight();
        flight.setFlight_number("W12345");
        flight.setGate(new Gate());
        flight.getGate().setName("A2");

        Gate gate = new Gate();
        gate.setName("A2");

        when(flightRepository.findByFlightNumber("W12345")).thenReturn(Optional.of(flight));
        when(gateRepository.findByName("A2")).thenReturn(Optional.of(gate));
        when(flightRepository.save(flight)).thenReturn(flight);

        Flight result = flightService.changeFlightGate("W12345", "A2");

        assertNotNull(result);
        assertEquals("W12345", result.getFlight_number());
        assertEquals("A2", result.getGate().getName());

        verify(flightRepository).findByFlightNumber("W12345");
        verify(gateRepository).findByName("A2");
        verify(flightRepository).save(flight);
    }

    @Test
    void changeFlightStatus(){
        Flight flight = new Flight();
        flight.setFlight_number("W12345");
        flight.setStatus(FlightStatus.SCHEDULED);

        when(flightRepository.findByFlightNumber("W12345")).thenReturn(Optional.of(flight));
        when(flightRepository.save(flight)).thenReturn(flight);

        Flight result = flightService.changeFlightStatus("W12345", "DELAYED");

        assertNotNull(result);
        assertEquals("W12345", result.getFlight_number());
        assertEquals(FlightStatus.DELAYED, result.getStatus());

        verify(flightRepository).findByFlightNumber("W12345");
        verify(flightRepository).save(flight);
    }

    @Test
    void flightNotFoundException(){
        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> flightService.getFlightbyFlightNumber("W12345"));
        assertEquals("Flight with flight number W12345 not found", exception.getMessage());
    }
}
