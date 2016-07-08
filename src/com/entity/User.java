package com.entity;
/**
 * @author ��ͨ
 */

import java.sql.*;
import java.util.Calendar;

import com.test.LibConnection;

import java.sql.*;

public class User {
	protected static int cardID;
	static Connection connection = null;
	public User(int cardID){
		this.cardID = cardID;
		connection = LibConnection.getConnection();
	}
	public double fine(int cardID){
		//超期天数
		Statement sta = null;
		
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		double fine=0.1;
		Date date = new Date(System.currentTimeMillis());//系统当前日期
		String sql = "SELECT deadline,return_date FROM borrow,user WHERE bo_cardID=cardID and return_date > deadline and cardID="+cardID;
		String sql2="select deadline from borrow,user where (deadline<'"+date+"' and return_date is null) and cardID="+cardID;
		ResultSet rs = null;
		ResultSet rs1 = null;
		long betweenDays=0;
		try {
			rs = sta.executeQuery(sql);		
			while(rs.next()){
				Date deadline=rs.getDate("deadline");
				Date return_date=rs.getDate("return_date");
				long beginTime = deadline.getTime(); 
				long endTime = return_date.getTime(); 
				betweenDays = (long)((endTime - beginTime) / (1000 * 60 * 60 *24) + 0.5); 
					
			}
			rs1=sta.executeQuery(sql2);
			while(rs1.next()){
				Date deadline2=rs1.getDate("deadline");
				long dead=deadline2.getTime();
				long today=date.getTime();
				betweenDays = (long)((today - dead) / (1000 * 60 * 60 *24) + 0.5); 
				System.out.println(betweenDays);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return betweenDays;
	}
	public static ResultSet Cui(int cardID){
		//催还单
		Statement sta = null;
		
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		double fine=0.1;
		Date date = new Date(System.currentTimeMillis());//系统当前日期
		String sql = "SELECT name,bo_cardID,bo_bookid,deadline,bo_date FROM borrow,user,book WHERE bo_cardID=cardID and bo_bookid=bookid and bo_bookid=bookid and 0<deadline-'"+date+"'<5 and return_date is null and cardID="+cardID;
		
		ResultSet rs = null;
		ResultSet rs1 = null;
		long betweenDays=0;
		try {
			rs = sta.executeQuery(sql);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	} 
	public ResultSet searchOverdateByUser(int cardID) {
		// 查询某个人的违章记录
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date date = new Date(System.currentTimeMillis());//系统当前日期
		String sql = "SELECT bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID and (return_date > deadline or (deadline<'"+date+"' and return_date is null))"+" and cardID="
				+ cardID;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet showMyBook_now(int cardID) throws SQLException{	
		Statement statement = connection.createStatement();
		String sql = "SELECT bookid,bookname,bo_date,deadline FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID="+cardID+")) and return_date is null AND bo_cardID="+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	private boolean Isoverdate(ResultSet a) throws SQLException{		//�Ƿ���
		boolean m;
		Timestamp c = a.getTimestamp("deadline");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(ts);			//��ǰʱ��
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(c);			//��ֹ����
		return cal1.after(cal2);			//	��ǰʱ�䳬����ֹ���ڣ�Ϊ����
		
	}
	
	public ResultSet showMyBook_history(int cardID) throws SQLException{	
		Statement statement = connection.createStatement();
		String sql = "SELECT bookid,bookname,bo_date,return_date FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID=123)) and return_date is not null and bo_cardID = "+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet show_my_info() throws SQLException{	
		Statement statement = connection.createStatement();
		String sql = "select * from user where cardID ="+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	
	public void Update_info(String propertyname,String value) throws SQLException{	
		Statement statement = connection.createStatement();
		String sql = "update user set "+propertyname+" = "+value+" where cardID = "+cardID;
		statement.executeUpdate(sql);	
	}
	
	public ResultSet showMyBook_Reminder() throws SQLException{	
		Statement statement = connection.createStatement();
		String sql = "select a.bookname,a.`index`,r.bo_date,r.return_date from category a,book b,borrow r  where DATEDIFF(r.return_date,now())<3 and DATEDIFF(r.return_date,now())>0 and  a.`index` = b.cateindex and b.bookid = r.bo_bookid and bo_cardID= "+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public void Alter_order(int Order_index) throws SQLException{
		Statement statement = connection.createStatement();
		String sql = "insert into `order` values("+Order_index+","+cardID+",now(),(select date_add(now(), interval 1 day)))";
		statement.executeUpdate(sql);
	}
	
	public ResultSet showMyorder(int cardID) throws SQLException{	
		Statement statement = connection.createStatement();
		String sql = "select a.bookname,a.`index`,b.bookingdate,b.deadline from category a,book c,`order` b,user u where a.`index`=c.cateindex and c.bookid=b.ord_bookid and b.ord_cardID=u.cardID and cardId = "+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	

	public static void main(String args[]) throws SQLException{
		
//		cardID = 223;
//		User user = new User(cardID);
//		String bookname = null;
//		try {
//			while (rs.next()) {
//				bookname = rs.getString("bookname");
//				//index = rs.getInt(1);		//��ȡ��һ�У���index
//				System.out.println(bookname+'\n');
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}
}
