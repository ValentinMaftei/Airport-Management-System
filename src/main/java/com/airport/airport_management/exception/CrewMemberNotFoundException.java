package com.airport.airport_management.exception;

public class CrewMemberNotFoundException extends RuntimeException {
    public CrewMemberNotFoundException(String registration_number) {
        super("Crew member with registration number " + registration_number + " not found");
    }
}
