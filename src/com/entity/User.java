package com.entity;
/**
 * @author 汪通
 */

import java.sql.*;
import java.util.Calendar;

import com.mysql.jdbc.Statement;


public class User {
	static Connection con = null;
	protected static int cardID;
	
	public User(int cardID){
		this.cardID = cardID;
	}
	
	public static ResultSet showMyBook_now() throws SQLException{		//查询当前借阅（书名，索书号，两个日期）
		String sql = "SELECT bookid,bookname,bo_date,deadline FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID=123)) and return_date is null AND bo_cardID="+cardID;
		java.sql.Statement statement =con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	private boolean Isoverdate(ResultSet a) throws SQLException{		//是否超期
		boolean m;
		Timestamp c = a.getTimestamp("deadline");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(ts);			//当前时间
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(c);			//截止日期
		return cal1.after(cal2);			//	当前时间超过截止日期，为超期
		
	}
	
	public static ResultSet showMyBook_history() throws SQLException{		//查询历史借阅（书名，索书号，两个日期）
		String sql = "SELECT bookid,bookname,bo_date,return_date FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID=123)) and return_date is not null and bo_cardID = "+cardID;
		java.sql.Statement statement =con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public static ResultSet show_my_info() throws SQLException{		//查询当前用户的所有信息
		String sql = "select * from user where cardID ="+cardID;
		java.sql.Statement statement =con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	
	public static void Update_info(String propertyname,String value) throws SQLException{		//更改用户数据
		String sql = "update user set "+propertyname+" = "+value+" where cardID = "+cardID;
		java.sql.Statement statement =con.createStatement();
		statement.executeUpdate(sql);	
	}
	
	public static ResultSet showMyBook_Reminder() throws SQLException{		//催还单，所有离还书日期还差三天的（书名，索书号，两个日期）
		String sql = "select a.bookname,a.`index`,r.bo_date,r.return_date from category a,book b,borrow r  where DATEDIFF(r.return_date,now())<3 and DATEDIFF(r.return_date,now())>0 and  a.`index` = b.cateindex and b.bookid = r.bo_bookid and bo_cardID= "+cardID;
		java.sql.Statement statement =con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public static void Alter_order(int Order_index) throws SQLException{		//添加一个预约信息
		String sql = "insert into `order` values("+Order_index+","+cardID+",now(),(select date_add(now(), interval 1 day)))";
		java.sql.Statement statement =con.createStatement();
		statement.executeUpdate(sql);
	}
	
	public static ResultSet showMyorder() throws SQLException{		//显示预约信息
		String sql = "select a.bookname,a.`index`,b.bookingdate,b.deadline from category a,book c,`order` b,user u where a.`index`=c.cateindex and c.bookid=b.ord_bookid and b.ord_cardID=u.cardID and cardId = "+cardID;
		java.sql.Statement statement =con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	

	public static void main(String args[]) throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
			
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/test", "root", "wanner1597");// 创建数据连接	
			System.out.println("hello");
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		
		/////测试输出
		cardID = 223;
		ResultSet rs = User.showMyorder();
		String bookname = null;
		try {
			while (rs.next()) {
				bookname = rs.getString("bookname");
				//index = rs.getInt(1);		//获取第一列，即index
				System.out.println(bookname+'\n');
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
