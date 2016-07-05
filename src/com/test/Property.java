package com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Property {

	static Properties property ;
	static FileInputStream inStream = null;
	static FileOutputStream outputFile = null;  
	
	
	
	public Property(){
		property= new Properties();
		try {
			inStream = new FileInputStream("library.properties");
			outputFile =  new FileOutputStream("library.properties");
			
		} catch (FileNotFoundException e) {
			System.out.println("找不到配置文件");
			e.printStackTrace();
		} 
	}
	
	 
	 
	 public static void setProperty(String key,String value){
		 try {
			property.setProperty(key, value);
			property.store(outputFile, "libtest");
			System.out.println("property"+key+"="+value+" saved");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		 
	 }
	
	 public static String getProperty(String key){
		 try {
			property.load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(property.get(key)!= null){
			 
			 return property.getProperty(key);
		 }
		 else{
			return "ERROR, 不存在的属性";
		 }
		 
	 }
	 
	 public static void main(String[] args) {  
		 Property property = new Property();
		 property.setProperty("number", "23244");
		System.out.println(property.getProperty("number"));
	    }  
}
