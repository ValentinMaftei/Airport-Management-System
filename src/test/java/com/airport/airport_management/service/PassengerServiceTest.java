package com.airport.airport_management.service;


import com.airport.airport_management.dto.PassengerDto;
import com.airport.airport_management.model.Passenger;
import com.airport.airport_management.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

    @InjectMocks
    private PassengerService passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Test
    void addPassenger() {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setIdentity_number("123456789");
        passengerDto.setFirst_name("Marcel");
        passengerDto.setLast_name("Ionut");
        passengerDto.setPhone_number("1234567890");

        Passenger passenger = new Passenger();
        passenger.setIdentity_number("123456789");
        passenger.setFirst_name("Marcel");
        passenger.setLast_name("Ionut");
        passenger.setPhone_number("1234567890");

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        Passenger result = passengerService.savePassenger(passengerDto);

        assertNotNull(result);
        assertEquals("123456789", result.getIdentity_number());
        assertEquals("Marcel", result.getFirst_name());
        assertEquals("Ionut", result.getLast_name());
        assertEquals("1234567890", result.getPhone_number());

        verify(passengerRepository).save(passenger);
    }

    @Test
    void getPassengerByIdentity_number() {
        Passenger passenger = new Passenger();
        passenger.setIdentity_number("123456789");
        passenger.setFirst_name("Marcel");
        passenger.setLast_name("Ionut");
        passenger.setPhone_number("1234567890");

        when(passengerRepository.findByIdentityNumber("123456789")).thenReturn(java.util.Optional.of(passenger));

        Passenger result = passengerService.getPassengerByIdentity_number("123456789");

        assertNotNull(result);
        assertEquals("123456789", result.getIdentity_number());
        assertEquals("Marcel", result.getFirst_name());
        assertEquals("Ionut", result.getLast_name());
        assertEquals("1234567890", result.getPhone_number());

        verify(passengerRepository).findByIdentityNumber("123456789");
    }
}
