package org.varukha.entity.enums;

/**
 * Enumeration representing categories of cargo.
 */
public enum CargoCategory {
    FOOD("Food"),
    ELECTRONICS("Electronics"),
    FURNITURE("Furniture"),
    CLOTHING("Clothing"),
    BOOKS("Books"),
    OTHER("Other");

    private final String categoryName;

    CargoCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
