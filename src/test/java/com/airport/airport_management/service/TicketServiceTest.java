package com.airport.airport_management.service;

import com.airport.airport_management.dto.TicketDto;
import com.airport.airport_management.model.Flight;
import com.airport.airport_management.model.Passenger;
import com.airport.airport_management.model.Ticket;
import com.airport.airport_management.repository.FlightRepository;
import com.airport.airport_management.repository.TicketRepository;
import com.airport.airport_management.utils.TicketUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private TicketUtil ticketUtil;

    @Test
    void add_ticket() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setPassenger_id(1);
        ticketDto.setFlight_number("WZZ123");
        ticketDto.setPrice(100);
        ticketDto.setBooking_datetime(LocalDateTime.of(2025, 1, 12, 12, 0, 0));
        ticketDto.setSeat_number("A1");

        Passenger passenger = new Passenger();
        passenger.setId(1);

        Ticket ticket = new Ticket();
        ticket.setPassenger(passenger);
        ticket.setPrice(100);
        ticket.setBooking_datetime(LocalDateTime.of(2025, 1, 12, 12, 0, 0));
        ticket.setSeat_number("A1");

        Flight flight = new Flight();
        flight.setFlight_number("WZZ123");

        when(ticketUtil.fromDtoToTicket(ticketDto)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(flightRepository.findByFlightNumber("WZZ123")).thenReturn(Optional.of(flight));

        Ticket result = ticketService.addTicket(ticketDto);

        assertNotNull(result);
        assertEquals(1, result.getPassenger().getId());
        assertEquals(100, result.getPrice());
        assertEquals(LocalDateTime.of(2025, 1, 12, 12, 0, 0), result.getBooking_datetime());
        assertEquals("A1", result.getSeat_number());

        verify(ticketRepository).save(ticketUtil.fromDtoToTicket(ticketDto));
        verify(flightRepository).findByFlightNumber("WZZ123");
    }

    @Test
    void findTicketByPassenger(){
        Ticket ticket = new Ticket();
        ticket.setPassenger(new Passenger());
        ticket.getPassenger().setIdentity_number("123456789");
        ticket.setPrice(100);
        ticket.setBooking_datetime(LocalDateTime.of(2025, 1, 12, 12, 0, 0));
        ticket.setSeat_number("A1");

        when(ticketRepository.findByPassengerIdentityNumber("123456789")).thenReturn(java.util.Optional.of(ticket));

        Ticket result = ticketService.findTicketByPassenger("123456789");

        assertNotNull(result);
        assertEquals(100, result.getPrice());
        assertEquals(LocalDateTime.of(2025, 1, 12, 12, 0, 0), result.getBooking_datetime());
        assertEquals("A1", result.getSeat_number());

        verify(ticketRepository).findByPassengerIdentityNumber("123456789");
    }

    @Test
    void findTicketByFlightNumber(){
        Ticket ticket = new Ticket();
        ticket.setPassenger(new Passenger());
        ticket.getPassenger().setIdentity_number("123456789");
        ticket.setPrice(100);
        ticket.setBooking_datetime(LocalDateTime.of(2025, 1, 12, 12, 0, 0));
        ticket.setSeat_number("A1");

        Ticket ticket2 = new Ticket();
        ticket2.setPassenger(new Passenger());
        ticket2.getPassenger().setIdentity_number("123456789");
        ticket2.setPrice(100);
        ticket2.setBooking_datetime(LocalDateTime.of(2025, 1, 12, 12, 0, 0));
        ticket2.setSeat_number("A1");

        when(ticketRepository.findByFlightNumber("WZZ123")).thenReturn(java.util.Optional.of(List.of(ticket)));

        List<Ticket> result = ticketService.findTicketsByFlightNumber("WZZ123");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100, result.getFirst().getPrice());
        assertEquals(LocalDateTime.of(2025, 1, 12, 12, 0, 0), result.getFirst().getBooking_datetime());
        assertEquals("A1", result.getFirst().getSeat_number());

        verify(ticketRepository).findByFlightNumber("WZZ123");
    }
}
