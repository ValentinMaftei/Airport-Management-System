package com.airport.airport_management.exception;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String flight_number) {
        super("Flight with flight number " + flight_number + " not found");
    }
}
