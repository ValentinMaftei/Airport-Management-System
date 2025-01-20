package com.airport.airport_management.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class FlightCrewPK implements Serializable {
    private int crew_id;
    private String flight_number;

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (! (object instanceof FlightCrewPK flightCrewPK))
            return false;
        return crew_id == flightCrewPK.crew_id && Objects.equals(flight_number, flightCrewPK.flight_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew_id, flight_number);
    }
}
