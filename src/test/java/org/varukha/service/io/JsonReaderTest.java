package org.varukha.service.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.varukha.entity.Cargo;

/**
 * Test class for the {@link JsonReader} class, responsible for testing
 * the JSON file loading functionality.
 */
public class JsonReaderTest {
    private static final Cargo TEST_CARGO_1 = new Cargo();
    private static final Cargo TEST_CARGO_2 = new Cargo();

    private final File invalidFile = new File("invalidFile.json");
    private final File emptyFile = new File("src/test/resources/test_data/test_empty.json");
    private final File file = new File("src/test/resources/test_data/test_data.json");
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private JsonFactory jsonFactory;
    @Mock
    private JsonParser jsonParser;
    @InjectMocks
    private JsonReader jsonReader;

    public JsonReaderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setUpAll() {
        TEST_CARGO_1.setId(1L);
        TEST_CARGO_1.setDescription("Clothing");
        TEST_CARGO_1.setWeight(10.5);
        TEST_CARGO_1.setDestination("New York");

        TEST_CARGO_2.setId(2L);
        TEST_CARGO_2.setDescription("Electronics");
        TEST_CARGO_2.setWeight(15.0);
        TEST_CARGO_2.setDestination("Chicago");
    }

    /**
     * Tests the {@link JsonReader#loadJsonFile(File)} method to ensure
     * it correctly loads and parses a valid JSON file.
     */
    @Test
    void testLoadJsonFile() throws IOException {
        when(objectMapper.getFactory()).thenReturn(jsonFactory);
        when(jsonFactory.createParser(file)).thenReturn(jsonParser);
        when(objectMapper.readValue(jsonParser, Cargo.class))
                .thenReturn(TEST_CARGO_1)
                .thenReturn(TEST_CARGO_2)
                .thenReturn(null);
        List<Cargo> expectedResult = new ArrayList<>(
                List.of(TEST_CARGO_1, TEST_CARGO_2));
        List<Cargo> result = jsonReader.loadJsonFile(file);
        assertEquals(expectedResult.size(), result.size());
    }

    /**
     * Tests the {@link JsonReader#loadJsonFile(File)} method when an empty JSON file
     * is provided. It verifies that an empty list is returned.
     */
    @Test
    public void testLoadJsonFile_EmptyJsonFile_ReturnsEmptyList()
            throws IOException {
        when(objectMapper.getFactory()).thenReturn(jsonFactory);
        when(jsonFactory.createParser(file)).thenReturn(jsonParser);
        when(jsonParser.nextToken()).thenReturn(null);
        List<Cargo> actualCargoList = jsonReader.loadJsonFile(emptyFile);
        assertEquals(Collections.emptyList(), actualCargoList);
    }

    /**
     * Tests the {@link JsonReader#loadJsonFile(File)} method when an invalid JSON file
     * is provided. It verifies that a RuntimeException is thrown.
     */
    @Test
    public void testLoadJsonFile_InvalidJsonFile_ThrowsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> jsonReader.loadJsonFile(invalidFile));
    }

    /**
     * Tests the {@link JsonReader#loadJsonFile(File)} method when an invalid JSON file
     * is provided. It verifies that the thrown exception message is correct.
     */
    @Test
    public void testLoadJsonFile_InvalidJsonFile_ThrowsExceptionWithCorrectMessage() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> jsonReader.loadJsonFile(invalidFile));
        assertEquals("Error: The specified file invalidFile.json"
                + " is not a valid JSON file.", exception.getMessage());
    }
}
