package com.entity;
/**
 * @author 圆润
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.test.LibConnection;

public class LibSystem {
	Statement statement = LibConnection.getStatement();

	/*
	 * 得到图书数量
	 */
	public int getBookNum() {
		int s = 0;
		String sql = "select count(bookid) from book";
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	/*
	 * 得到各类图书所占比率
	 */
	public BookRatio getAllBookcateRate() {
		return new BookRatio(statement);
	}

	
	/*
	 * 按书名搜索图书，，并计算相同类别的图书有多少本
	 * 
	 */
public ResultSet queryByBookName(String strif) {
		
		
		String sql = "";
		if (strif != "all" && strif != null && strif != "") {
			sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND bookname LIKE +'%" + strif + "%' GROUP BY `index`";
		} else {
			sql = "select * from  book join category on book.cateindex=category.`INDEX`  ";
		}
		
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		return rs;
	}
	/*
	 * 根据作者来查询图书信息，并计算相同类别的图书有多少本
	 */
public ResultSet queryByAuthor(String strif) {
		
		
		String sql = "";
		if (strif != "all" && strif != null && strif != "") {
			sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND author LIKE +'%" + strif + "%' GROUP BY `INDEX`";
		} else {
			sql = "select * from  book join category on book.cateindex=category.`INDEX`  ";
		}
		
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		return rs;
	}

/*
 * 根据出版社来查询图书信息，并计算出相同类别的图书有多少本
 */
public ResultSet queryByPress(String strif) {
	
	
	String sql = "";
	if (strif != "all" && strif != null && strif != "") {
		sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND press LIKE +'%" + strif + "%' GROUP BY `INDEX`";
	} else {
		sql = "select * from  book join category on book.cateindex=category.`INDEX`  ";
	}
	
	ResultSet rs = null;
	try {
		rs = statement.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	return rs;
}

/*
 * 根据语言来查询图书信息，并计算相同类别的图书有多少本
 */
public ResultSet queryByLanguage(String strif) {
	
	
	String sql = "";
	if (strif != "all" && strif != null && strif != "") {
		sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND language LIKE +'%" + strif + "%' GROUP BY `INDEX`";
	} else {
		sql = "select * from  book join category on book.cateindex=category.`INDEX`  ";
	}
	
	ResultSet rs = null;
	try {
		rs = statement.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	return rs;
}
/*
 * 根据类别号来查询图书信息，并计算相同类别的图书有多少本
 */
public ResultSet queryByIndex(String string) {
	
	
	String sql = "";
	
		sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND `INDEX` LIKE +'%" + string + "%' GROUP BY `INDEX`";
	
		
	
	
	ResultSet rs = null;
	try {
		rs = statement.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	return rs;
}
/*
 * 根据bookid来查询图书信息，并计算相同类别的图书有多少本
 */
public ResultSet queryByBookId(String strif) {
	
	
	String sql = "";
	if (strif != "all" && strif != null && strif != "") {
		sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate FROM category,book WHERE cateindex=`index` ";
	} else {
		sql = "select * from  book join category on book.cateindex=category.`INDEX`  ";
	}
	
	ResultSet rs = null;
	try {
		rs = statement.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	return rs;
}//测试方法少count（bookid）

/*
 * 根据图书类型来查询图书信息，并计算相同类别的图书有多少本
 */
public ResultSet queryByBookType(String strif) {
	
	
	String sql = "";
	if (strif != "all" && strif != null && strif != "") {
		sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND booktype LIKE +'%" + strif + "%' GROUP BY `INDEX`";
	} else {
		sql = "select * from  book join category on book.cateindex=category.`INDEX`  ";
	}
	
	ResultSet rs = null;
	try {
		rs = statement.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	return rs;
}
/*
 * 根据印刷日期来查询图书信息，并计算相同类别的图书有多少本
 */
public ResultSet queryByPressDate(String strif) {
	
	
	String sql = "";
	if (strif != "all" && strif != null && strif != "") {
		sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND pressdate LIKE +'%" + strif + "%' GROUP BY `INDEX`";
	} else {
		sql = "select * from  book join category on book.cateindex=category.`INDEX`  ";
	}
	
	ResultSet rs = null;
	try {
		rs = statement.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	return rs;
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LibSystem a = new LibSystem();
		System.out.println(a.getAllBookcateRate().getComicrate());
		System.out.println(a.getAllBookcateRate().getTestbookrate());
		System.out.println(a.getAllBookcateRate().getCoachbookrate());
		System.out.println(a.getAllBookcateRate().getNovelrate());
		ResultSet bookquery=a.queryByBookType("comic");
		 try{
			 while(bookquery.next()){
				 String index=bookquery.getString("index");
				 String booktype=bookquery.getString("booktype");
				 String bookname=bookquery.getString("bookname");
				 String author=bookquery.getString("author");
				 String press=bookquery.getString("press");
				 String languege=bookquery.getString("language");
				 String pressdate=bookquery.getString("pressdate");
				 String howmanybookid=bookquery.getString("COUNT(bookid)");
				 System.out.println(index+"   "+booktype+"   "+bookname+"   "+author+"   "+press+"   "+howmanybookid+"   "+languege+""
				 		+ "   "+pressdate);
				 Date dateU = new Date();
		           java.sql.Date date = new java.sql.Date(dateU.getTime());
		           String date_str = String.valueOf(date);
	            System.out.println(date_str);
			 }
		 }
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		
		
	}
}