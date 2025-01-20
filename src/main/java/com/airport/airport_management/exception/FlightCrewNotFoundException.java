package com.airport.airport_management.exception;

public class FlightCrewNotFoundException extends RuntimeException {
    public FlightCrewNotFoundException(String flight_number) {
        super("Flight Crew for flight number " + flight_number + " not found");
    }
}
