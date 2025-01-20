package com.airport.airport_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "flight_crew")
public class FlightCrew {
    @EmbeddedId
    private FlightCrewPK flight_crew_id;

    @ManyToOne
    @JoinColumn(name = "crew_id", insertable = false, updatable = false, referencedColumnName = "id")
    private CrewMember crewMember;

    @ManyToOne
    @JoinColumn(name = "flight_number", insertable = false, updatable = false, referencedColumnName = "flight_number")
    private Flight flight;
}
