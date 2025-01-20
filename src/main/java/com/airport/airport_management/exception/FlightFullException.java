package com.airport.airport_management.exception;

public class FlightFullException extends RuntimeException {
    public FlightFullException(String flight_number) {
        super("Flight with flight number " + flight_number + " is full");
    }
}
