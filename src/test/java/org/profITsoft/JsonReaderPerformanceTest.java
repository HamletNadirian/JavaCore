package org.profITsoft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderPerformanceTest {
    private static final String TEST_PATH = "./data";
    private static final String TEST_ATTRIBUTE = "genre";

    @Test
    @BeforeEach
    void setUp() {
        assertTrue(Files.exists(Paths.get(TEST_PATH)),
                "Test data directory must exist: " + TEST_PATH);
    }

    @Test
    void comparePerformanceAcrossThreadCounts() throws Exception {
        int[] threadCounts = {1, 2, 4, 8};

        System.out.println("Performance Benchmark");
        for (int threadCount : threadCounts) {
            JsonReader.processFiles(TEST_PATH, TEST_ATTRIBUTE, threadCount);

            long startTime = System.currentTimeMillis();
            Map<String, Integer> result = JsonReader.processFiles(
                    TEST_PATH,
                    TEST_ATTRIBUTE,
                    threadCount
            );
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.printf("Threads: %2d - > Time: %5d ms%n", threadCount, duration);
            assertNotNull(result);
        }
    }
    @Test
    void testCorrectness()throws Exception{
        Map<String,Integer> resultOneThread = JsonReader.processFiles(TEST_PATH,TEST_ATTRIBUTE,1);
        Map<String,Integer> resultEightThreads = JsonReader.processFiles(TEST_PATH,TEST_ATTRIBUTE,8);

        assertEquals(resultOneThread.size(),resultEightThreads.size(),
                "Results should be identical");
        assertEquals(resultOneThread,resultEightThreads,
                "Results should match!");

    }
}
