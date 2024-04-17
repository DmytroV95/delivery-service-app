package org.varukha.service.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.varukha.entity.Cargo;

/**
 * Class for reading JSON files and parsing them into Cargo objects.
 * This class provides a method to load a JSON file
 * and parse its contents into a list of Cargo objects.
 */
public class JsonReader {
    private static final Logger LOGGER = LogManager.getLogger(JsonReader.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Loads JSON file into a list of Cargo objects.
     *
     * @param file the JSON file to parse
     * @return a list of Cargo objects parsed from the JSON file
     * @throws RuntimeException if the specified file is not a valid JSON file
     */
    public List<Cargo> loadJsonFile(File file) {
        List<Cargo> cargoList = new ArrayList<>();
        try (JsonParser parser = createJsonParser(file)) {
            while (parser.nextToken() != null) {
                if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
                    Cargo cargo = OBJECT_MAPPER.readValue(parser, Cargo.class);
                    cargoList.add(cargo);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error parsing the file {}", file.getName());
            throw new RuntimeException("Error: The specified file "
                    + file.getName() + " is not a valid JSON file.");
        }
        return cargoList;
    }

    /**
     * Creates a JsonParser for the specified file.
     *
     * @param file the JSON file
     * @return a JsonParser instance
     * @throws IOException if an I/O error occurs
     */
    private JsonParser createJsonParser(File file) throws IOException {
        return OBJECT_MAPPER.getFactory().createParser(file);
    }
}
