package com.airport.airport_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler({TicketFlightNumberNotFoundException.class})
    public ResponseEntity<String> handle(TicketFlightNumberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({PassengerNotFoundException.class})
    public ResponseEntity<String> handle(PassengerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({FlightFullException.class})
    public ResponseEntity<String> handle(FlightFullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({SeatAlreadyTakenException.class})
    public ResponseEntity<String> handle(SeatAlreadyTakenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({AircraftNotFoundException.class})
    public ResponseEntity<String> handle(AircraftNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({AirlineNotFoundException.class})
    public ResponseEntity<String> handle(AirlineNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({CrewMemberNotFoundException.class})
    public ResponseEntity<String> handle(CrewMemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({FlightCrewNotFoundException.class})
    public ResponseEntity<String> handle(FlightCrewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({FlightNotFoundException.class})
    public ResponseEntity<String> handle(FlightNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({GateNotFoundException.class})
    public ResponseEntity<String> handle(GateNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({GateOccupiedException.class})
    public ResponseEntity<String> handle(GateOccupiedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({TicketPassengerNotFoundException.class})
    public ResponseEntity<String> handle(TicketPassengerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
