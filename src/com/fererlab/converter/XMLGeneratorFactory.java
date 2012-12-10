package com.fererlab.converter;

public class XMLGeneratorFactory {

	private XMLGenerator xmlGenerator;
	
	public XMLGeneratorFactory(){
	}

	public XMLGenerator getXmlGenerator(){
		
		if(xmlGenerator == null){
			xmlGenerator = new SimpleXMLGenerator();
		}
		
		return xmlGenerator;
	}
	
}
