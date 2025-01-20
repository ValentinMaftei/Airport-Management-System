package com.airport.airport_management.exception;

public class TicketFlightNumberNotFoundException extends RuntimeException {
    public TicketFlightNumberNotFoundException(String flight_number) {
        super("There is no ticket for provided flight number " + flight_number + " not found");
    }
}
