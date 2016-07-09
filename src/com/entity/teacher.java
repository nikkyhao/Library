package com.entity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class teacher extends User {
	static int cardID;

	public teacher(int cardID) {
		super(cardID);
		// TODO �Զ����ɵĹ��캯�����
	}
	
	public void Alter_score(int comment_index,int comment_stu,String a) throws SQLException{		
		Statement statement = connection.createStatement();
		String sql = "update comment set comment_teacher = "+cardID+",score = "+a+" where com_index = "+comment_index+" and comment_stu = "+comment_stu;
		statement.executeUpdate(sql);
	}
	
	public ResultSet showscore() throws SQLException{
		Statement statement = connection.createStatement();
		String sql = "select score from comment where comment_teacher = "+cardID;
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	
	
	
	public static void main(String args[]) {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");// ����Mysql��������
//			
//			con = DriverManager.getConnection(
//					"jdbc:mysql://127.0.0.1:3306/test", "root", "wanner1597");// ������������	
//			System.out.println("hello");
//		} catch (Exception e) {
//			System.out.println("���ݿ�����ʧ��" + e.getMessage());
//		}
//		
//		/////�������
//		cardID = 321;
//		String score = null;
//		ResultSet rs = teacher.showscore();
//		try {
//			while (rs.next()) {
//				score = rs.getString("score");
//				//index = rs.getInt(1);		//��ȡ��һ�У���index
//				System.out.println(score+'\n');
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}

}