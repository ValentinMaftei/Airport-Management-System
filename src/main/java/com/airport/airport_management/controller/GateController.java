package com.airport.airport_management.controller;

import com.airport.airport_management.dto.GateDto;
import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Gate;
import com.airport.airport_management.service.GateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/gates")
@Validated
public class GateController {
    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a gate", description = "Provide gate details to add a new gate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gate added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Gate> addGate(@RequestBody @Valid GateDto gateRequest) {
        var gate = gateService.addGate(gateRequest);

        var uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(gate.getId())
            .toUri();

        return ResponseEntity.created(uri).body(gate);
    }

    @GetMapping("/{gate_name}")
    @Operation(summary = "Get gate by gate number", description = "Provide a gate number to look up specific gate details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gate found"),
            @ApiResponse(responseCode = "404", description = "Gate not found")
    })
    public ResponseEntity<Gate> getGateByGateName(@PathVariable String gate_name) {
        var gate = gateService.getGateByName(gate_name);
        return ResponseEntity.ok(gate);
    }

    @DeleteMapping("/{gate_name}")
    @Operation(summary = "Delete gate by gate name", description = "Provide a gate name to delete specific gate details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gate deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Gate not found")
    })
    public ResponseEntity<String> deleteGateByGateName(@PathVariable String gate_name) {
        gateService.deleteGateByName(gate_name);
        return ResponseEntity.ok("Gate deleted successfully");
    }

    @PutMapping("/change_is_cleared/{gate_name}")
    @Operation(summary = "Change gate is cleared", description = "Provide a gate name to change the gate is cleared status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gate is cleared status changed successfully"),
            @ApiResponse(responseCode = "404", description = "Gate not found")
    })
    public ResponseEntity<Gate> changeGateIsCleared(@PathVariable String gate_name) {
        var gate = gateService.changeGateIsCleared(gate_name);
        return ResponseEntity.ok(gate);
    }

    @GetMapping("/all_aircrafts")
    @Operation(summary = "Get all aircrafts", description = "Get all aircrafts parked at a gate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aircrafts found"),
            @ApiResponse(responseCode = "404", description = "Aircrafts not found")
    })
    public ResponseEntity<List<Aircraft>> getAllAircraft() {
        var aircrafts = gateService.getAllAircraftAtGates();
        return ResponseEntity.ok(aircrafts);
    }
}
