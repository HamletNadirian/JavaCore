package org.profITsoft;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonReaderTest {

    @Test
    void testProcessSingleFile(@TempDir Path tempDir) throws Exception {

        Path testDir = tempDir.resolve("test");
        Files.createDirectories(testDir);

        Path testFile = testDir.resolve("test.json");
        String jsonContent = """
                [
                {"title": "The Clockmaker of Briar Lane", "producer": "Piers Ashcombe", "year_of_manufacture": 1889, "genre": "Steampunk, Adventure" },
                  {"title": "Salt of the Moon", "producer": "Talia Okeke", "year_of_manufacture": 1994, "genre": "Magical Realism" }
                ]
                """;
        Files.writeString(testFile,jsonContent);

        Map<String,Integer> result = JsonReader.calculateStatistics(
                testDir.toString(),
                "genre",
                16
        );
        assertEquals(3,result.size());
        assertEquals(1,result.get("Adventure"));
        assertEquals(1,result.get("Steampunk"));
        assertEquals(1,result.get("Magical Realism"));
    }
}
