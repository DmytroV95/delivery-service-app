package org.varukha.service.parser.impl;

import java.util.Map;
import org.varukha.service.parser.AttributeParser;
import org.varukha.service.parser.AttributeParserStrategy;

/**
 * Implementation of AttributeParserStrategy that uses a map to look up AttributeParsers
 * based on attribute names.
 */
public class AttributeParserStrategyImpl implements AttributeParserStrategy {
    private final Map<String, AttributeParser> attributeStrategeMap;

    public AttributeParserStrategyImpl(Map<String, AttributeParser> attributeStrategeMap) {
        this.attributeStrategeMap = attributeStrategeMap;
    }

    /**
     * Retrieves the AttributeParser associated with the given attribute name.
     *
     * @param attribute the attribute name
     * @return the AttributeParser associated with the attribute name, or null if not found
     */
    @Override
    public AttributeParser getAttributeParser(String attribute) {
        return attributeStrategeMap.get(attribute);
    }
}
