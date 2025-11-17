package org.profITsoft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWriteToXml {

    @Test
    void testXmlWriting()throws Exception{
        Map<String,Integer> statistics = Map.of(
                "Action",5,
                "Drama",10
        );

        String attribute = "genre";
        String fileName = "statistics_by_"+attribute+".xml";

        WriteToXml.writeStatisticsToXml(statistics,attribute);

        Path workingDir = Path.of("output").toAbsolutePath();

        Path xmlFile = workingDir.resolve(fileName);

        assertTrue(Files.exists(xmlFile),"XML file was not created!");

        String content = Files.readString(xmlFile);

        assertTrue(content.contains("<statistics>"));
        assertTrue(content.contains("<value>Action</value>"));
        assertTrue(content.contains("<count>5</count>"));
        assertTrue(content.contains("<value>Drama</value>"));
        assertTrue(content.contains("<count>10</count>"));

        Files.deleteIfExists(xmlFile);
    }
}
