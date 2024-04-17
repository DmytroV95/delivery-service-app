package org.varukha.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.varukha.entity.Cargo;
import org.varukha.service.JsonParserService;
import org.varukha.service.StatisticService;
import org.varukha.service.io.JsonReader;
import org.varukha.util.Validator;

/**
 * Implementation of the JsonParserService interface for parsing JSON files
 * and generating statistics based on specified attributes.
 */
public class JsonParserImpl implements JsonParserService {
    private static final int THREADS_NUMBER = 8;
    private static final Logger LOGGER = LogManager.getLogger(JsonParserImpl.class);
    private final StatisticService statisticService;
    private final JsonReader jsonReader;

    public JsonParserImpl(StatisticService statisticService, JsonReader jsonReader) {
        this.statisticService = statisticService;
        this.jsonReader = jsonReader;
    }

    /**
     * Parses JSON files in the specified directory, extracts attributes
     * based on the given attribute name, and generates statistics.
     *
     * @param directory     the directory containing JSON files to parse
     * @param attributeName the name of the attribute to extract from JSON objects
     * @return a map containing statistics based on the extracted attributes
     */
    public Map<String, Integer> parseJsonByAttributes(File directory, String attributeName) {
        List<Future<List<Cargo>>> futures = new ArrayList<>();
        Map<String, Integer> statistics = new HashMap<>();

        File[] files = directory.listFiles();
        Validator.validateFilesExist(files);

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        for (File file : files) {
            Validator.validateJson(file);
            futures.add(executor.submit(() -> jsonReader.loadJsonFile(file)));
        }
        for (Future<List<Cargo>> future : futures) {
            try {
                List<Cargo> cargoList = future.get();
                statisticService.updateStatistics(attributeName, statistics, cargoList);
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Error occurred while parsing files: " + e.getMessage());
            }
        }
        executor.shutdown();
        return statisticService.sortAndCombineStatistics(statistics);
    }
}
