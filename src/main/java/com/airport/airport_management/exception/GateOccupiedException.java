package com.airport.airport_management.exception;

public class GateOccupiedException extends RuntimeException {
    public GateOccupiedException(String message) {
        super("Gate with name " + message + " is occupied");
    }
}
