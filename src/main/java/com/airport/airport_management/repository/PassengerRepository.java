package com.airport.airport_management.repository;

import com.airport.airport_management.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM passenger WHERE identity_number = :identityNumber")
    Optional<Passenger> findByIdentityNumber(String identityNumber);
}
