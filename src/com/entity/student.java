package com.entity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class student extends User {
	static int cardID;
	public student(int cardID) {
		super(cardID);
		// TODO 自动生成的构造函数存根
	}

	public static void comment(int comment_index,String b) throws SQLException{			//添加评论,参数为书的索书号和评论
		String sql = "insert into comment values("+comment_index+","+cardID+",null,"+b+",now(),null)";
		java.sql.Statement statement =con.createStatement();
		statement.executeUpdate(sql);	
	}
	
	public static ResultSet ShowMyComment() throws SQLException{ 								//查看自己的所有评论
		String sql = "Select * from comment where comment_stu ="+cardID;
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
		ResultSet rs = student.ShowMyComment();
		String remark = null;
		try {
			while (rs.next()) {
				remark = rs.getString("remark");
				//index = rs.getInt(1);		//获取第一列，即index
				System.out.println(remark+'\n');
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
