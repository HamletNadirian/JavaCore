package org.profITsoft;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static java.lang.System.exit;

public class App {

    private static final Set<String> VALID_ATTRIBUTES = Set.of("title", "year", "producer", "genre");

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("You should 2 arguments use <path> <attribute>");
            System.err.println("Available attributes:" + String.join(", ", VALID_ATTRIBUTES));
            System.out.println("For example: java JavaCore ./data genre");
            exit(1);
        }

        String path = args[0];
        String attribute = args[1];

        if (!VALID_ATTRIBUTES.contains(attribute)) {
            System.err.println("Error: incorrect attribute");
            System.err.println("Enter one of these attributes: " + String.join(", ", VALID_ATTRIBUTES));
            exit(1);
        }

        if (!Files.exists(Paths.get(path))) {
            System.err.println("Path does not exist: " + path);
            exit(1);
        }
        try {
            Map<String, Integer> statisticsDate =
                    JsonReader.calculateStatistics(path, attribute, Runtime.getRuntime().availableProcessors()
                    );
            WriteToXml.writeStatisticsToXml(statisticsDate, attribute);
            System.out.println("Ready! Created file statistics_by_" + attribute + ".xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
