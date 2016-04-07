package Practica1_XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MyContentHandler extends org.xml.sax.helpers.DefaultHandler{ //класс наследует DefaultHandler

    private int level = 0; //уровень вложенности xml-тега
    private boolean inStudent = false;
    private StringBuffer text = new StringBuffer();

    @Override
    public void startDocument() throws SAXException {
        System.out.println("List of students started"); // сообщение о начале документа
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("\nList of students ended"); // сообщение о конце документа
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException { // вхождение в тег
        //level++;
        if (/*level == 2 && */qName.equals("student")) {
            //inStudent = true;

            if (atts != null) {
                int len = atts.getLength();
                for (int i = 0; i < len; i++) {
                    System.out.println("student " + atts.getLocalName(i) + " " + atts.getValue(i)); // выводим атрибуты хобби
                }
            }
        }
        System.out.println("");

        if (/*level == 3 && */qName.equals("subject")) {
            if (atts != null) {
                int len = atts.getLength();
                for (int i = 0; i < len; i++) {
                    System.out.println("subject: " + atts.getLocalName(i) + " " + atts.getValue(i));
                }
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException { // сканирование элемента и выход
        if (/*level == 3 && */inStudent) {
            System.out.println("" + qName + ": " + text); // выводим имя тега и его содержимое
        }
        //if (level == 2) {
            inStudent = false;
        //}
        //level--;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }
}