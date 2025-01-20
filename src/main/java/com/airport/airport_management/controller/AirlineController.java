package com.airport.airport_management.controller;

import com.airport.airport_management.dto.AirlineDto;
import com.airport.airport_management.model.Airline;
import com.airport.airport_management.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/airlines")
@Validated
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add an airline", description = "Provide airline details to add a new airline")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airline added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid airline details")
    })
    public ResponseEntity<Airline> addAirline(@RequestBody @Valid AirlineDto airlineRequest) {
        var airline = airlineService.addAirline(airlineRequest);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(airline.getId())
                .toUri();

        return ResponseEntity.created(uri).body(airline);
    }

    @GetMapping("/{icao}")
    @Operation(summary = "Get airline by ICAO", description = "Provide an airline ICAO to look up specific airline details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline found"),
            @ApiResponse(responseCode = "404", description = "Airline not found")
    })
    public ResponseEntity<Airline> getAirlineByIcao(@PathVariable String icao) {
        var airline = airlineService.getAirlineByIcao(icao);
        return ResponseEntity.ok(airline);
    }
}
