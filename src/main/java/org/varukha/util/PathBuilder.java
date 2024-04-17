package org.varukha.util;

import java.io.File;

/**
 * Utility class for building paths related to XML statistic reports.
 */
public class PathBuilder {
    private static final String STATISTIC_DIRECTORY_NAME = "xml_statistic_report";
    private static final String PREFIX_STATISTICS_BY = "statistics_by_";
    private static final String STATISTIC_FILE_EXTENSION = ".xml";

    /**
     * Builds the report path for the given attribute name.
     *
     * @param attributeName the attribute name
     * @return the report path for the given attribute name
     */
    public static String buildReportPath(String attributeName) {
        return new StringBuilder()
                .append(buildRelativeReportPath())
                .append(File.separator)
                .append(PREFIX_STATISTICS_BY)
                .append(attributeName)
                .append(STATISTIC_FILE_EXTENSION)
                .toString();
    }

    /**
     * Builds the relative directory path for the XML statistic report files.
     *
     * @return the relative directory path for the XML statistic report files
     */
    public static String buildRelativeReportPath() {
        return new StringBuilder()
                .append("src")
                .append(File.separator)
                .append("main")
                .append(File.separator)
                .append("resources")
                .append(File.separator)
                .append(STATISTIC_DIRECTORY_NAME)
                .toString();
    }
}
