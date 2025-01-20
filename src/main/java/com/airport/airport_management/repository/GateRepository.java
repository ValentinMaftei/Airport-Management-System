package com.airport.airport_management.repository;

import com.airport.airport_management.model.Aircraft;
import com.airport.airport_management.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GateRepository extends JpaRepository<Gate, Integer> {

    Optional<Gate> findByName(String name);

    @Query(nativeQuery = true, value = "SELECT a FROM Gate g JOIN Flight f ON f.gate.id = g.id JOIN Aircraft a ON f.aircraft.registration = a.registration")
    List<Aircraft> findAllAircraftAtGates();
}
