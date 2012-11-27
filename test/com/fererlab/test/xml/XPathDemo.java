package com.fererlab.test.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * acm 11/25/12 7:48 PM
 */
public class XPathDemo {

    public static void main(String[] args)
            throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException, TransformerException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(("<?xml version=\"1.0\" ?>\n" +
                "<information>\n" +
                "  <person id=\"1\">\n" +
                "  <name>Deep</name>\n" +
                "  <age>34</age>\n" +
                "  <gender>Male</gender>\n" +
                "  </person>\n" +
                " \n" +
                " <person id=\"2\">\n" +
                "  <name>Kumar</name>\n" +
                "  <age>24</age>\n" +
                "  <gender>Male</gender>\n" +
                "  </person>\n" +
                " \n" +
                "  <person id=\"3\">\n" +
                "  <name>Deepali</name>\n" +
                "  <age>19</age>\n" +
                "  <gender>Female</gender>\n" +
                "  </person>\n" +
                "\n" +
                "  <!-- more persons... -->\n" +
                "</information>").getBytes()));

        XPath xpath = XPathFactory.newInstance().newXPath();

        // XPath Query for showing all nodes value
        // "/information/person/name/text()"
        // "/information/person/*/text()"
        // "//person/*/text()"
        XPathExpression expr = xpath.compile("/information/person/name/text()");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }

        System.out.println("doc.toString(): " + doc.toString());

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        //initialize StreamResult with File object to save to file
        StreamResult streamResult = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, streamResult);

        String xmlString = streamResult.getWriter().toString();
        System.out.println(xmlString);

    }
}