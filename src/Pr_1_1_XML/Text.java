package Pr_1_1_XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by Oleksandr on 30.01.2016.
 * Иксмл файлы с хобби, длительностью занятий
 * Вывести хобби, с длительностью больше половины максимального.
 * Найти макс, половину от максимального времени, сравнить и вывести те что больше.
 *
 * SAX разобраться: www.tutorialspoint.com почитать Дом и Сакс
 * UserHandler
 */
public class Text {

    public static void main(String[] args) {

        //все операции в блоке try
        try {
            File inputFile = new File(".\\src\\Pr_1_1_XML\\input.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();// создаем фабрику дом-парсера
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder(); // создаем билдера для построения ДОМ-документа
            Document doc = dbBuilder.parse(inputFile); //создаем ДОМ-документ и заносим туда данные из пропарсенного файла

            doc.getDocumentElement().normalize(); //возвращает корневой элемент документа и нормализет элементы ДОМ (нет нулевых нод или сдвоенных)

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); //выводим имя корневого элемента

            NodeList nList = doc.getElementsByTagName("student"); //по тегу student создаем список нод

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i); //создаем отдельную ноду
                System.out.println("Current element: " + nNode.getNodeName());//выводим ее имя

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {//если тип ноды это элемент
                    Element eElement = (Element)nNode; //создаем объект типа Element и присваеваем ему ноду
                    System.out.println("");
                    System.out.println("Student id: " + eElement.getAttribute("rollno")); //для каждого элемента выводим атрибут номера
                    System.out.println("Student name: " + eElement.getElementsByTagName("firstname").item(0).getTextContent()); // имя студента
                    System.out.println("Student surname: " + eElement.getElementsByTagName("lastname").item(0).getTextContent()); // фамилию
                    System.out.println("Student mark: " + eElement.getElementsByTagName("mark").item(0).getTextContent()); // оценку
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
