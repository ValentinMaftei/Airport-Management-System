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

public class CrewMemberDto {
    @NotNull(message = "First cannot be null")
    private String first_name;
    @NotNull(message = "Last_name cannot be null")
    private String last_name;
    @NotNull(message = "Role cannot be null")
    private String role;
    @NotNull(message = "Airline id cannot be null")
    private int airline_id;
    @Size(min = 6, max = 10, message = "Registration number must be 6 characters long")
    @NotNull(message = "Registration number cannot be null")
    private String registration_number;
}
