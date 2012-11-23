package com.fererlab.converter;


public interface XMLGenerator {

	String XML_ROOT = "data";
	
	public void toXML();
	
	public String getXmlString();
	
}
