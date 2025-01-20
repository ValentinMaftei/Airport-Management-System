package com.airport.airport_management.service;

import com.airport.airport_management.dto.PassengerDto;
import com.airport.airport_management.exception.PassengerNotFoundException;
import com.airport.airport_management.model.Passenger;
import com.airport.airport_management.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.airport.airport_management.utils.PassengerUtil.fromDtoToPassenger;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger savePassenger(PassengerDto passenger) {
        return passengerRepository.save(fromDtoToPassenger(passenger));
    }

    public Passenger getPassengerByIdentity_number(String identity_number) {
        Optional<Passenger> passengerOptional = passengerRepository.findByIdentityNumber(identity_number);
        if (passengerOptional.isPresent()) {
            return passengerOptional.get();
        } else {
            throw new PassengerNotFoundException(identity_number);
        }
    }
}
