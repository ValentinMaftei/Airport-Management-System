package com.airport.airport_management.exception;

public class SeatAlreadyTakenException extends RuntimeException {
    public SeatAlreadyTakenException(String seat_number) {
        super("Seat with seat number " + seat_number + " is already taken");
    }
}
