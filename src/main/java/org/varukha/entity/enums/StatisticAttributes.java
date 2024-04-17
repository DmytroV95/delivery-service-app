package org.varukha.entity.enums;

/**
 * Enumeration representing statistic attributes of cargo to create XML report.
 */
public enum StatisticAttributes {
    DELIVERY_STATUS("delivery_status"),
    CARGO_CATEGORY("cargo_category");

    private final String attributeName;

    StatisticAttributes(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
