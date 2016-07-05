package com.entity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class student extends User {
	static int cardID;
	public student(int cardID) {
		super(cardID);
		// TODO �Զ���ɵĹ��캯����
	}

	public static void comment(int comment_index,String b) throws SQLException{			//�������,����Ϊ�������ź�����
		String sql = "insert into comment values("+comment_index+","+cardID+",null,"+b+",now(),null)";
		java.sql.Statement statement =con.createStatement();
		statement.executeUpdate(sql);	
	}
	
	public static ResultSet ShowMyComment() throws SQLException{ 								//�鿴�Լ�����������
		String sql = "Select * from comment where comment_stu ="+cardID;
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
		cardID = 223;
		ResultSet rs = student.ShowMyComment();
		String remark = null;
		try {
			while (rs.next()) {
				remark = rs.getString("remark");
				//index = rs.getInt(1);		//��ȡ��һ�У���index
				System.out.println(remark+'\n');
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
