package com.airport.airport_management.service;

import com.airport.airport_management.dto.TicketDto;
import com.airport.airport_management.exception.FlightFullException;
import com.airport.airport_management.exception.FlightNotFoundException;
import com.airport.airport_management.exception.SeatAlreadyTakenException;
import com.airport.airport_management.exception.TicketPassengerNotFoundException;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.repository.FlightRepository;
import com.airport.airport_management.repository.TicketRepository;
import com.airport.airport_management.model.Ticket;
import com.airport.airport_management.utils.TicketUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketUtil ticketUtil;
    private final FlightRepository flightRepository;

    public TicketService(TicketRepository ticketRepository, TicketUtil ticketUtil, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketUtil = ticketUtil;
        this.flightRepository = flightRepository;
    }

    public Ticket addTicket(TicketDto ticketDto) {
        Optional<Flight> flightOptional = flightRepository.findByFlightNumber(ticketDto.getFlight_number());
        if (flightOptional.isEmpty()) {
            throw new FlightNotFoundException(ticketDto.getFlight_number());
        }

        Optional<List<Ticket>> ticketOptional = ticketRepository.findByFlightNumber(ticketDto.getFlight_number());
        if (ticketOptional.isPresent()) {
            List<Ticket> tickets = ticketOptional.get();
            if (tickets.size() >= flightOptional.get().getAircraft().getCapacity()) {
                throw new FlightFullException(ticketDto.getFlight_number());
            }
            else {
                for (Ticket ticket : tickets) {
                    if (ticket.getSeat_number().equals(ticketDto.getSeat_number())) {
                        throw new SeatAlreadyTakenException(ticketDto.getSeat_number());
                    }
                }
            }
        }

        return ticketRepository.save(ticketUtil.fromDtoToTicket(ticketDto));
    }

    public Ticket findTicketByPassenger(String identity_number) {
        Optional<Ticket> ticketOptional = ticketRepository.findByPassengerIdentityNumber(identity_number);
        if (ticketOptional.isPresent()) {
            return ticketOptional.get();
        } else {
            throw new TicketPassengerNotFoundException(identity_number);
        }
    }

    public List<Ticket> findTicketsByFlightNumber(String flight_number) {
        Optional<List<Ticket>> ticketOptional = ticketRepository.findByFlightNumber(flight_number);
        if (ticketOptional.isPresent()) {
            return ticketOptional.get();
        } else {
            throw new FlightNotFoundException(flight_number);
        }
    }
}
