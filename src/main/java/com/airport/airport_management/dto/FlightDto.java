package com.airport.airport_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class FlightDto {
    @Size(min = 4, max = 7, message = "Flight number must be 5 characters long")
    @NotNull(message = "Flight number cannot be null")
    private String flightNumber;
    @NotNull(message = "Origin cannot be null")
    private String origin;
    @NotNull(message = "Destination cannot be null")
    private String destination;
    @NotNull(message = "Departure time cannot be null")
    private LocalDateTime departure_time;
    @NotNull(message = "Arrival time cannot be null")
    private LocalDateTime arrival_time;
    @NotNull(message = "Aircraft id cannot be null")
    private String aircraft_id;
    @NotNull(message = "Airline id cannot be null")
    private int airline_id;
    @NotNull(message = "Type cannot be null")
    private String type;
}
