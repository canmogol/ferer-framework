package com.fererlab.util;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class XMLUtil {
	
	public static Document getDocumentObj(String file){
		
		SAXBuilder builder = new SAXBuilder();
		
		Document document = null;
		try {
			document = builder.build(file);
		} catch (JDOMException e) {
			// TODO : log exception
		} catch (IOException e) {
			// TODO : log exception
		}
		
		return document;
	} 	
}
