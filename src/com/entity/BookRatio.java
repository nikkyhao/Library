package com.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.test.LibConnection;

public class BookRatio {
	double comicrate;
	double testbookrate;
	double coachbookrate;
	double novelrate;
	Statement statement = null;
	//科学 人文 图册 语言
	//science humanity picture language
	BookRatio(Statement s){
		this.statement = s;
		try {
			comicrate=getBookcateRate("comic");
			testbookrate=getBookcateRate("testbook");
			coachbookrate=getBookcateRate("coachbook");
			novelrate=getBookcateRate("novel");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private double getBookcateRate(String bcategory) throws SQLException{
		String sql1="select count(*) from category where booktype='"+bcategory+"'";
		String sql = "select count(bookid) from book";
		double s=0;
		double h=0;
		double rate;
		ResultSet sd=null;
		ResultSet rs=null;
		 try {
		     rs = statement.executeQuery(sql1);	 
		     while(rs.next()){
			 s=rs.getInt(1);
//			System.out.println(s);
			
		 }
	     } catch (SQLException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
	       }
		  try {
			     sd = statement.executeQuery(sql);	 
			     while(sd.next()){
				 h=sd.getInt(1);
//				 System.out.println(h);
				 
				
			 }
		     } catch (SQLException e) {
			     // TODO Auto-generated catch block
			     e.printStackTrace();
		       }
		   
		rate=s/h;
		return rate;
	}
	
	public double getComicrate() {
		return comicrate;
	}

	public double getTestbookrate() {
		return testbookrate;
	}

	public double getCoachbookrate() {
		return coachbookrate;
	}

	public double getNovelrate() {
		return novelrate;
	}

	
	public BookRatio(){
	
	}
	
	

}
