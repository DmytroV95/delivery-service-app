package org.varukha.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.varukha.entity.enums.VehicleType;

/**
 * A class representing a vehicle entity.
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class Vehicle {
    private Long id;
    private VehicleType type;
    private String vehicleNumber;
    private Double cargoCapacity;
    private String route;
}
