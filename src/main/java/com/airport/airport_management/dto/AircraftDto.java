package com.airport.airport_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AircraftDto {
    @Size(min = 1, max = 8, message = "Registration must be between 1 and 10 characters")
    @NotNull(message = "Registration cannot be null")
    private String registration;
    @NotNull(message = "Model cannot be null")
    private String model;
    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;
    @NotNull(message = "Airline id cannot be null")
    private int airline_id;
}
