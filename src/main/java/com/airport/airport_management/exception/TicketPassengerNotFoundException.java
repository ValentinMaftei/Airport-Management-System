package com.airport.airport_management.exception;

public class TicketPassengerNotFoundException extends RuntimeException {
    public TicketPassengerNotFoundException(String identity_number) {
        super("There is no ticket for provided passenger identity_number" + identity_number + " not found");
    }
}
