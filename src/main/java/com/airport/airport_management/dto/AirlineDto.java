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
public class AirlineDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    @Size(min = 3, max = 3, message = "ICAO code must be 3 characters long")
    @NotNull(message = "Country cannot be null")
    private String icao;
    @NotNull(message = "Country cannot be null")
    private String country;
    @NotNull(message = "Fleet size cannot be null")
    @Min(value = 0, message = "Fleet size must be greater than 0")
    private int fleetSize;
}
