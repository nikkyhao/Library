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

import com.entity.LibSystem;
import com.test.LibConnection;
import com.util.LogInCheck;

public class SearchBorrowServlet extends HttpServlet{
HttpSession session = null;
ResultSet rs = null;//用于存放搜索结果
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");//防止中文乱码问题
		session = request.getSession();
		String content =  request.getParameter("keyboard");
		
		System.out.println(content);
		
	
			rs = LibSystem.queryByIndex2(Integer.parseInt(content));
			try {
		    while (rs.next()) {
					System.out.println(rs.getString("bookname"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		session.setAttribute("SearchBorrow", rs);
		request.getRequestDispatcher("admin/borrow1.jsp").forward(request, response);
	
		
	}

	
	
}
