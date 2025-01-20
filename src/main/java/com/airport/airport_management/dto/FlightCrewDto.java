package com.airport.airport_management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlightCrewDto {
    @NotNull(message = "Crew id cannot be null")
    private int crew_id;
    @NotNull(message = "Flight id cannot be null")
    private String flight_number;
}
