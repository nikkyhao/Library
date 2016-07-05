package com.entity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class teacher extends User {
	static int cardID;

	public teacher(int cardID) {
		super(cardID);
		// TODO �Զ���ɵĹ��캯����
	}
	
	public static void Alter_score(int comment_index,int comment_stu,String a) throws SQLException{		//�����۴��
		String sql = "update comment set comment_teacher = "+cardID+",score = "+a+" where com_index = "+comment_index+" and comment_stu = "+comment_stu;
		java.sql.Statement statement =con.createStatement();
		statement.executeUpdate(sql);
	}
	
	public static ResultSet showscore() throws SQLException{							//�鿴�Լ��Ĵ��
		String sql = "select score from comment where comment_teacher = "+cardID;
		java.sql.Statement statement =con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		return rs;
	}
	
	
	
	
	public static void main(String args[]) throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");// ����Mysql�����
			
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/test", "root", "wanner1597");// �����������	
			System.out.println("hello");
		} catch (Exception e) {
			System.out.println("��ݿ�����ʧ��" + e.getMessage());
		}
		
		/////�������
		cardID = 321;
		String score = null;
		ResultSet rs = teacher.showscore();
		try {
			while (rs.next()) {
				score = rs.getString("score");
				//index = rs.getInt(1);		//��ȡ��һ�У���index
				System.out.println(score+'\n');
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
