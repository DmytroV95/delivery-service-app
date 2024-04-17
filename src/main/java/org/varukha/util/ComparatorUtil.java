package org.varukha.util;

import java.util.Comparator;
import java.util.Map;

public class ComparatorUtil {
    /**
     * Comparator for sorting Map entries by value in descending order.
     *
     * @return a comparator for sorting Map entries by value in descending order
     */
    public static Comparator<Map.Entry<String, Integer>> valueComparatorDesc() {
        return (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue());
    }
}
