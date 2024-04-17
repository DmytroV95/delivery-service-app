package org.varukha.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.varukha.entity.Cargo;
import org.varukha.service.StatisticService;
import org.varukha.service.parser.AttributeParser;
import org.varukha.util.ComparatorUtil;

/**
 * Implementation of the {@link StatisticService} interface that provides methods
 * for updating and sorting statistics based on attribute values extracted from Cargo objects.
 */
public class StatisticServiceImpl implements StatisticService {
    private final AttributeParser attributeParser;

    public StatisticServiceImpl(AttributeParser attributeParser) {
        this.attributeParser = attributeParser;
    }

    /**
     * Updates the statistics map based on the attribute values extracted from the list of cargos.
     *
     * @param attributeName the name of the attribute to update statistics for
     * @param statistics    the map containing statistics to update
     * @param cargos        the list of Cargo objects to extract attribute values from
     */
    public void updateStatistics(String attributeName,
                                 Map<String, Integer> statistics,
                                 List<Cargo> cargos) {
        Map<String, Integer> partialStatistics = attributeParser
                .parseByAttribute(cargos, attributeName);
        partialStatistics.forEach(
                (key, value) -> statistics.merge(key, value, Integer::sum));
    }

    /**
     * Sorts the statistics map by value in descending order and returns the sorted map.
     *
     * @param statistics the unsorted statistics map
     * @return the sorted statistics map by value in descending order
     */
    public Map<String, Integer> sortAndCombineStatistics(Map<String, Integer> statistics) {
        return statistics.entrySet().stream()
                .sorted(ComparatorUtil.valueComparatorDesc())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
