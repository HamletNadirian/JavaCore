package org.profITsoft;

import java.util.concurrent.ConcurrentMap;

public class CalculationOfStatistics {
    public static void processMovie(
            Movie movie, String attribute,
            ConcurrentMap<String, Integer> result) {

        String key = switch (attribute) {
            case "title" -> movie.getTitle();
            case "year" -> String.valueOf(movie.getYear_of_manufacture());
            case "producer" -> movie.getProducer();
            case "genre" -> movie.getGenre();
            default -> throw new IllegalArgumentException("Unknown attribute: " + attribute);
        };

        if (attribute.equals("genre")) {
            String[] genres = key.split(",");

            for (String genre : genres) {
                String cleaned = genre.trim();
                if (!cleaned.isEmpty()) {
                    result.merge(cleaned, 1, Integer::sum);
                }
            }
        } else {
            result.merge(key.trim(), 1, Integer::sum);
        }
    }
}
