package org.varukha.entity;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.varukha.entity.enums.CargoCategory;
import org.varukha.entity.enums.DeliveryStatus;

/**
 * A class representing a cargo entity.
 */
@Data
@RequiredArgsConstructor
public class Cargo {
    private Long id;
    private String description;
    private Double weight;
    private String dimensions;
    private String destination;
    private DeliveryStatus status;
    private List<CargoCategory> categories;
    private Vehicle vehicleInfo;
}
