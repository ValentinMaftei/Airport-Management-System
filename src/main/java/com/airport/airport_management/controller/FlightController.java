package com.airport.airport_management.controller;

import com.airport.airport_management.dto.FlightDto;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/flights")
@Validated
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a flight", description = "Provide flight details to add a new flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight added"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    public ResponseEntity<Flight> addFlight(@RequestBody @Valid FlightDto flightRequest) {
        var flight = flightService.addFlight(flightRequest);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(flight.getFlight_number())
                .toUri();

        return ResponseEntity.created(uri).body(flight);
    }

    @GetMapping("/{flight_number}")
    @Operation(summary = "Get flight by flight number", description = "Provide a flight number to look up specific flight details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight found"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
    })
    public ResponseEntity<Flight> getFlightByFlightNumber(@PathVariable String flight_number) {
        var flight = flightService.getFlightbyFlightNumber(flight_number);
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/change_gate/{flight_number}")
    @Operation(summary = "Assign gate for a flight", description = "Provide a flight number and a gate number to assign the gate for a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gate assigned"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
    })
    public ResponseEntity<Flight> changeGateForFlight(@PathVariable String flight_number, @RequestParam String new_gate_name) {
        var flight = flightService.changeFlightGate(flight_number, new_gate_name);
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/change_status/{flight_number}")
    @Operation(summary = "Change flight status", description = "Provide a flight number and a status to change the status of a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status changed"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
    })
    public ResponseEntity<Flight> changeFlightStatus(@PathVariable String flight_number, @RequestParam String status) {
        var flight = flightService.changeFlightStatus(flight_number, status);
        return ResponseEntity.ok(flight);
    }
}
