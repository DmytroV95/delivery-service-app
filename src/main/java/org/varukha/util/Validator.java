package org.varukha.util;

import static org.varukha.Main.ATTRIBUTE_STRATEGY_MAP;

import java.io.File;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for validating directories and files.
 */
public class Validator {
    private static final String FILE_EXTENSION_JSON = ".json";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger LOGGER = LogManager.getLogger(Validator.class);

    /**
     * Validates whether the specified directory exists and is indeed a directory.
     *
     * @param directory the directory to validate
     * @throws IllegalArgumentException if the directory does not exist or is not a directory
     */
    public static void validateDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            String logMessage = String.format(
                    "Error! The specified directory %s does not exist"
                            + " or is not a directory!", directory.getPath());
            LOGGER.error(logMessage);
            throw new IllegalArgumentException(logMessage);
        }
    }

    /**
     * Validates whether the specified array of files is not null and not empty.
     *
     * @param files the array of files to validate
     * @throws IllegalArgumentException if the array of files is null or empty
     */
    public static void validateFilesExist(File[] files) {
        if (files == null || files.length == 0) {
            String logMessage = "Error! No files were found found";
            LOGGER.error(logMessage);
            throw new IllegalArgumentException("Error! No files were found."
                    + " Please generate the data set.");
        }
    }

    /**
     * Validates whether the specified file has a JSON extension.
     *
     * @param file the file to validate
     * @throws IllegalArgumentException if the file does not have a JSON extension
     */
    public static void validateJson(File file) {
        if (!file.getName().endsWith(FILE_EXTENSION_JSON)) {
            String logMessage = String.format("Error: The specified file '%s'"
                    + " is not a valid JSON file.", file.getName());
            LOGGER.error(logMessage);
            throw new IllegalArgumentException(logMessage);
        }
    }

    /**
     * Validates the command line arguments to ensure that both a directory path and
     * an attribute name are provided. If the number of arguments is less than 2,
     * the first argument is not a valid directory path, or the second
     * argument is not an available attribute name, prompts the user to enter the
     * correct arguments until valid inputs are provided.
     *
     * @param args The array of command line arguments.
     * @return An array containing the validated directory path and attribute name.
     */
    public static String[] validateCommandLineArguments(String[] args) {
        String[] arguments = new String[2];
        while (args.length < 2 || !isDirectory(args[0]) || !isAttributeNameAvailable(args[1])) {
            if (args.length < 2) {
                LOGGER.info("Please enter both directory path and attribute name.");
            } else if (!isDirectory(args[0])) {
                LOGGER.info(" Wrong path: {} The first argument should be a valid directory path."
                        + " Please enter both directory path and attribute name.", args[0]);
            } else if (!isAttributeNameAvailable(args[1])) {
                LOGGER.error("The entered attribute name: {} is not valid."
                        + " Here is a list of available attributes:", args[1]);
                showAvailableAttributeNames();
                LOGGER.error("""
                        Please enter both directory and attribute name:""");
            }
            args = SCANNER.nextLine().trim().split("\\s+");
        }
        arguments[0] = args[0];
        arguments[1] = args[1];
        return arguments;
    }

    /**
     * Checks if the specified path corresponds to a directory.
     *
     * @param path The path to check.
     * @return {@code true} if the path represents a directory, {@code false} otherwise.
     */
    private static boolean isDirectory(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    /**
     * Checks if the specified argument name is available in the attribute strategy map.
     *
     * @param attributeName The name of the attribute to check.
     * @return {@code true} if the attribute name is available in the map, {@code false} otherwise.
     */
    private static boolean isAttributeNameAvailable(String attributeName) {
        return ATTRIBUTE_STRATEGY_MAP.containsKey(attributeName);
    }

    /**
     * Prints all keys in the ATTRIBUTE_STRATEGY_MAP to the console.
     */
    private static void showAvailableAttributeNames() {
        ATTRIBUTE_STRATEGY_MAP.keySet().forEach(System.out::println);
    }
}
