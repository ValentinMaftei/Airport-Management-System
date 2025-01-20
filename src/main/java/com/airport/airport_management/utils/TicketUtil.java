package com.airport.airport_management.utils;

import com.airport.airport_management.dto.TicketDto;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.Passenger;
import com.airport.airport_management.model.Ticket;
import com.airport.airport_management.repository.FlightRepository;
import com.airport.airport_management.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketUtil {

    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public TicketUtil(FlightRepository flightRepository, PassengerRepository passengerRepository) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    public Ticket fromDtoToTicket(TicketDto ticketDto) {
        Flight flight = flightRepository.findById(ticketDto.getFlight_number()).orElse(null);
        Passenger passenger = passengerRepository.findById(ticketDto.getPassenger_id()).orElse(null);
        return Ticket.builder()
                .seat_number(ticketDto.getSeat_number())
                .price(ticketDto.getPrice())
                .booking_datetime(ticketDto.getBooking_datetime())
                .flight(flight)
                .passenger(passenger)
                .build();
    }
}
