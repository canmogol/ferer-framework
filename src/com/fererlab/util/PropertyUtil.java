package com.fererlab.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertyUtil {

	private static PropertyUtil propertyUtil;
	private Properties properties;
	private String propertyConfFile;
	
	private PropertyUtil(String propertyFile){
		this.propertyConfFile = propertyFile;
	}
	
	public static synchronized PropertyUtil createInstance(String propertyFile){
		
		if(propertyUtil == null){
			propertyUtil = new PropertyUtil(propertyFile);
			propertyUtil.loadProperties();
		}
		
		return propertyUtil;
		
	}
	
	private void loadProperties(){
		
		this.properties = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(propertyConfFile);
			try {
				properties.loadFromXML(fis);
			} catch (InvalidPropertiesFormatException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static String getProperty(String key){
		
		String property = null;
		if(propertyUtil.getProperties().containsKey(key)){
			property = propertyUtil.getProperties().getProperty(key);
		}
		return property;
		
	}
	
	public static void setProperty(String key, String value){
		propertyUtil.getProperties().setProperty(key, value);
	}
	
	private Properties getProperties() {
		return properties;
	}
	
}
