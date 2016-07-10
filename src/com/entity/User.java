package com.entity;
/**
 * @author ��ͨ
 */

import java.sql.*;
import java.util.Calendar;

import com.test.LibConnection;

import java.sql.*;

public class User {
	public int cardID;
	static Connection connection = null;
	public User(int cardID){
		this.cardID = cardID;
		connection = LibConnection.getConnection();
	}
	public double fine(){
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
	public void submitComment (int index,String remark) {
		// 提交评论
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date date = new Date(System.currentTimeMillis());
		String sql = " INSERT INTO comment(com_index,comment_stu,remark,com_date) VALUES( " +  index + "," + cardID + ",'" + remark + "','" + date + "' )";
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ResultSet getUserCommentAverage() {
		// 通过id查询评论总数以及平均分
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT avg(score) as avg_score,count(comment_stu) as num FROM comment WHERE comment_stu=" + cardID;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void updateUser(String name, String sex, int stuID,String tel) {
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "update user set NAME='" + name + "',sex='" + sex + "',studentID=" + stuID + ",telephone=" + tel + " where cardID=" + cardID;
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public ResultSet getUserComment() {
		// 通过id查询评论
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT name,bookname,remark,score FROM user,comment,category WHERE cardID=comment_stu and com_index=`INDEX` and cardID=" + cardID;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void updateUserPwd(String pwd) {
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "update user set password=" + pwd +  "s where cardID=" + cardID;
		try {
			System.out.println(pwd);
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public ResultSet Cui(){
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
	public ResultSet searchOverdateByUser() {
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
	public ResultSet showMyBook_now() {	
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "SELECT bookid,bookname,bo_date,deadline FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID="+cardID+")) and return_date is null AND bo_cardID="+cardID;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	private boolean Isoverdate(ResultSet a) {		//�Ƿ���
		boolean m;
		Timestamp c = null;
		try {
			c = a.getTimestamp("deadline");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(ts);			//��ǰʱ��
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(c);			//��ֹ����
		return cal1.after(cal2);			//	��ǰʱ�䳬����ֹ���ڣ�Ϊ����
		
	}
	
	public ResultSet showMyBook_history() {	
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "SELECT bookid,bookname,bo_date,return_date FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID=123)) and return_date is not null and bo_cardID = "+cardID;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet show_my_info() {	
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "select * from user where cardID ="+cardID;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void Update_info(String propertyname,String value){	
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "update user set "+propertyname+" = "+value+" where cardID = "+cardID;
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public ResultSet showMyBook_Reminder() {	
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "select a.bookname,a.`index`,r.bo_date,r.return_date from category a,book b,borrow r  where DATEDIFF(r.return_date,now())<3 and DATEDIFF(r.return_date,now())>0 and  a.`index` = b.cateindex and b.bookid = r.bo_bookid and bo_cardID= "+cardID;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void Alter_order(int Order_index) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "insert into `order` values("+Order_index+","+cardID+",now(),(select date_add(now(), interval 1 day)))";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet showMyorder() {	
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "select a.bookname,a.`index`,b.bookingdate,b.deadline from category a,book c,`order` b,user u where a.`index`=c.cateindex and c.bookid=b.ord_bookid and b.ord_cardID=u.cardID and cardId = "+cardID;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static void main(String args[]){
		
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
