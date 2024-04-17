package org.varukha.service.parser.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.varukha.entity.Cargo;
import org.varukha.entity.enums.CargoCategory;
import org.varukha.service.parser.AttributeParser;

/**
 * Implementation of AttributeParser for parsing cargo categories.
 */
public class CargoCategoryParserImpl implements AttributeParser {

    /**
     * Parses the list of cargoes by cargo categories and returns a map
     * of category names to counts.
     *
     * @param cargos    the list of cargoes to parse
     * @param attribute the attribute to parse by (not used)
     * @return a map containing category names as keys and the count of cargoes
     * in each category as values
     */
    @Override
    public Map<String, Integer> parseByAttribute(List<Cargo> cargos, String attribute) {
        return cargos.stream()
                .flatMap(cargo -> cargo.getCategories().stream())
                .collect(Collectors.groupingBy(
                        CargoCategory::getCategoryName,
                        Collectors.summingInt(i -> 1)));
    }
}
