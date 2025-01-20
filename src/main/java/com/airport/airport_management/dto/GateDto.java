package com.airport.airport_management.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class GateDto {
    @Min(value = 1, message = "Gate number must be greater than 0")
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Occupany status cannot be null")
    private boolean occupancy_status;
}
