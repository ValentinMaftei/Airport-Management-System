package com.airport.airport_management.controller;

import com.airport.airport_management.dto.TicketDto;
import com.airport.airport_management.model.Ticket;
import com.airport.airport_management.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/tickets")
@Validated

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add a ticket", description = "Provide ticket details to add a new ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket added"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
    })
    public ResponseEntity<Ticket> addTicket(@RequestBody @Valid TicketDto ticketRequest) {
        var ticket = ticketService.addTicket(ticketRequest);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticket.getId())
                .toUri();

        return ResponseEntity.created(uri).body(ticket);
    }

    @PostMapping("/{passenger_identity_number}")
    @Operation(summary = "Get a ticket", description = "Provide passenger identity_number to get ticket details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket found"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
    })
    public ResponseEntity<Ticket> getTicketById(@PathVariable String passenger_identity_number) {
        var ticket = ticketService.findTicketByPassenger(passenger_identity_number);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/all/{flight_number}")
    @Operation(summary = "Get all tickets for a flight", description = "Provide a flight number to get all tickets for a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets found"),
            @ApiResponse(responseCode = "404", description = "Tickets not found"),
    })
    public ResponseEntity<Iterable<Ticket>> getAllTicketsForFlight(@PathVariable String flight_number) {
        var tickets = ticketService.findTicketsByFlightNumber(flight_number);
        return ResponseEntity.ok(tickets);
    }
}
