package org.varukha.entity.enums;

/**
 * Enumeration representing types of vehicles.
 */
public enum VehicleType {
    CAR("Car"),
    TRUCK("Truck"),
    TRAIN("Train"),
    PLANE("Plane"),
    SHIP("Ship"),
    HELICOPTER("Helicopter"),
    DRONE("Drone");

    private final String type;

    VehicleType(String vehicleType) {
        this.type = vehicleType;
    }

    public String getType() {
        return type;
    }
}
