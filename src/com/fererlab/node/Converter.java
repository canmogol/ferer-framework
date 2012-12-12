package com.fererlab.node;

import com.fererlab.converter.Conversion;
import com.fererlab.converter.Formatter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * acm 11/27/12 9:58 PM
 */
public class Converter extends Node {

    private List<Conversion> conversions = new ArrayList<>();
    private final Document document;

    public Converter(Document document) {
        this.document = document;
    }

    @Override
    public void execute() throws Exception {

        /*
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.formatter = formatter;
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
         */

        for (Conversion conversion : conversions) {



            // XPath Query
            XPathExpression expr = XPathFactory.instance().compile(conversion.getSourcePath());
            Object result = expr.evaluate(document);

            String sourceValue = "";
            String destinationValue = "";

            // find source name and value
            if (result instanceof List) {
                List sourceList = (List) result;
                Element sourceElement = ((Element) sourceList.get(0));
                sourceValue = sourceElement.getValue();
            } else if (result instanceof String) {
                sourceValue = (String) result;
            }

            // do formatting, get destination value
            destinationValue = conversion.getFormatter().format(sourceValue);



            // XPath Query
            String sourceValueDestination = "";
            XPathExpression exprDestination = XPathFactory.instance().compile(conversion.getDestinationPath());
            Object resultDestination = exprDestination.evaluate(document);
            if (resultDestination instanceof List) {
                List sourceListDestination = (List) resultDestination;
                Element sourceElementDestination = ((Element) sourceListDestination.get(0));
                sourceValueDestination = sourceElementDestination.getValue();
            } else if (resultDestination instanceof String) {
                sourceValueDestination = (String) resultDestination;
            } else {
                System.out.println("resultDestination: " + resultDestination);
            }
            System.out.println("sourceValueDestination: " + sourceValueDestination);


            // set destination value
            document.getRootElement().getChildren().add(
                    getDestinationElement(
                            conversion.getDestinationPath(),
                            destinationValue
                    )
            );

            XMLOutputter outPutter = new XMLOutputter(Format.getPrettyFormat());
            outPutter.output(document, System.out);

        }
    }

    private Element getDestinationElement(String destinationPath, String destinationValue) {
        //      /root/user/username
        //      "root", "user", "username"
        ArrayList<String> destinationPathParts = new ArrayList<>();
        destinationPathParts.addAll(Arrays.asList(destinationPath.split("/")));
        if (destinationPathParts.get(0).isEmpty()) {
            destinationPathParts.remove(0);// this is the empty element, just remove it
        }
        //      "root"
        Element topElement = new Element(destinationPathParts.remove(0)); // there should be at least one node name
        //      "user", "username"
        createElement(topElement, destinationPathParts, destinationValue);
        //      Element(root)->Element(user)->Element(username)
        return topElement;
    }

    private void createElement(Element parent, List<String> destinationPathParts, String destinationValue) {
        try {
            Element child = new Element(destinationPathParts.remove(0));
            parent.addContent(child);
            createElement(child, destinationPathParts, destinationValue);
        } catch (Exception e) {
            // e is safely ignored, it will be thrown at .remove(0),
            // this means the collection is empty and the parent Element node is the last node
            parent.addContent(new Text(destinationValue));
        }
    }

    public void addConversion(String sourcePath, String destinationPath, Formatter formatter, String successMessage, String errorMessage) {
        conversions.add(
                new Conversion(
                        sourcePath, destinationPath, formatter, successMessage, errorMessage
                )
        );
    }
}
