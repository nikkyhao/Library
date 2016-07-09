package com.entity;
/**
 * @author Բ��
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.test.LibConnection;

public class LibSystem {
	static Connection connection = null;
static{
			connection = LibConnection.getConnection();
}
	
	public int getBookNum() {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	 * �õ�����ͼ����ռ����
	 */
	public BookRatio getAllBookcateRate() {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BookRatio(statement);
	}

	
	public static int searchBorrowNum(int index) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 一共有几本index类的书
		int num = 0;
		String sql = "SELECT COUNT(bookid) as num FROM book,category,borrow WHERE bookid=bo_bookid AND cateindex=`index` AND return_date IS NULL AND `index`=" + index;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	/*
	 * ����������ͼ�飬����������ͬ����ͼ���ж��ٱ�
	 * 
	 */
	public static int searchNum(int index) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 一共you几本index类的书
		int num = 0;
		String sql = "select count(bookid) as num from category,book where `index`=cateindex and `index`=" + index;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
public static ResultSet queryByBookName(String strif) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		
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
	 * �����������ѯͼ����Ϣ����������ͬ����ͼ���ж��ٱ�
	 */
public static ResultSet queryByAuthor(String strif) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
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
 * ��ݳ���������ѯͼ����Ϣ�����������ͬ����ͼ���ж��ٱ�
 */
public static ResultSet queryByPress(String strif) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
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
 * �����������ѯͼ����Ϣ����������ͬ����ͼ���ж��ٱ�
 */
public static ResultSet queryByLanguage(String strif) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
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
 * �����������ѯͼ����Ϣ����������ͬ����ͼ���ж��ٱ�
 */
public static ResultSet queryByIndex(String string) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
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
public static ResultSet queryByIndex2(int index) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	String sql = "SELECT `index`,booktype,bookname,author,press,language,pressdate,COUNT(bookid) FROM category,book WHERE cateindex=`index` AND `INDEX` =" + index + " GROUP BY `INDEX`";
	ResultSet rs = null;
	try {
		rs = statement.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return rs;
}

public static ResultSet queryByBookId(String strif) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
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
}//���Է�����count��bookid��

/*
 * ���ͼ����������ѯͼ����Ϣ����������ͬ����ͼ���ж��ٱ�
 */
public static ResultSet queryByBookType(String strif) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
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
 * ���ӡˢ��������ѯͼ����Ϣ����������ͬ����ͼ���ж��ٱ�
 */
public static ResultSet queryByPressDate(String strif) {
	Statement statement = null;
	try {
		statement = connection.createStatement();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
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
//		System.out.println(a.getAllBookcateRate().getComicrate());
//		System.out.println(a.getAllBookcateRate().getTestbookrate());
//		System.out.println(a.getAllBookcateRate().getCoachbookrate());
//		System.out.println(a.getAllBookcateRate().getNovelrate());
		ResultSet bookquery=queryByBookName("java");
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