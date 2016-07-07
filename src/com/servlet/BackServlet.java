package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Administrator;
import com.util.LogInCheck;

public class BackServlet extends HttpServlet{
	HttpSession session = null;
	Administrator admin=new Administrator();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");//防止中文乱码问题
		session = request.getSession();
		String bookid =  request.getParameter("bookid");
		String cardid = request.getParameter("cardid");
		System.out.println(bookid);
		System.out.println(cardid);
		
		admin.back(bookid, Integer.parseInt(cardid));
	
	
	}

	
	
}
