package org.varukha.service.parser.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.varukha.entity.Cargo;
import org.varukha.entity.enums.DeliveryStatus;
import org.varukha.entity.enums.StatisticAttributes;

/**
 * Test class for the {@link DeliveryStatusParserImpl} class, responsible for
 * testing the parsing of cargo attributes based on delivery statuses.
 */
public class DeliveryStatusParserImplTest {
    private static final String ATTRIBUTE_NAME = StatisticAttributes
            .DELIVERY_STATUS.getAttributeName();
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
    private DeliveryStatusParserImpl deliveryStatusParser;

    public DeliveryStatusParserImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        TEST_STATISTIC_DATA.put("In Transit", 2);
        TEST_STATISTIC_DATA.put("Delivered", 1);
        TEST_STATISTIC_DATA.put("Out for Delivery", 1);
        TEST_STATISTIC_DATA.put("Pending", 2);
    }

    /**
     * Tests the {@code parseByAttribute} method of {@link DeliveryStatusParserImpl}
     * when valid delivery statuses are provided.
     * It mocks the Cargo objects and sets up behavior for their getStatus() method
     * to return specific DeliveryStatus values.
     * Then, it asserts that the parsed map matches the expected statistic data.
     */
    @Test
    void testParseByAttribute_ValidDeliveryStatus() {
        List<Cargo> cargos = List.of(
                TEST_CARGO_1, TEST_CARGO_2, TEST_CARGO_3,
                TEST_CARGO_4, TEST_CARGO_5, TEST_CARGO_6
        );
        when(TEST_CARGO_1.getStatus()).thenReturn(DeliveryStatus.IN_TRANSIT);
        when(TEST_CARGO_2.getStatus()).thenReturn(DeliveryStatus.DELIVERED);
        when(TEST_CARGO_3.getStatus()).thenReturn(DeliveryStatus.OUT_FOR_DELIVERY);
        when(TEST_CARGO_4.getStatus()).thenReturn(DeliveryStatus.PENDING);
        when(TEST_CARGO_5.getStatus()).thenReturn(DeliveryStatus.PENDING);
        when(TEST_CARGO_6.getStatus()).thenReturn(DeliveryStatus.IN_TRANSIT);

        Map<String, Integer> actualResultMap = deliveryStatusParser
                .parseByAttribute(cargos, ATTRIBUTE_NAME);

        assertEquals(TEST_STATISTIC_DATA.size(), actualResultMap.size());
        assertEquals(actualResultMap.get("In Transit"),
                TEST_STATISTIC_DATA.get("In Transit"));
        assertEquals(actualResultMap.get("Delivered"),
                TEST_STATISTIC_DATA.get("Delivered"));
        assertEquals(actualResultMap.get("Out for Delivery"),
                TEST_STATISTIC_DATA.get("Out for Delivery"));
        assertEquals(actualResultMap.get("Pending"),
                TEST_STATISTIC_DATA.get("Pending"));
    }
}
