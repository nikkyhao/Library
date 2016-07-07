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
	Statement statement = null;
	
	public User(int cardID){
		this.cardID = cardID;
		try {
			statement = LibConnection.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ResultSet showMyBook_now() throws SQLException{		//��ѯ��ǰ���ģ�����������ţ��������ڣ�
		String sql = "SELECT bookid,bookname,bo_date,deadline FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID=123)) and return_date is null AND bo_cardID="+cardID;
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
	
	public ResultSet showMyBook_history() throws SQLException{		//��ѯ��ʷ���ģ�����������ţ��������ڣ�
		String sql = "SELECT bookid,bookname,bo_date,return_date FROM borrow,category JOIN book ON category.`index`=book.cateindex WHERE (bo_bookid,bookname) IN (select bookid,bookname from category JOIN book ON category.`index`=book.cateindex WHERE bookid in(select bo_bookid from borrow WHERE bo_cardID=123)) and return_date is not null and bo_cardID = "+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public ResultSet show_my_info() throws SQLException{		//��ѯ��ǰ�û���������Ϣ
		String sql = "select * from user where cardID ="+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	
	public void Update_info(String propertyname,String value) throws SQLException{		//�����û�����
		String sql = "update user set "+propertyname+" = "+value+" where cardID = "+cardID;
		statement.executeUpdate(sql);	
	}
	
	public ResultSet showMyBook_Reminder() throws SQLException{		//�߻����������뻹�����ڻ�������ģ�����������ţ��������ڣ�
		String sql = "select a.bookname,a.`index`,r.bo_date,r.return_date from category a,book b,borrow r  where DATEDIFF(r.return_date,now())<3 and DATEDIFF(r.return_date,now())>0 and  a.`index` = b.cateindex and b.bookid = r.bo_bookid and bo_cardID= "+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	public void Alter_order(int Order_index) throws SQLException{		//���һ��ԤԼ��Ϣ
		String sql = "insert into `order` values("+Order_index+","+cardID+",now(),(select date_add(now(), interval 1 day)))";
		statement.executeUpdate(sql);
	}
	
	public ResultSet showMyorder() throws SQLException{		//��ʾԤԼ��Ϣ
		String sql = "select a.bookname,a.`index`,b.bookingdate,b.deadline from category a,book c,`order` b,user u where a.`index`=c.cateindex and c.bookid=b.ord_bookid and b.ord_cardID=u.cardID and cardId = "+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	

	public static void main(String args[]) throws SQLException{
		
		cardID = 223;
		User user = new User(cardID);
		ResultSet rs = user.showMyorder();
		String bookname = null;
		try {
			while (rs.next()) {
				bookname = rs.getString("bookname");
				//index = rs.getInt(1);		//��ȡ��һ�У���index
				System.out.println(bookname+'\n');
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
