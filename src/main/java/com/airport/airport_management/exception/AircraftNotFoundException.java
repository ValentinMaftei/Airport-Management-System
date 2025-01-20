package com.airport.airport_management.exception;

public class AircraftNotFoundException extends RuntimeException {
    public AircraftNotFoundException(String registration) {
        super("Aircraft with registration " + registration + " not found");
    }
}
