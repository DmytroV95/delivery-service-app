package org.varukha;

import java.io.File;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.varukha.entity.enums.StatisticAttributes;
import org.varukha.service.JsonParserService;
import org.varukha.service.StatisticService;
import org.varukha.service.impl.JsonParserImpl;
import org.varukha.service.impl.StatisticServiceImpl;
import org.varukha.service.io.JsonReader;
import org.varukha.service.io.XmlStatisticsWriter;
import org.varukha.service.parser.AttributeParser;
import org.varukha.service.parser.AttributeParserStrategy;
import org.varukha.service.parser.impl.AttributeParserStrategyImpl;
import org.varukha.service.parser.impl.CargoCategoryParserImpl;
import org.varukha.service.parser.impl.DeliveryStatusParserImpl;
import org.varukha.util.PathBuilder;
import org.varukha.util.Validator;

/**
 * Main class for the delivery service app.
 * This class serves as the entry point for the application.
 * It parses command-line arguments, performs file parsing and statistic generation,
 * and writes the results to XML files.
 */
public class Main {
    /**
     * Map containing attribute names and corresponding AttributeParsers.
     * Used for mapping attribute names to their respective parsers.
     */
    public static final Map<String, AttributeParser> ATTRIBUTE_STRATEGY_MAP = Map.of(
            StatisticAttributes.CARGO_CATEGORY.getAttributeName(),
            new CargoCategoryParserImpl(),
            StatisticAttributes.DELIVERY_STATUS.getAttributeName(),
            new DeliveryStatusParserImpl());
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        // Validate and retrieve command line arguments
        String[] validCommandLineArguments = Validator.validateCommandLineArguments(args);
        String directoryPath = validCommandLineArguments[0];
        String attributeName = validCommandLineArguments[1];

        long startTime = System.currentTimeMillis();

        // Creating an attribute parser strategy based on the predefined strategy map
        AttributeParserStrategy attributeParserStrategy =
                new AttributeParserStrategyImpl(ATTRIBUTE_STRATEGY_MAP);

        // Retrieving the appropriate attribute parser based on the provided attribute name
        AttributeParser attributeParser = attributeParserStrategy
                .getAttributeParser(attributeName);

        // Creating a StatisticService implementation with the chosen attribute parser
        StatisticService statisticService = new StatisticServiceImpl(attributeParser);

        // Creating a JsonReader object to inject to JsonParserService
        JsonReader jsonReader = new JsonReader();

        // Creating a JSON parser implementation with the chosen StatisticService and JsonReader
        JsonParserService jsonParserImpl = new JsonParserImpl(statisticService, jsonReader);

        // Validating the directory containing JSON files
        File directory = new File(directoryPath);
        Validator.validateDirectory(directory);

        // Parsing JSON files and generating statistics based on the provided attribute
        Map<String, Integer> statistics = jsonParserImpl
                .parseJsonByAttributes(directory, attributeName);

        // Building the report path for the XML file based on the attribute name
        String reportPath = PathBuilder.buildReportPath(attributeName);

        // Writing the generated statistics to an XML file
        XmlStatisticsWriter xmlStatisticsWriter = new XmlStatisticsWriter();
        xmlStatisticsWriter.writeStatisticsToFile(statistics, reportPath, attributeName);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOGGER.info("Total time taken to read all files: {} milliseconds", totalTime);
    }
}
