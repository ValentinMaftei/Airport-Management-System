package com.airport.airport_management.controller;

import com.airport.airport_management.dto.AircraftDto;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.service.AircraftService;
import com.airport.airport_management.service.AirlineService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.*;

@RestController
@RequestMapping("/aircrafts")
@Validated
public class AircraftController {

    private final AircraftService aircraftService;
    private final AirlineService airlineService;

    public AircraftController(AircraftService aircraftService, AirlineService airlineService) {
        this.aircraftService = aircraftService;
        this.airlineService = airlineService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add an aircraft", description = "Provide aircraft details to add a new aircraft")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aircraft added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid aircraft details")
    })
    public ResponseEntity<Aircraft> addAircraft(@RequestBody @Valid AircraftDto aircraftRequest) {
        var aircraft = aircraftService.addAircraft(aircraftRequest);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{registration}")
                .buildAndExpand(aircraft.getRegistration())
                .toUri();

        return ResponseEntity.created(uri).body(aircraft);
    }

    @GetMapping("/{registration}")
    @Operation(summary = "Get aircraft by registration", description = "Provide an aircraft registration to look up specific aircraft details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aircraft found"),
            @ApiResponse(responseCode = "404", description = "Aircraft not found")
    })
    public ResponseEntity<Aircraft> getAircraftByRegistration(@PathVariable String registration) {
        var aircraft = aircraftService.getAircraftByRegistration(registration);
        return ResponseEntity.ok(aircraft);
    }
}
