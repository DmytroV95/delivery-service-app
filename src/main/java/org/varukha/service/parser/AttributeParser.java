package org.varukha.service.parser;

import java.util.List;
import java.util.Map;
import org.varukha.entity.Cargo;

/**
 * Interface for parsing attributes of cargoes.
 */
public interface AttributeParser {
    /**
     * Parses the list of cargoes by the specified attribute and returns a map
     * of attribute values to counts.
     *
     * @param cargos   the list of cargoes to parse
     * @param category the attribute to parse by
     * @return a map containing attribute values as keys and the count of cargoes
     * with each attribute value as values
     */
    Map<String, Integer> parseByAttribute(List<Cargo> cargos, String category);
}
