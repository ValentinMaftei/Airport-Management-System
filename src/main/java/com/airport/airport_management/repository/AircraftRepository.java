package com.airport.airport_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.airport.airport_management.model.Aircraft;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM aircraft WHERE airline_id = :id")
    List<Aircraft> findAllByAirlineId(Integer airlineId);

    Optional<Aircraft> findByRegistration(String registration);
}
