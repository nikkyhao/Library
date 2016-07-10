package com.servlet;
//用于处理用户的搜索请求
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.User;
import com.test.LibConnection;
import com.util.LogInCheck;

public class SubmitCommentServlet extends HttpServlet{
HttpSession session = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user=new User(123);
		request.setCharacterEncoding("utf-8");//防止中文乱码问题
		session = request.getSession();
		String score =  request.getParameter("score");
		String remark = request.getParameter("remark");
		String index = request.getParameter("index");
		System.out.println(index);
		System.out.println(index);
		user.submitComment(Integer.parseInt(index), remark);
	
		
		
		request.getRequestDispatcher("/vip/book.jsp").forward(request, response);
	
	}

	
	
}
