package com.airport.airport_management.repository;

import com.airport.airport_management.model.Passenger;
import com.airport.airport_management.model.Ticket;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(nativeQuery = true, value = "SELECT A.* FROM ticket A JOIN passenger B ON B.id = A.passenger_id WHERE B.identity_number = :identityNumber")
    Optional<Ticket> findByPassengerIdentityNumber(String identityNumber);

    @Query(nativeQuery = true, value = "SELECT * FROM ticket WHERE flight_number = :flightNumber")
    Optional<List<Ticket>> findByFlightNumber(@NotNull(message = "Flight id cannot be null") String flightNumber);
}
