package Practica1_XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 04.02.2016.
 * Разработать на Java консольное приложение, имеющее два входных параметра:
 * имена входного и выходного файла. Задача приложения заключается в проверке
 * значения средней оценки и его коррекции, если в исходном документе оно не
 * соответствует действительности с точностью до 1 десятой. Приложение должно
 * проверять файл на соответствие указанному DTD.
 */
public class DemoMarkCorrector {
    static String INIT_FILE_PATH = ".\\src\\Practica1_XML\\group.xml";
    static String CORRECTED_FILE_PATH = ".\\src\\Practica1_XML\\correctedGroup.xml";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        //получение документа после проверки на ДТД
        Document document = MarkCorrector.parse(INIT_FILE_PATH);

        //проверка и исправление значения
        Document correctedDocument = MarkCorrector.correctAllAverageMarks(document);

        //вывод на экран исправленного документа
        System.out.println("Data after correcting:\n");
        NodeList items = correctedDocument.getDocumentElement().getChildNodes(); //достаем корневой элемент, а от него все дочерние
        for (int i = 0; i < items.getLength(); i++) {
            if (items.item(i).getNodeName().equals("student")) {
                Element element = (Element) items.item(i);
                System.out.println("student: " + element.getAttribute("firstname") +
                        " " + element.getAttribute("lastname") +
                        " " + element.getAttribute("groupnumber"));
                MarkCorrector.printStudent(items.item(i));
            }
        }

        //запись документа в файл
        MarkCorrector.writeDocumentToXml(CORRECTED_FILE_PATH, correctedDocument);
    }
}
