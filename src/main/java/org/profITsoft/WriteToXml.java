package org.profITsoft;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class WriteToXml {
    public static void writeStatisticsToXml(Map<String, Integer> statistics, String attribute) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("statistics");
        document.appendChild(root);

        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            Element item = document.createElement("item");

            Element value = document.createElement("value");
            value.appendChild(document.createTextNode(entry.getKey()));

            Element count = document.createElement("count");
            count.appendChild(document.createTextNode(String.valueOf(entry.getValue())));

            item.appendChild(value);
            item.appendChild(count);
            root.appendChild(item);
        }
        Path projectRoot = Path.of("").toAbsolutePath();

        File outputDir = projectRoot.resolve("output").toFile();
        if (!outputDir.exists()&&!outputDir.mkdirs()){
            throw new RuntimeException("Failed to create output directory:"+outputDir.getAbsolutePath());
        }

        File xmlFile = new File(outputDir, "statistics_by_" + attribute + ".xml");

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(xmlFile));
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

    }
}
