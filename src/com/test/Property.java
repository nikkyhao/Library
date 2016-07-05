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
	
	static{
		property= new Properties();
		try {
			inStream = new FileInputStream("library.properties");
//			outputFile =  new FileOutputStream("library.properties");
			System.out.println("property 已初始化");
		} catch (FileNotFoundException e) {
			System.out.println("找不到配置文件");
			e.printStackTrace();
		} catch (IOException e) {
			
		}
	}
	
	
	private Property(){
		
	}
	 
	 private static void setProperty(String key,String value){
			property.setProperty(key, value);
			System.out.println(key+"="+value+" saved");
			try {
				property.store(outputFile, "libtest");
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
//		 Property.setProperty("number", "23244");
//		 Property.setProperty("name2", "xuhao");
		 System.out.println(Property.getProperty("number"));
		 System.out.println(Property.getProperty("name"));
		
		
	    }  
}
