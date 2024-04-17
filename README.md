# Delivery Service App (Console Application)

This Java console application parses a collection of JSON files representing
entities and generates statistics based on one of their attributes.
The program supports processing multiple attributes, with the user specifying
one of them as the basis for generating statistics.

## Content

- [Input Parameters](#application-input-parameters)
- [Application response](#application-response)
- [Thread Pool Experiment Results](#thread-pool-experiment-results)
- [Instructions for Running the Application](#instructions-for-running-the-application)
- [Entities Description](#entities-description)
- [Generating Dummy JSON Data Files](#generating-dummy-json-data-files-with-cargo-information)
  

## Application Input Parameters

The application requires the following input parameters:
(refer to the instructions below for running the application)

- Path to the folder containing JSON files.
- [Attribute name](#please-select-one-of-the-available-attributes)
  for generating statistics.

[Go back to top](#delivery-service-app-console-application)

## Application response

The application generates an XML file with statistics sorted by count in descending order.
The filename for the output file follows the format: `statistics_by_{attribute}.xml`.

For instance, if the statistics are based on the genre attribute, the output file
`statistics_by_genre.xml` might look like this:

```xml
<statistics>
  <item>
    <value>Electronics</value>
    <count>56</count>
  </item>
  <item>
    <value>Food</value>
    <count>13</count>
  </item>
  <!-- Other cargo category -->
</statistics>
```
[Go back to top](#delivery-service-app-console-application)

## Thread Pool Experiment Results

The following table shows the performance comparison for parsing JSON files with
different thread pool sizes.

| JSON Files Count | JSON Objects / File | Threads Quantity | Time Taken (milliseconds) |
|:----------------:|:-------------------:|:----------------:|:-------------------------:|
|       1000       |         1000        |         1        |           2602            |
|                  |                     |         2        |           1667            |
|                  |                     |         4        |           1143            |
|                  |                     |         8        |            939            |


The above results are for illustration purposes. Actual performance may vary based
on system specifications and file sizes.

[Go back to top](#delivery-service-app-console-application)

## Instructions for Running the Application

To run the application, follow these steps:

1. **Build the Application**: Before running the application, you need to build the
project and generate the JAR file. You can do this using Maven by executing the command:
```mvn clean package```
2. **Execute the Application**: Once the JAR file is generated, you can run the
application using the `java -jar` command. Provide the required input parameters
when prompted.
   - To run the console application using the previously built JAR package, use the
following command: (Remember to add the absolute path to the
'src\main\resources\json_data_set' folder)

```java -jar DeliveryService-1.0-SNAPSHOT-jar-with-dependencies.jar <Here absolute path to \src\main\resources\json_data_set> cargo_category```
   - Here, `DeliveryService-1.0-SNAPSHOT-jar-with-dependencies.jar` is the name of the JAR file
generated in the `target` directory. The `first argument` is the path to the folder
containing the JSON files, and the `second argument` is the attribute name by which
the report will be generated. 

 - The report will be generated in the following folder:
```src/main/resources/xml_statistic_report```

### Please select one of the available attributes:
   - delivery_status
   - cargo_category

[Go back to top](#delivery-service-app-console-application)

3.**Using Java Properties in IntelliJ IDEA**: Alternatively, you can provide
the arguments as Java properties directly in IntelliJ IDEA. To do this, follow these steps:
- Open IntelliJ IDEA and navigate to the Run/Debug Configurations.
- Create a new Application configuration for your project.
- In the Configuration tab, specify the main class.
- In the **Program arguments** options field, add the following:
  ```
  <Absolute path to \src\main\resources\json_data_set> <one of the available
  attributes (see options above)>
  ```
- Click Apply and then OK to save the configuration.
- Now, using IntelliJ IDEA you can run the application from the Main class, it will
use the specified arguments.

By following these steps, you can run the application and generate the desired report
based on the provided JSON data.

[Go back to top](#delivery-service-app-console-application)

## Entities Description

The application deals with cargo-related entities represented by the following classes:
<br>**Note:** During the development of the project, the fields and attributes of these entities may be changed.

### Vehicle
- **id**: The unique identifier of the vehicle.
- **type**: The type of the vehicle (e.g., Car, Truck, Plane).
- **vehicleNumber**: The registration number of the vehicle.
- **cargoCapacity**: The maximum cargo capacity of the vehicle.
- **route**: The route assigned to the vehicle.

### Cargo
- **id**: The unique identifier of the cargo.
- **description**: A brief description of the cargo.
- **weight**: The weight of the cargo.
- **dimensions**: The dimensions of the cargo.
- **destination**: The destination of the cargo.
- **status**: The delivery status of the cargo (e.g., In Transit, Delivered).
- **categories**: The categories to which the cargo belongs (e.g., Food, Electronics).
- **vehicleInfo**: Information about the vehicle assigned to transport the cargo.

The application also includes several enums that define constants for cargo categories, delivery statuses, vehicle types, and statistic attributes.

#### Below is an example of how a single cargo entity is represented in JSON:

```json
[
  {
    "id": 1,
    "description": "Cargo 1",
    "weight": 20.93,
    "dimensions": "10x10x10",
    "destination": "Destination 1",
    "status": "PENDING",
    "categories": [
      "FURNITURE",
      "BOOKS",
      "OTHER",
      "CLOTHING",
      "FOOD"
    ],
    "vehicleInfo": {
      "id": 1,
      "type": "PLANE",
      "vehicleNumber": "ABC123",
      "cargoCapacity": 215.37,
      "route": "Route 3"
    }
  }
]
```
[Go back to top](#delivery-service-app-console-application)

## Generating Dummy JSON Data Files with Cargo Information

The `JsonDataGenerator` class in the project provides a utility for generating JSON data files containing dummy cargo information. Here's how to use it:

### Location of the Generator

You can find the `JsonDataGenerator` class at the following path in the project structure:
```
src/main/java/org/varukha/util/jsongenerator/JsonDataGenerator.java
```

### Setting Parameters

The generator has default values set for the number of JSON files (`NUMBER_JSON_FILES`) and the number of cargoes per file (`NUMBER_CARGOES_PER_FILE`). These values are set to 10 and 100 respectively. You can adjust these values based on your requirements by modifying the constants in the `JsonDataGenerator` class.

### Generation Process

When you run the `main` method of the `JsonDataGenerator` class, it will generate JSON files containing cargo information. Each file will contain cargo objects with randomly generated attributes. The cargo objects include an ID, description, weight, dimensions, destination, status, categories, and vehicle information.

### Directory for Generated Files

The generated JSON files are saved in the directory specified by the constant `JSON_DATA_SET_PATH`, which is set to `"src/main/resources/json_data_set"`. You can change this path if needed by modifying the constant in the `JsonDataGenerator` class.

### JSON File Naming Convention

The generated JSON files are named in the format `cargo_data_{index}.json`, where `{index}` represents the index of the file starting from 1.

### Running the Generator

To generate the JSON data files, simply execute the `main` method of the `JsonDataGenerator` class. This will initiate the generation process, and the files will be saved in the specified directory.

### Adjusting Parameters

If you need to generate a different number of files or cargoes per file, you can modify the respective constants in the `JsonDataGenerator` class and rerun the generator.

[Go back to top](#delivery-service-app-console-application)
#