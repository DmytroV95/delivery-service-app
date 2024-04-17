package org.varukha.entity.enums;

/**
 * Enumeration representing delivery status of cargo.
 */
public enum DeliveryStatus {
    IN_TRANSIT("In Transit"),
    DELIVERED("Delivered"),
    OUT_FOR_DELIVERY("Out for Delivery"),
    PENDING("Pending"),
    RETURNED("Returned"),
    LOST("Lost");

    private final String statusName;

    DeliveryStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
