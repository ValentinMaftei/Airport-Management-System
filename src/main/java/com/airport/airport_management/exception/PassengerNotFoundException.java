package com.airport.airport_management.exception;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(String identity_number) {
        super("Passenger with identity number " + identity_number + " not found");
    }
}
