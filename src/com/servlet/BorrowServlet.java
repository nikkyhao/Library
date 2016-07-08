package com.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Administrator;
import com.util.LogInCheck;

public class BorrowServlet extends HttpServlet{
	HttpSession session = null;
	Administrator admin=new Administrator();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");//防止中文乱码问题
		session = request.getSession();
		String index =  request.getParameter("index");
		String cardid = request.getParameter("cardID");
		String bookid="";
		ResultSet bookidSet=admin.searchCanBorrow(Integer.parseInt(index));
		try {
			if(bookidSet.next())
				bookid=bookidSet.getString("bookid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(index);
		System.out.println(cardid);
		System.out.println(bookid);
		
		
		admin.borrow(bookid, Integer.parseInt(cardid));
		request.getRequestDispatcher("/admin/borrow1.jsp").forward(request, response);
	
	}

	
	
}
