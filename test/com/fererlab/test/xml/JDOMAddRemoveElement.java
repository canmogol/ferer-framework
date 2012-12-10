package com.fererlab.test.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * acm 11/25/12 9:19 PM
 */
public class JDOMAddRemoveElement {
    public static void main(String[] args) {
        String xml =
                "<root>" +
                        " <user name=\"Alice\" age=\"21\" dob=\"20-01-1988\"/>" +
                        " <user name=\"Bob\" age=\"23\" dob=\"01-05-1986\"/>" +
                        "</root>";

        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(new ByteArrayInputStream(xml.getBytes()));

            //
            // Adding a new element to the root of the document using the
            // addContent method.
            //
            document.getRootElement().addContent(new Element("people").setAttribute("id", "2"));
            document.getRootElement().addContent(new Element("people").setAttribute("id", "3"));
            document.getRootElement().addContent(new Element("people").setAttribute("id", "4"));

            //
            // Add a new element. By adding a new element to the List of
            // children we can modified the xml document. Using 
            // java.util.List makes the modification of XML document 
            // simple and easy.
            //
            List<Element> children = document.getRootElement().getChildren();
            children.add(new Element("user")
                    .setAttribute("name", "Carol")
                    .setAttribute("age", "25")
                    .setAttribute("dob", "06-03-1984"));

            //
            // Add element to the begining of the xml document.
            //
            children.add(0, new Element("user")
                    .setAttribute("name", "Jimmy")
                    .setAttribute("age", "25")
                    .setAttribute("dob", "16-05-1984"));

            //
            // Remove the 1st element.
            //
            children.remove(1);

            // XPath Query
            XPathExpression expr = XPathFactory.instance().compile("/root/user[@age='23']/@name");
            Object result = expr.evaluate(document);

            List nodes = (List) result;
            for (Object node : nodes) {
                System.out.println(node);
            }


            XMLOutputter outPutter = new XMLOutputter(Format.getPrettyFormat());
            //outPutter.output(document, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}