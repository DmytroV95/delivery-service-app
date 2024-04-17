package org.varukha.service.parser;

/**
 * Interface for a strategy to retrieve AttributeParser based on attribute names.
 */
public interface AttributeParserStrategy {
    /**
     * Retrieves the AttributeParser associated with the given attribute name.
     *
     * @param attribute the attribute name
     * @return the AttributeParser associated with the attribute name, or null if not found
     */
    AttributeParser getAttributeParser(String attribute);
}

