package com.airport.airport_management.controller;

import com.airport.airport_management.dto.CrewMemberDto;
import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.service.CrewMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/crew-members")
@Validated
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a crew member", description = "Provide crew member details to add a new crew member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Crew member added"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    public ResponseEntity<CrewMember> addCrewMember(@RequestBody @Valid CrewMemberDto crewMemberRequest) {
        var crewMember = crewMemberService.addCrewMember(crewMemberRequest);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(crewMember.getId())
                .toUri();

        return ResponseEntity.created(uri).body(crewMember);
    }

    @GetMapping("/{registration_number}")
    @Operation(summary = "Get crew member by registration number", description = "Provide a crew member registration number to look up specific crew member details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crew member found"),
            @ApiResponse(responseCode = "404", description = "Crew member not found"),
    })
    public ResponseEntity<CrewMember> getCrewMemberByRegistrationNumber(@PathVariable String registration_number) {
        var crewMember = crewMemberService.findByRegistrationNumber(registration_number);
        return ResponseEntity.ok(crewMember);
    }

    @GetMapping("/all/{airline_name}")
    @Operation(summary = "Get all crew members for an airline", description = "Provide an airline name to look up all crew members details")
    public ResponseEntity<Iterable<CrewMember>> getAllCrewMembersByAirlineId(@PathVariable String airline_name) {
        var crewMembers = crewMemberService.getAllCrewMembersForAnAirline(airline_name);
        return ResponseEntity.ok(crewMembers);
    }
}
