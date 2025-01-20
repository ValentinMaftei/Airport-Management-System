package com.airport.airport_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @Column(unique = true)
    private String registration;

    private String model;

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "airline_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Airline airline;

    @PrePersist
    @PreUpdate
    private void prepareData() {
        this.registration = this.registration.toUpperCase();
    }
}
