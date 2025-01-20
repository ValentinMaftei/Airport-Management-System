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

public class TicketDto {
    @Size(min = 2, message = "Seat number must have at two one characters")
    @NotNull(message = "Seat number cannot be null")
    private String seat_number;
    @NotNull(message = "Passenger id cannot be null")
    private int passenger_id;
    @NotNull(message = "Flight id cannot be null")
    private String flight_number;
    @NotNull(message = "Price cannot be null")
    private double price;
    @NotNull(message = "Booking datetime cannot be null")
    private LocalDateTime booking_datetime;
}
