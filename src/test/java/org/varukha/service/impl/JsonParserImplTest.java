package org.varukha.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.varukha.entity.Cargo;
import org.varukha.service.StatisticService;
import org.varukha.service.io.JsonReader;

/**
 * Test class for the {@link JsonParserImpl} class, responsible for
 * testing the parsing of JSON files by attributes.
 */
public class JsonParserImplTest {
    private static final String ATTRIBUTE_NAME = "cargo_category";
    private static final Map<String, Integer> TEST_STATISTIC_DATA = new HashMap<>();
    private static final Cargo TEST_CARGO_1 = new Cargo();
    private static final Cargo TEST_CARGO_2 = new Cargo();
    private static final Cargo TEST_CARGO_3 = new Cargo();
    private static final Cargo TEST_CARGO_4 = new Cargo();
    private static final Cargo TEST_CARGO_5 = new Cargo();
    private static final Cargo TEST_CARGO_6 = new Cargo();

    private static File[] nonEmptyFilesArray;
    @Mock
    private static File TEST_DIRECTORY;
    @Mock
    private StatisticService statisticService;
    @Mock
    private JsonReader jsonReader;
    @InjectMocks
    private JsonParserImpl jsonParser;

    public JsonParserImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setUpAll() {
        nonEmptyFilesArray = new File[]{
                new File("file1.json"),
                new File("file2.json")
        };

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
     * Tests the {@link JsonParserImpl#parseJsonByAttributes(File, String)} method to ensure it
     * returns correct statistics. It mocks the behavior of the JsonReader and StatisticService
     * to simulate the parsing process.
     */
    @Test
    void parseJsonByAttributes_ReturnsCorrectStatistics() {
        List<Cargo> cargoList1 = new ArrayList<>();
        cargoList1.add(TEST_CARGO_1);
        cargoList1.add(TEST_CARGO_2);
        cargoList1.add(TEST_CARGO_3);

        List<Cargo> cargoList2 = new ArrayList<>();
        cargoList2.add(TEST_CARGO_4);
        cargoList2.add(TEST_CARGO_5);
        cargoList2.add(TEST_CARGO_6);

        when(jsonReader.loadJsonFile(any(File.class)))
                .thenReturn(cargoList1)
                .thenReturn(cargoList2);

        when(statisticService.sortAndCombineStatistics(anyMap()))
                .thenReturn(TEST_STATISTIC_DATA);

        when(TEST_DIRECTORY.listFiles()).thenReturn(nonEmptyFilesArray);

        Map<String, Integer> actualStatistics = jsonParser
                .parseJsonByAttributes(TEST_DIRECTORY, ATTRIBUTE_NAME);

        assertEquals(TEST_STATISTIC_DATA, actualStatistics);
        verify(jsonReader, times(2)).loadJsonFile(any(File.class));
        verify(statisticService).updateStatistics(eq(ATTRIBUTE_NAME), anyMap(), eq(cargoList1));
        verify(statisticService).updateStatistics(eq(ATTRIBUTE_NAME), anyMap(), eq(cargoList2));
        verify(statisticService).sortAndCombineStatistics(anyMap());
    }
}
