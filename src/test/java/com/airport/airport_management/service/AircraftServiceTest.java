package com.airport.airport_management.service;

import com.airport.airport_management.dto.AircraftDto;
import com.airport.airport_management.exception.AircraftNotFoundException;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.repository.AircraftRepository;
import com.airport.airport_management.repository.AirlineRepository;
import com.airport.airport_management.utils.AircraftUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceTest {

    @InjectMocks
    private AircraftService aircraftService;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AircraftUtil aircraftUtil;

    @Test
    void aircraft_create() {
        AircraftDto aircraftDto = new AircraftDto();
        aircraftDto.setRegistration("ABC123");
        aircraftDto.setAirline_id(1);

        Aircraft aircraft = new Aircraft();
        aircraft.setRegistration("ABC123");

        Airline airline = new Airline();
        airline.setId(1);
        airline.setFleetSize(10);

        when(airlineRepository.findById(1)).thenReturn(Optional.of(airline));
        when(aircraftUtil.fromDtoToAircraft(aircraftDto)).thenReturn(aircraft);
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);

        Aircraft result = aircraftService.addAircraft(aircraftDto);

        assertNotNull(result);
        assertEquals("ABC123", result.getRegistration());
        assertEquals(11, airline.getFleetSize());

        verify(airlineRepository, times(1)).findById(1);
        verify(aircraftRepository, times(1)).save(aircraft);
    }

    @Test
    void aircraftNotFoundException() {
        AircraftNotFoundException exception = assertThrows(AircraftNotFoundException.class, () -> aircraftService.getAircraftByRegistration("ABC123"));
        assertEquals("Aircraft with registration ABC123 not found", exception.getMessage());
    }

    @Test
    void getAircraftByRegistration() {
        Aircraft aircraft = new Aircraft();
        aircraft.setRegistration("ABC123");

        when(aircraftRepository.findByRegistration("ABC123")).thenReturn(Optional.of(aircraft));

        Aircraft result = aircraftService.getAircraftByRegistration("ABC123");

        assertNotNull(result);
        assertEquals("ABC123", result.getRegistration());

        verify(aircraftRepository, times(1)).findByRegistration("ABC123");
    }
}
