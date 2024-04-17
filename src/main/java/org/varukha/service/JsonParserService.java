package org.varukha.service;

import java.io.File;
import java.util.Map;

/**
 * Interface for parsing JSON files, extracting attributes based on a given attribute name,
 * and generating statistics.
 */
public interface JsonParserService {

    /**
     * Parses JSON files in the specified directory, extracts attributes
     * based on the given attribute name, and generates statistics.
     *
     * @param directory     the directory containing JSON files to parse
     * @param attributeName the name of the attribute to extract from JSON objects
     * @return a map containing statistics based on the extracted attributes
     */
    Map<String, Integer> parseJsonByAttributes(File directory, String attributeName);
}
