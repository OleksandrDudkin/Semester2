package Pr_1_1_XML.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MyContentHandler extends org.xml.sax.helpers.DefaultHandler{ //класс наследует DefaultHandler

    private int level = 0; //уровень вложенности xml-тега
    private boolean inHobby = false;
    private StringBuffer text = new StringBuffer();

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Document started"); // сообщение о начале документа
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("\nDocument ended"); // сообщение о конце документа
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException { // вхождение в тег
        level++;
        if (level == 2 && qName.equals("hobby")) {
            inHobby = true;

            if (atts != null) {
                int len = atts.getLength();
                for (int i = 0; i < len; i++) {
                    System.out.println("\nHobby " + atts.getLocalName(i) + " " + atts.getValue(i)); // выводим атрибуты хобби
                }
            }
        }
        text.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException { // сканирование элемента и выход
        if (level == 3 && inHobby) {
            System.out.println("" + qName + ": " + text); // выводим имя тега и его содержимое
        }
        if (level == 2) {
            inHobby = false;
        }
        level--;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }
}