package com.airport.airport_management.repository;

import com.airport.airport_management.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM flight WHERE flight_number = :flightNumber")
    Optional<Flight> findByFlightNumber(String flightNumber);

    Optional<Flight> findByGateName(String name);
}
