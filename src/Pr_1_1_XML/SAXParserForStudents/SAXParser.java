package Pr_1_1_XML.SAXParserForStudents;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SAXParser {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParser saxParser = new SAXParser(); //создаем экземпляр класса
        saxParser.process(); //запускаем метод process()
    }

    public void process() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance(); //создаем объект фабрики
        factory.setNamespaceAware(true); // parser produced by this code will provide support for XML-namespaces
        javax.xml.parsers.SAXParser parser = factory.newSAXParser(); //создаем объект parser

        FileInputStream fis = new FileInputStream(new File(".\\src\\Practica1_XML\\input.xml")); //создаем fis
        InputSource source = new InputSource(fis); // создаем объект source, куда помещаем fis

        XMLReader xmlReader = parser.getXMLReader(); // из парсера создаем объект xmlReader
        xmlReader.setContentHandler(new MyContentHandler()); //ему устанавливаем ContentHandler
        xmlReader.parse(source); // xmlReader парсит источник с данными - source
    }
}
