package org.varukha.service.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Test class for the {@link XmlStatisticsWriter} class, responsible for
 * testing the functionality of writing statistics to an XML file.
 */
public class XmlStatisticsWriterTest {
    private static final String ATTRIBUTE_NAME = "cargo_category";
    private static final Map<String, Integer> TEST_STATISTIC_DATA
            = new HashMap<>();
    private static final String DIRECTORY_PATH = "src/test/resources/test_report";
    private static final String REPORT_PATH = "src/test/resources/test_report/test_report.xml";

    @InjectMocks
    private XmlStatisticsWriter xmlStatisticsWriter;

    public XmlStatisticsWriterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        TEST_STATISTIC_DATA.put("Clothing", 20);
        TEST_STATISTIC_DATA.put("Electronics", 11);
        TEST_STATISTIC_DATA.put("Food", 13);
        TEST_STATISTIC_DATA.put("Books", 2);
    }

    /**
     * Tests the {@link XmlStatisticsWriter#writeStatisticsToFile(Map, String, String)}
     * method to ensure successful writing of statistics to an XML file.
     */
    @Test
    public void testWriteStatisticsToFile_Success() {
        xmlStatisticsWriter.writeStatisticsToFile(
                TEST_STATISTIC_DATA,
                REPORT_PATH,
                ATTRIBUTE_NAME);

        File directory = new File(DIRECTORY_PATH);
        assertTrue(directory.exists() && directory.isDirectory(),
                "Directory must exist and be a directory");

        File[] filesInDirectory = directory.listFiles();
        assertTrue(filesInDirectory != null && filesInDirectory.length == 1,
                "Directory must contain exactly one file");

        File reportFile = new File(REPORT_PATH);
        assertTrue(reportFile.exists() && reportFile.isFile(),
                "Report file must exist in the directory");

        assertEquals("test_report.xml", reportFile.getName(),
                "Report file must have the appropriate name");

        assertTrue(reportFile.getName().endsWith(".xml"),
                "Report file must have XML extension");
    }

    /**
     * Tests the {@link XmlStatisticsWriter#writeStatisticsToFile(Map, String, String)}
     * method to ensure that it throws a RuntimeException when given null arguments.
     */
    @Test
    public void testWriteStatisticsToFile_ExceptionThrown() {
        assertThrows(RuntimeException.class,
                () -> xmlStatisticsWriter.writeStatisticsToFile(
                        null, REPORT_PATH, ATTRIBUTE_NAME));
        assertThrows(RuntimeException.class,
                () -> xmlStatisticsWriter.writeStatisticsToFile(
                        TEST_STATISTIC_DATA, null, ATTRIBUTE_NAME));
    }

    /**
     * Tests the {@link XmlStatisticsWriter#writeStatisticsToFile(Map, String, String)}
     * method to ensure that the thrown exception message is correct when given null arguments.
     */
    @Test
    public void testWriteStatisticsToFile_ExceptionMessage() {
        assertThrows(RuntimeException.class,
                () -> xmlStatisticsWriter.writeStatisticsToFile(
                        null, REPORT_PATH, ATTRIBUTE_NAME),
                "Error occurred while generating XML statistic report");

        assertThrows(RuntimeException.class,
                () -> xmlStatisticsWriter.writeStatisticsToFile(
                        TEST_STATISTIC_DATA, null, ATTRIBUTE_NAME),
                "Error occurred while generating XML statistic report");
    }
}
