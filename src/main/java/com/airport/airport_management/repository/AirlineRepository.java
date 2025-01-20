package com.airport.airport_management.repository;

import com.airport.airport_management.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

    Optional<Airline> findByIcao(String icao);
}
