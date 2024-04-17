package org.varukha.service.io;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.varukha.util.PathBuilder;

/**
 * Class for writing statistics data to an XML file.
 * This class provides a method to write statistics data
 * in XML format to a specified file.
 */
public class XmlStatisticsWriter {
    private static final Logger LOGGER =
            LogManager.getLogger(XmlStatisticsWriter.class);

    /**
     * Writes the statistics data to an XML file.
     *
     * @param statisticsData The statistics data to be written to the file.
     * @param reportPath     The path to the XML file where the statistics data will be written.
     */
    public void writeStatisticsToFile(Map<String, Integer> statisticsData,
                                      String reportPath,
                                      String attribute) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

            Statistics statistics = new Statistics();
            statistics.setStatistics(statisticsData);

            createDirectoryIfNotExists();

            File xmlFile = new File(reportPath);
            xmlMapper.writeValue(xmlFile, statistics);

            LOGGER.info(String.format("XML statistic report by %s generated successfully."
                    + " Please follow the link %s: ", attribute, xmlFile.getAbsolutePath()));
        } catch (IOException e) {
            LOGGER.error("Error occurred while generating XML statistic report: " + e.getMessage());
            throw new RuntimeException("Error occurred while generating XML statistic report");
        }
    }

    /**
     * Represents the statistics data in XML format.
     */
    @JsonRootName("statistics")
    static class Statistics {
        @JacksonXmlProperty(localName = "item")
        @JacksonXmlElementWrapper(useWrapping = false)
        private final List<Item> statisticsData = new ArrayList<>();

        /**
         * Sets the statistics data.
         *
         * @param statisticsData The statistics data to be set.
         */
        public void setStatistics(Map<String, Integer> statisticsData) {
            for (Map.Entry<String, Integer> entry : statisticsData.entrySet()) {
                this.statisticsData.add(
                        new Item(entry.getKey(),
                                entry.getValue()));
            }
        }
    }

    /**
     * Represents an item in the statistics data.
     */
    @JsonPropertyOrder({"value", "count"})
    static class Item {
        @JacksonXmlProperty(localName = "value")
        private final String value;

        @JacksonXmlProperty(localName = "count")
        private final int count;

        public Item(String value, int count) {
            this.value = value;
            this.count = count;
        }
    }

    /**
     * Creates the directory for the report file if it does not exist.
     */
    private void createDirectoryIfNotExists() {
        String path = PathBuilder.buildRelativeReportPath();
        Path directoryPath = Paths.get(path);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                LOGGER.error("Failed to create directory: " + directoryPath);
                throw new RuntimeException("Failed to create directory: " + e.getMessage());
            }
        }
    }
}
