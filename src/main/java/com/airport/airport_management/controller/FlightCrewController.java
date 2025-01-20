package com.airport.airport_management.controller;

import com.airport.airport_management.dto.FlightCrewDto;
import com.airport.airport_management.model.FlightCrew;
import com.airport.airport_management.service.FlightCrewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flight-crews")
@Validated

public class FlightCrewController {
    private final FlightCrewService flightCrewService;

    public FlightCrewController(FlightCrewService flightCrewService) {
        this.flightCrewService = flightCrewService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a flight crew", description = "Provide flight crew details to add a new flight crew")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight crew added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<FlightCrew> addFlightCrew(@RequestBody @Valid FlightCrewDto flightCrewRequest) {
        var flightCrew = flightCrewService.addFlightCrew(flightCrewRequest);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(flightCrew.getFlight_crew_id())
                .toUri();

        return ResponseEntity.created(uri).body(flightCrew);
    }

    @GetMapping("/{flight_number}")
    @Operation(summary = "Get flight crew by flight number", description = "Provide a flight number to look up specific flight crew details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight crew found"),
            @ApiResponse(responseCode = "404", description = "Flight crew not found")
    })
    public ResponseEntity<Optional<List<FlightCrew>>> getFlightCrewByFlightNumber(@PathVariable String flight_number) {
        var flightCrew = flightCrewService.getFlightCrewByFlightNumber(flight_number);
        return ResponseEntity.ok(flightCrew);
    }

    @DeleteMapping("/{crew_id}/{flight_number}")
    @Operation(summary = "Delete crew member from flight", description = "Provide a crew id and a flight number to delete a crew member from a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crew member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crew member not found")
    })
    public ResponseEntity<String> deleteCrewMemberFromFlight(@PathVariable int crew_id, @PathVariable String flight_number) {
        flightCrewService.deleteCrewMemberFromFlight(crew_id, flight_number);
        return ResponseEntity.ok("Crew member deleted successfully");
    }
}
