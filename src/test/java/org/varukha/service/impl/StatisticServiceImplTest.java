package org.varukha.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.varukha.entity.Cargo;
import org.varukha.service.parser.AttributeParser;

/**
 * Test class for the {@link StatisticServiceImpl} class, responsible for
 * testing the statistics calculation and manipulation methods.
 */
public class StatisticServiceImplTest {
    private static final Cargo TEST_CARGO_1 = new Cargo();
    private static final Cargo TEST_CARGO_2 = new Cargo();
    private static final Cargo TEST_CARGO_3 = new Cargo();
    private static final Cargo TEST_CARGO_4 = new Cargo();
    private static final Cargo TEST_CARGO_5 = new Cargo();
    private static final Cargo TEST_CARGO_6 = new Cargo();
    private static final Map<String, Integer> TEST_STATISTIC_DATA
            = new HashMap<>();
    private static final Map<String, Integer> UNSORTED_TEST_DATA
            = new HashMap<>();
    private static final String ATTRIBUTE_NAME = "cargo_category";
    @InjectMocks
    private StatisticServiceImpl statisticService;
    @Mock
    private AttributeParser attributeParser;

    public StatisticServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        TEST_STATISTIC_DATA.put("Clothing", 2);
        TEST_STATISTIC_DATA.put("Electronics", 1);
        TEST_STATISTIC_DATA.put("Food", 1);
        TEST_STATISTIC_DATA.put("Books", 2);

        TEST_CARGO_1.setId(1L);
        TEST_CARGO_1.setDescription("Clothing");
        TEST_CARGO_1.setWeight(10.5);
        TEST_CARGO_1.setDestination("New York");

        TEST_CARGO_2.setId(2L);
        TEST_CARGO_2.setDescription("Electronics");
        TEST_CARGO_2.setWeight(15.0);
        TEST_CARGO_2.setDestination("Chicago");

        TEST_CARGO_3.setId(3L);
        TEST_CARGO_3.setDescription("Food");
        TEST_CARGO_3.setWeight(8.75);
        TEST_CARGO_3.setDestination("Los Angeles");

        TEST_CARGO_4.setId(4L);
        TEST_CARGO_4.setDescription("Books");
        TEST_CARGO_4.setWeight(12.3);
        TEST_CARGO_4.setDestination("Seattle");

        TEST_CARGO_5.setId(5L);
        TEST_CARGO_5.setDescription("Clothing");
        TEST_CARGO_5.setWeight(9.0);
        TEST_CARGO_5.setDestination("Los Angeles");

        TEST_CARGO_6.setId(6L);
        TEST_CARGO_6.setDescription("Books");
        TEST_CARGO_6.setWeight(11.2);
        TEST_CARGO_6.setDestination("New York");
    }

    /**
     * Tests the {@link StatisticServiceImpl#updateStatistics(String, Map, List)} method to ensure
     * it returns updated statistics. It mocks the behavior of the AttributeParser to simulate
     * the parsing process and checks if the statistics are updated correctly.
     */
    @Test
    void updateStatistics_ReturnUpdatedStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("Clothing", 10);
        statistics.put("Electronics", 10);
        statistics.put("Food", 10);
        statistics.put("Books", 10);

        List<Cargo> cargos = Arrays.asList(
                TEST_CARGO_1, TEST_CARGO_2, TEST_CARGO_3,
                TEST_CARGO_4, TEST_CARGO_5, TEST_CARGO_6
        );

        when(attributeParser.parseByAttribute(cargos, ATTRIBUTE_NAME))
                .thenReturn(TEST_STATISTIC_DATA);
        statisticService.updateStatistics(ATTRIBUTE_NAME, statistics, cargos);

        assertEquals(4, statistics.size());
        assertEquals(12, statistics.get("Clothing"));
        assertEquals(11, statistics.get("Electronics"));
        assertEquals(11, statistics.get("Food"));
        assertEquals(12, statistics.get("Books"));
    }

    /**
     * Tests the {@link StatisticServiceImpl#sortAndCombineStatistics(Map)} method to ensure
     * it returns sorted statistics. It prepares unsorted test data, calls the method, and
     * checks if the returned statistics are sorted correctly.
     */
    @Test
    void sortAndCombineStatistics_ReturnSortedStatistics() {
        UNSORTED_TEST_DATA.put("Clothing", 10);
        UNSORTED_TEST_DATA.put("Electronics", 15);
        UNSORTED_TEST_DATA.put("Food", 30);
        UNSORTED_TEST_DATA.put("Books", 20);

        Map<String, Integer> sortedStatistics = statisticService
                .sortAndCombineStatistics(UNSORTED_TEST_DATA);

        List<Integer> sortedList = sortedStatistics
                .values()
                .stream()
                .toList();

        assertEquals(sortedList.get(0), sortedStatistics.get("Food"));
        assertEquals(sortedList.get(1), sortedStatistics.get("Books"));
        assertEquals(sortedList.get(2), sortedStatistics.get("Electronics"));
        assertEquals(sortedList.get(3), sortedStatistics.get("Clothing"));
    }
}
