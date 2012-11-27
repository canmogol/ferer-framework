package com.fererlab.converter;

import com.thoughtworks.xstream.XStream;

import java.util.Map;


public class SimpleXMLGenerator extends AbstractXMLGenerator implements XMLGenerator{

	public SimpleXMLGenerator() {
		super();
	}

	public void toXML() {
		
		XStream xstream = getXStream();

		StringBuffer buffer = new StringBuffer();

        for(Map.Entry<String, Class> entry : super.getAliasMap().entrySet()){
            xstream.alias(entry.getKey(), entry.getValue());
        }

		for(Map.Entry<String, Object> entry : super.getDataMap().entrySet()){
            xstream.alias(entry.getKey(), entry.getValue().getClass());
			buffer.append(xstream.toXML(entry.getValue()));
		}
		
		setXmlString(buffer.toString());
	}

}
