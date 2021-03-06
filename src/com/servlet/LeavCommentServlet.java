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

import com.test.LibConnection;
import com.util.LogInCheck;

public class LeavCommentServlet extends HttpServlet{
HttpSession session = null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");//防止中文乱码问题
		session = request.getSession();
		String username =  request.getParameter("username");
		String password = request.getParameter("password");
		String identity = request.getParameter("identity");
		String string = LogInCheck.isLogin(identity,username, password);
		session.setAttribute("loginresult", string);
		System.out.println(string);
		
		
		request.getRequestDispatcher("LogResult.jsp").forward(request, response);
	
	}

	
	
}
