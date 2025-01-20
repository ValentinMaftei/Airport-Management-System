package com.airport.airport_management.exception;

public class AirlineNotFoundException extends RuntimeException {
    public AirlineNotFoundException(String icao) {
        super("Airline with ICAO " + icao + " not found");
    }
}
