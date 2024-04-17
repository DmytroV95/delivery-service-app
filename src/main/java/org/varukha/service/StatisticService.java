package org.varukha.service;

import java.util.List;
import java.util.Map;
import org.varukha.entity.Cargo;

/**
 * This interface defines operations for updating and sorting statistics based on attribute values.
 */
public interface StatisticService {
    /**
     * Updates the statistics map based on the attribute values extracted from the list of cargos.
     *
     * @param attributeName the name of the attribute to update statistics for
     * @param statistics    the map containing statistics to update
     * @param cargos        the list of Cargo objects to extract attribute values from
     */
    void updateStatistics(String attributeName,
                          Map<String, Integer> statistics,
                          List<Cargo> cargos);

    /**
     * Sorts the statistics map by value in descending order and returns the sorted map.
     *
     * @param statistics the unsorted statistics map
     * @return the sorted statistics map by value in descending order
     */
    Map<String, Integer> sortAndCombineStatistics(Map<String, Integer> statistics);
}
