package org.varukha.util.jsongenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.varukha.Main;
import org.varukha.entity.Cargo;
import org.varukha.entity.Vehicle;
import org.varukha.entity.enums.CargoCategory;
import org.varukha.entity.enums.DeliveryStatus;
import org.varukha.entity.enums.VehicleType;

/**
 * Utility class for generating JSON data files with dummy cargo data.
 */
public class JsonDataGenerator {
    private static final int NUMBER_JSON_FILES = 10;
    private static final int NUMBER_CARGOES_PER_FILE = 100;
    private static final String JSON_DATA_SET_PATH = "src/main/resources/json_data_set";
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        generateAndSaveJsonFiles();
    }

    /**
     * Generates and saves JSON files with dummy cargo data.
     */
    private static void generateAndSaveJsonFiles() {
        int totalCargoes = NUMBER_JSON_FILES * NUMBER_CARGOES_PER_FILE;

        int maxNumCategories = CargoCategory.values().length;
        List<Cargo> cargoes = generateDummyCargoes(totalCargoes, maxNumCategories);

        for (int i = 0; i < NUMBER_JSON_FILES; i++) {
            String fileName = "cargo_data_" + (i + 1) + ".json";
            List<Cargo> cargoesForFile = cargoes
                    .subList(i * NUMBER_CARGOES_PER_FILE, (i + 1) * NUMBER_CARGOES_PER_FILE);
            writeToJsonFile(cargoesForFile, fileName);
        }
    }

    /**
     * Generates a list of dummy cargoes with the specified number of cargoes
     * and maximum number of categories.
     *
     * @param numCargoes       The number of cargoes to generate.
     * @param maxNumCategories The maximum number of categories for each cargo.
     * @return A list of dummy cargoes.
     */
    private static List<Cargo> generateDummyCargoes(int numCargoes, int maxNumCategories) {
        List<Cargo> cargoes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numCargoes; i++) {
            cargoes.add(generateDummyCargo(random, i, maxNumCategories));
        }
        return cargoes;
    }

    /**
     * Generates a single dummy cargo with random attributes.
     *
     * @param random           Random object to generate random values.
     * @param index            The index of the cargo being generated.
     * @param maxNumCategories The maximum number of categories for the cargo.
     * @return A single dummy cargo.
     */
    private static Cargo generateDummyCargo(Random random,
                                            int index,
                                            int maxNumCategories) {
        Cargo cargo = new Cargo();
        cargo.setId((long) (index + 1));
        cargo.setDescription("Cargo " + (index + 1));
        cargo.setWeight(random.nextDouble() * 100);
        cargo.setDimensions(index + "0x" + index + "0x" + index + "0");
        cargo.setDestination("Destination " + (index + 1));
        cargo.setStatus(DeliveryStatus.values()[random.nextInt(
                DeliveryStatus.values().length)]);
        cargo.setCategories(generateRandomCategories(random, maxNumCategories));
        cargo.setVehicleInfo(generateDummyVehicle());
        return cargo;
    }

    /**
     * Generates a list of random cargo categories.
     *
     * @param random           Random object for generating random numbers.
     * @param maxNumCategories Maximum number of categories to generate.
     * @return List of generated cargo categories.
     */
    private static List<CargoCategory> generateRandomCategories(Random random,
                                                                int maxNumCategories) {
        List<CargoCategory> categories = new ArrayList<>();
        CargoCategory[] allCategories = CargoCategory.values();

        int numCategories = random.nextInt(maxNumCategories) + 1;
        while (categories.size() < numCategories) {
            CargoCategory randomCategory = allCategories[random.nextInt(allCategories.length)];
            if (!categories.contains(randomCategory)) {
                categories.add(randomCategory);
            }
        }
        return categories;
    }

    /**
     * Generates a dummy vehicle with random attributes.
     *
     * @return Generated dummy vehicle.
     */
    private static Vehicle generateDummyVehicle() {
        Random random = new Random();
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setType(VehicleType
                .values()[random.nextInt(VehicleType.values().length)]);
        vehicle.setVehicleNumber("ABC123");
        vehicle.setCargoCapacity(random.nextDouble() * 1000);
        vehicle.setRoute("Route " + (random.nextInt(5) + 1));
        return vehicle;
    }

    /**
     * Writes the given list of cargoes to a JSON file.
     *
     * @param cargoes  List of cargoes to write to the file.
     * @param fileName Name of the JSON file.
     */
    private static void writeToJsonFile(List<Cargo> cargoes, String fileName) {
        String filePath = JSON_DATA_SET_PATH + File.separator + fileName;
        try {
            File outputFile = new File(filePath);
            OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
            OBJECT_MAPPER.writeValue(outputFile, cargoes);
        } catch (IOException e) {
            LOGGER.error("An error occurred while writing JSON to file: {}", e.getMessage());
        }
    }
}
