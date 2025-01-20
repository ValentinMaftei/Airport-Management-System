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

public class PassengerDto {
    @NotNull(message = "Name cannot be null")
    private String first_name;
    @NotNull(message = "Surname cannot be null")
    private String last_name;
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    @NotNull(message = "Phone number cannot be null")
    private String phone_number;
    @Size(min = 10, max = 14, message = "Identity number must be 11 characters")
    @NotNull(message = "Identity number cannot be null")
    private String identity_number;
}
