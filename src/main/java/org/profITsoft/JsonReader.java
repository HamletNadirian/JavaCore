package org.profITsoft;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static void parseLargeJsonFile(
            Path file,
            ExecutorService executorService,
            ConcurrentHashMap<String, Integer> statistics,
            String attribute
    ) throws Exception {
        JsonFactory factory = new JsonFactory();
        try (JsonParser jsonParser = factory.createParser(file.toFile())) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new RuntimeException("JSON must contain an array of movies: " + file);
            }

            while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                Movie movie = mapper.readValue(jsonParser, Movie.class);

                executorService.submit(() ->
                        CalculationOfStatistics.processMovie(movie, attribute, statistics)
                );
            }
        }
    }

    public static Map<String, Integer> calculateStatistics(
            String path,
            String attribute,
            int threadCount) throws Exception {
        ConcurrentHashMap<String, Integer> statistics = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        try {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path), "*.json")) {
                for (Path file : stream) {
                    parseLargeJsonFile(file, executorService, statistics, attribute);
                }
            }
            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                System.err.println("Forcing shutdown of ExecutorService after timeout");
                executorService.shutdown();
                //даємо доп.час
                if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                    throw new RuntimeException("ExecutorService failed to terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdown();
            Thread.currentThread().interrupt();
            throw new RuntimeException("Processing was interrupted", e);
        }
        return statistics;
    }
}
