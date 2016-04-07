//package Practica1_XML;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by Oleksandr Dudkin on 04.02.2016.
 * <p>
 * Разработать на Java консольное приложение, имеющее два входных параметра:
 * имена входного и выходного файла. Задача приложения заключается в проверке
 * значения средней оценки и его коррекции, если в исходном документе оно не
 * соответствует действительности с точностью до 1 десятой. Приложение должно
 * проверять файл на соответствие указанному DTD.
 *  * КАК ЗАПУСТИТЬ
 * 1. Закоментировать package
 * 2. прописать переменную среды CLASSPATH - путь до корня проекта включительно : E:\Admin\OneDrive\Java\Semester2\
 * 3. В ком. строке откомплировать:
 * E:\Admin\OneDrive\Java\Semester2\src\Practica1_XML>javac MarkCorrectorConsole.java
 * 4. В ком. строке запустить с двумя параметрами:
 * E:\Admin\OneDrive\Java\Semester2\src\Practica1_XML>java MarkCorrectorConsole group.xml correctedGroup.xml
 *
 * </p>
 */
public class MarkCorrectorConsole {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        //получить имена входного и выходного файла
        if(args.length < 2) {
            System.out.println("You should input 2 names - for input and output xml-files.\n");
            System.exit(1);
        }
        String initFilePath = args[0];
        System.out.println(initFilePath);
        String correctedFilePath = args[1];
        System.out.println(correctedFilePath);

        //получение документа после проверки на ДТД
        Document document = parse(initFilePath);

        //проверка и исправление значения
        Document correctedDocument = correctAllAverageMarks(document);

        //вывод на экран исправленного документа
        System.out.println("Data after correcting:\n");
        NodeList items = correctedDocument.getDocumentElement().getChildNodes(); //достаем корневой элемент, а от него все дочерние
        for (int i = 0; i < items.getLength(); i++) {
            if (items.item(i).getNodeName().equals("student")) {
                Element element = (Element) items.item(i);
                System.out.println("student: " + element.getAttribute("firstname") +
                        " " + element.getAttribute("lastname") +
                        " " + element.getAttribute("groupnumber"));
                printStudent(items.item(i));
            }
        }

        //запись документа в файл
        writeDocumentToXml(correctedFilePath, correctedDocument);
    }

    //возвращет документ с данными из файла, проверив предварительно на ДТД
    public static Document parse(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(true); // вызов валидации файла на ДТД
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        builder.setErrorHandler(new ErrorHandler() { //обрабатываем ошибки
            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println("The initial XML-file is not corresponding to its DTD!");
                exception.printStackTrace();
                System.exit(1);
            }
            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                exception.printStackTrace();
                System.exit(1);
            }

            @Override
            public void warning(SAXParseException exception) throws SAXException {
                exception.printStackTrace();
                System.exit(1);
            }
        });
        return builder.parse(fileName);
    }

    //возвращает документ с исправленным средним для всех студентов
    public static Document correctAllAverageMarks(Document document) {
        NodeList items = document.getDocumentElement().getChildNodes(); //достаем корневой элемент, а от него все дочерние
        for (int i = 0; i < items.getLength(); i++) {
            if (items.item(i).getNodeName().equals("student")) { // для всех нод обеспечиваем вывод информации о студенте
                correctAverageMarkForStudent(items.item(i));
            }
        }
        return document;
    }

    //считает среднее для студента, находит расхождение, если больше 0,1, то исправляет
    private static void correctAverageMarkForStudent(Node node) {
        NodeList nodes = node.getChildNodes(); // все теги для отдельного студента

        Double calculatedAverage = 0d;
        Integer totalMark = 0;
        int amountOfSubjects = 0;
        Double givenAverage = 0d;

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE && nodes.item(i).getNodeName().equals("subject")) {
                NamedNodeMap atts = nodes.item(i).getAttributes();
                totalMark += new Integer(atts.getNamedItem("mark").getNodeValue());
                amountOfSubjects += 1;
                calculatedAverage = (double) totalMark / amountOfSubjects;
            }
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE && nodes.item(i).getNodeName().equals("average")) {
                givenAverage = new Double(nodes.item(i).getTextContent());
            }
        }

        if (Math.abs(calculatedAverage - givenAverage) > 0.1) {
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE && nodes.item(i).getNodeName().equals("average")) {
                    nodes.item(i).setTextContent(String.valueOf(calculatedAverage));
                    //System.out.println(nodes.item(i).getTextContent());
                }
            }
        }
    }

    //вывод на печать данных студента
    public static void printStudent(Node node) {
        NodeList nodes = node.getChildNodes(); //создаем список дочерних узлов в каждом студенте
        for (int i = 0; i < nodes.getLength(); i++) { //в цикле выводим содержимое каждого тега
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE && nodes.item(i).getNodeName().equals("average")) {
                System.out.println("" + nodes.item(i).getNodeName() + ": " + //печатаем имя и...
                        nodes.item(i).getFirstChild().getNodeValue()); //значение тега
            }

            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE && nodes.item(i).getNodeName().equals("subject")) {
                NamedNodeMap atts = nodes.item(i).getAttributes();
                System.out.print(atts.getNamedItem("title"));
                System.out.println(atts.getNamedItem("mark"));
            }

        }
        System.out.println("");
    }

    //запись в файл
    private static void writeDocumentToXml(String newFilePath, Document document) {
        Source domSource = new DOMSource(document);
        Result fileResult = new StreamResult(new File(newFilePath));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer();
            transformer.transform(domSource, fileResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
