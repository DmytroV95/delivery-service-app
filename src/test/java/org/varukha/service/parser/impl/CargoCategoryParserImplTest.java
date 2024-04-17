package org.varukha.service.parser.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.varukha.entity.Cargo;
import org.varukha.entity.enums.CargoCategory;
import org.varukha.entity.enums.StatisticAttributes;

/**
 * Test class for the {@link CargoCategoryParserImpl} class, responsible for
 * testing the parsing of cargo attributes based on cargo categories.
 */
public class CargoCategoryParserImplTest {
    private static final String ATTRIBUTE_NAME = StatisticAttributes
            .CARGO_CATEGORY.getAttributeName();
    private static final Map<String, Integer> TEST_STATISTIC_DATA = new HashMap<>();

    @Mock
    private static Cargo TEST_CARGO_1;
    @Mock
    private static Cargo TEST_CARGO_2;
    @Mock
    private static Cargo TEST_CARGO_3;
    @Mock
    private static Cargo TEST_CARGO_4;
    @Mock
    private static Cargo TEST_CARGO_5;
    @Mock
    private static Cargo TEST_CARGO_6;
    @InjectMocks
    private CargoCategoryParserImpl cargoCategoryParser;

    public CargoCategoryParserImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        TEST_STATISTIC_DATA.put("Clothing", 2);
        TEST_STATISTIC_DATA.put("Electronics", 1);
        TEST_STATISTIC_DATA.put("Food", 1);
        TEST_STATISTIC_DATA.put("Books", 2);
    }

    /**
     * Tests the {@code parseByAttribute} method of {@link CargoCategoryParserImpl}
     * when valid cargo categories are provided.
     * It mocks the Cargo objects and sets up behavior for their getCategories() method
     * to return specific CargoCategory values.
     * Then, it asserts that the parsed map matches the expected statistic data.
     */
    @Test
    void testParseByAttribute_ValidCargoCategories() {
        List<Cargo> cargos = List.of(
                TEST_CARGO_1, TEST_CARGO_2, TEST_CARGO_3,
                TEST_CARGO_4, TEST_CARGO_5, TEST_CARGO_6
        );
        when(TEST_CARGO_1.getCategories())
                .thenReturn(Collections.singletonList(CargoCategory.CLOTHING));
        when(TEST_CARGO_2.getCategories())
                .thenReturn(Collections.singletonList(CargoCategory.ELECTRONICS));
        when(TEST_CARGO_3.getCategories())
                .thenReturn(Collections.singletonList(CargoCategory.FOOD));
        when(TEST_CARGO_4.getCategories())
                .thenReturn(Collections.singletonList(CargoCategory.BOOKS));
        when(TEST_CARGO_5.getCategories())
                .thenReturn(Collections.singletonList(CargoCategory.CLOTHING));
        when(TEST_CARGO_6.getCategories())
                .thenReturn(Collections.singletonList(CargoCategory.BOOKS));

        Map<String, Integer> actualResultMap = cargoCategoryParser
                .parseByAttribute(cargos, ATTRIBUTE_NAME);

        assertEquals(TEST_STATISTIC_DATA.size(), actualResultMap.size());
        assertEquals(actualResultMap.get("Clothing"),
                TEST_STATISTIC_DATA.get("Clothing"));
        assertEquals(actualResultMap.get("Electronics"),
                TEST_STATISTIC_DATA.get("Electronics"));
        assertEquals(actualResultMap.get("Food"),
                TEST_STATISTIC_DATA.get("Food"));
        assertEquals(actualResultMap.get("Books"),
                TEST_STATISTIC_DATA.get("Books"));
    }
}
