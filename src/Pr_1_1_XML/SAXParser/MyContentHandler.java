package Pr_1_1_XML.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class MyContentHandler extends org.xml.sax.helpers.DefaultHandler{
//public class MyContentHandler implements ContentHandler {

    private int level = 0;
    private boolean inHobby = false;
    private StringBuffer text = new StringBuffer();

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Document started");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("\nDocument ended");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        level++;
        if (level == 2 && qName.equals("hobby")) {
            inHobby = true;

            if (atts != null) {
                int len = atts.getLength();
                for (int i = 0; i < len; i++) {
                    System.out.println("\nHobby " + atts.getLocalName(i) + " " + atts.getValue(i));
                }
            }
        }
        text.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (level == 3 && inHobby) {
            System.out.println("" + qName + ": " + text);
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