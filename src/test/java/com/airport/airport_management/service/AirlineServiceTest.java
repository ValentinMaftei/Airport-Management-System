package com.airport.airport_management.service;

import com.airport.airport_management.dto.AirlineDto;
import com.airport.airport_management.exception.AirlineNotFoundException;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.repository.AirlineRepository;
import com.airport.airport_management.utils.AirlineUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.airport.airport_management.utils.AirlineUtil.fromDtoToAirline;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceTest {

    @InjectMocks
    private AirlineService airlineService;

    @Mock
    private AirlineRepository airlineRepository;

    @Test
    void airline_create() {
        AirlineDto airlineDto = new AirlineDto();
        airlineDto.setName("Airline1");
        airlineDto.setCountry("Country1");
        airlineDto.setFleetSize(10);
        airlineDto.setIcao("ICAO1");

        AirlineDto savedAirlineDto = new AirlineDto();
        savedAirlineDto.setName("Airline1");
        savedAirlineDto.setCountry("Country1");
        savedAirlineDto.setFleetSize(10);
        savedAirlineDto.setIcao("ICAO1");

        when(airlineRepository.save(fromDtoToAirline(airlineDto))).thenReturn(fromDtoToAirline(airlineDto));

        Airline result = airlineService.addAirline(airlineDto);

        assertNotNull(result);
        assertEquals("Airline1", result.getName());
        assertEquals("Country1", result.getCountry());
        assertEquals(10, result.getFleetSize());
        assertEquals("ICAO1", result.getIcao());

        verify(airlineRepository, times(1)).save(fromDtoToAirline(airlineDto));
    }

    @Test
    void airline_get_by_icao(){
        Airline airline = new Airline();
        airline.setName("Airline1");
        airline.setCountry("Country1");
        airline.setFleetSize(10);
        airline.setIcao("ICAO1");

        when(airlineRepository.findByIcao("ICAO1")).thenReturn(Optional.of(airline));

        Airline result = airlineService.getAirlineByIcao("ICAO1");

        assertNotNull(result);
        assertEquals("Airline1", result.getName());
        assertEquals("Country1", result.getCountry());
        assertEquals(10, result.getFleetSize());
        assertEquals("ICAO1", result.getIcao());

        verify(airlineRepository, times(1)).findByIcao("ICAO1");
    }

    @Test
    void airlineNotFoundException(){
        AirlineNotFoundException exception = assertThrows(AirlineNotFoundException.class, () -> airlineService.getAirlineByIcao("ICAO1"));
        assertEquals("Airline with ICAO ICAO1 not found", exception.getMessage());
    }
}
