package Pr_1_1_XML.SAXParser;

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
        SAXParser saxParser = new SAXParser();
        saxParser.process();
    }

    public void process() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true); // parser produced by this code will provide support for XML-namespaces
        javax.xml.parsers.SAXParser parser = factory.newSAXParser();

        FileInputStream fis = new FileInputStream(new File(".\\src\\Pr_1_1_XML\\SAXParser\\hobby.xml"));
        InputSource source = new InputSource(fis);

        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setContentHandler(new MyContentHandler());
        xmlReader.parse(source);
    }
}
