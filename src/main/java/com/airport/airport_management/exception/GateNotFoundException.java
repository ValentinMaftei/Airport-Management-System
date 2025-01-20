package com.airport.airport_management.exception;

public class GateNotFoundException extends RuntimeException {
    public GateNotFoundException(String gate_name) {
        super("Gate with name " + gate_name + " not found");
    }
}
