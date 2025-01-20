package com.airport.airport_management.controller;

import com.airport.airport_management.dto.PassengerDto;
import com.airport.airport_management.model.Passenger;
import com.airport.airport_management.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/passengers")
@Validated
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a passenger", description = "Provide passenger details to add a new passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid passenger details")
    })
    public ResponseEntity<Passenger> addPassenger(@RequestBody @Valid PassengerDto passengerRequest) {
        var passenger = passengerService.savePassenger(passengerRequest);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(passenger.getId())
                .toUri();

        return ResponseEntity.created(uri).body(passenger);
    }

    @GetMapping("/{identity_number}")
    @Operation(summary = "Get a passenger", description = "Provide passenger identity_number to get passenger details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger found"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    public ResponseEntity<Passenger> getPassengerById(@PathVariable String identity_number) {
        var passenger = passengerService.getPassengerByIdentity_number(identity_number);
        return ResponseEntity.ok(passenger);
    }
}
