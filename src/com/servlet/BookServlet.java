package com.servlet;
//用于处理每本书的跳转请求
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Administrator;
import com.entity.User;
import com.test.LibConnection;
import com.util.LogInCheck;

public class BookServlet extends HttpServlet {
	HttpSession session = null;

	//注意这里是get请求
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");// 防止中文乱码问题
		session = request.getSession();
		String index = request.getParameter("index");
		System.out.println(index);
		session.setAttribute("bookindex", Integer.parseInt(index));
		request.getRequestDispatcher("book.jsp").forward(request, response);
	}


	
}
