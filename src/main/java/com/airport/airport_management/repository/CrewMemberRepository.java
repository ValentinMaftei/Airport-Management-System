package com.airport.airport_management.repository;


import com.airport.airport_management.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM crew_member WHERE registration_number = :registrationNumber")
    Optional<CrewMember> findByRegistrationNumber(String registrationNumber);

    @Query(nativeQuery = true, value = "SELECT c.* FROM crew_member c JOIN airline a ON c.airline_id = a.id WHERE a.name = :airlineName")
    List<CrewMember> findAllCrewMembersForAnAirline(String airlineName);
}
