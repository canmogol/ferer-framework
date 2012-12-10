package com.fererlab.converter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public abstract class AbstractXMLGenerator {

	protected XStream xstream;

	private Map<String, Object> dataMap;
	private Map<String, Class> aliasMap;

	private String xmlString = "";

	public AbstractXMLGenerator() {
		dataMap = new HashMap<String, Object>();
	}

	protected XStream getXStream() {

		if (xstream == null) {

			xstream = new XStream(new XppDriver() {
				public HierarchicalStreamWriter createWriter(Writer out) {
					return new PrettyPrintWriter(out) {
						protected void writeText(QuickWriter writer, String text) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						}
					};
				}
			});

		}

		return xstream;
	}

	public abstract void toXML();

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

    public Map<String, Class> getAliasMap() {
        return aliasMap;
    }
}
