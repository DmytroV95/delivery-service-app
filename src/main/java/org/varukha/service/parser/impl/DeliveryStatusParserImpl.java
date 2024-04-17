package org.varukha.service.parser.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.varukha.entity.Cargo;
import org.varukha.service.parser.AttributeParser;

/**
 * Implementation of AttributeParser for parsing delivery statuses.
 */
public class DeliveryStatusParserImpl implements AttributeParser {

    /**
     * Parses the list of cargoes by delivery statuses and returns a map of
     * status names to counts.
     *
     * @param cargos         the list of cargoes to parse
     * @param deliveryStatus the delivery status attribute (not used)
     * @return a map containing delivery status names as keys and the count of cargoes
     * with each status as values
     */
    @Override
    public Map<String, Integer> parseByAttribute(List<Cargo> cargos, String deliveryStatus) {
        return cargos.stream()
                .collect(Collectors.groupingBy(
                        cargo -> cargo.getStatus()
                                .getStatusName(),
                        Collectors.summingInt(i -> 1)));
    }
}
