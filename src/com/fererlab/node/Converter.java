package com.fererlab.node;

import com.fererlab.converter.Conversion;
import com.fererlab.converter.Formatter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.util.ArrayList;
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

            String sourceName = "";
            String sourceValue = "";
            String destinationName = "";
            String destinationValue = "";

            // find source name and value
            if (result instanceof List) {
                List sourceList = (List) result;
                Element sourceElement = ((Element) sourceList.get(0));
                sourceName = sourceElement.getName();
                sourceValue = sourceElement.getValue();
            } else if (result instanceof String) {
                sourceName = conversion.getSourcePath();
                sourceValue = (String) result;
            }

            // do formatting, get destination value
            destinationValue = conversion.getFormatter().format(sourceValue);

            // set destination value
            List<Element> children = document.getRootElement().getChildren();
            children.add(new Element("user")
                    .setAttribute("name", "Carol")
                    .setAttribute("age", "25")
                    .setAttribute("dob", "06-03-1984"));


            System.out.println("sourceName: '" + sourceName + "' sourceValue: '" + sourceValue + "'");

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
