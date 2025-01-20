package com.airport.airport_management.utils;

import com.airport.airport_management.dto.PassengerDto;
import com.airport.airport_management.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerUtil {

    public static Passenger fromDtoToPassenger(PassengerDto passengerDto) {
        return Passenger.builder()
                .first_name(passengerDto.getFirst_name())
                .last_name(passengerDto.getLast_name())
                .identity_number(passengerDto.getIdentity_number())
                .phone_number(passengerDto.getPhone_number())
                .build();
    }
}
