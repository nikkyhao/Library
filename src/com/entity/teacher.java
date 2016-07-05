package com.entity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class teacher extends User {
	static int cardID;

	public teacher(int cardID) {
		super(cardID);
		// TODO 自动生成的构造函数存根
	}
	
	public static void Alter_score(int comment_index,int comment_stu,String a) throws SQLException{		//给评论打分
		String sql = "update comment set comment_teacher = "+cardID+",score = "+a+" where com_index = "+comment_index+" and comment_stu = "+comment_stu;
		java.sql.Statement statement =con.createStatement();
		statement.executeUpdate(sql);
	}
	
	public static ResultSet showscore() throws SQLException{							//查看自己的打分
		String sql = "select score from comment where comment_teacher = "+cardID;
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
		cardID = 321;
		String score = null;
		ResultSet rs = teacher.showscore();
		try {
			while (rs.next()) {
				score = rs.getString("score");
				//index = rs.getInt(1);		//获取第一列，即index
				System.out.println(score+'\n');
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
