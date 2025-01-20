package com.airport.airport_management.repository;

import com.airport.airport_management.model.CrewMember;
import com.airport.airport_management.model.FlightCrew;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightCrewRepository extends JpaRepository<FlightCrew, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM flight_crew WHERE flight_number = :flightNumber")
    Optional<List<FlightCrew>> findFlightCrewByFlightNumber(String flightNumber);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM flight_crew WHERE crew_id = :crewId AND flight_number = :flightNumber")
    void deleteCrewMemberFromFlight(int crewId, String flightNumber);
}
