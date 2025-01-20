package com.airport.airport_management.model;

import com.airport.airport_management.utils.FlightStatus;
import com.airport.airport_management.utils.FlightType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "flight")
public class Flight {
    @Id
    @Column(name = "flight_number", unique = true)
    private String flight_number;

    private String origin;

    private String destination;

    private LocalDateTime departure_time;

    private LocalDateTime arrival_time;

    @Enumerated(EnumType.STRING)
    private FlightType type;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "registration", nullable = false)
    private Aircraft aircraft;

    @ManyToOne
    @JoinColumn(name = "gate_id", referencedColumnName = "id")
    private Gate gate;
}
