package com.airport.airport_management.utils;

import com.airport.airport_management.dto.GateDto;
import com.airport.airport_management.model.Gate;

public class GateUtil {

    public static Gate fromDtoToGate(GateDto gateDto){
        return Gate.builder()
                .name(gateDto.getName())
                .occupancy_status(gateDto.isOccupancy_status())
                .build();
    }
}
