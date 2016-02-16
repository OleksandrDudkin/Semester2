package Pr_1_1_XML.DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 04.02.2016.
 */
public class DOMParser {

    public static Document parse(String fileName) throws ParserConfigurationException, IOException, SAXException {
        //возвращем ДОМ-документ с данными из файла
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Document doc = parse(".\\src\\Pr_1_1_XML\\DOMParser\\hobby.xml");
        NodeList items = doc.getDocumentElement().getChildNodes(); //достаем корневой элемент, а от него все дочерние

        for (int i = 0; i < items.getLength(); i++) {
            if (items.item(i).getNodeName().equals("hobby")) { // для всех нод обеспечиваем вывод информации о хобби
                Element element = (Element) items.item(i); // создаем из йтема объект типа элемент и...
                System.out.println("Hobby number " + element.getAttribute("number")); // выводим значение атрибута number у элемента
                printHobby(items.item(i)); // вызываем метод, который выводит все содержимое узла hobby
            }
        }
    }

    private static void printHobby(Node node) {
        NodeList nodes = node.getChildNodes(); //создаем список дочерних узлов в каждом хобби (name, status, duration)

        for (int i = 0; i < nodes.getLength(); i++) { //в цикле выводим содержимое каждого тега внутри хобби
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("" + nodes.item(i).getNodeName() + ": " + //печатаем имя и...
                        nodes.item(i).getFirstChild().getNodeValue()); //значение тега
            }
        }
        System.out.println("");
    }

}
