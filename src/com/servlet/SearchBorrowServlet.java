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

import com.entity.Administrator;
import com.entity.LibSystem;
import com.test.LibConnection;
import com.util.LogInCheck;

public class SearchBorrowServlet extends HttpServlet{
HttpSession session = null;
ResultSet borrow = null;//用于存放搜索结果
ResultSet back=null;
Administrator admin=new Administrator();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");//防止中文乱码问题
		session = request.getSession();
		String index =  request.getParameter("keyboard");
		String bookid= request.getParameter("bookid");
		System.out.println(index);
		System.out.println(bookid);
		if(index!=null){
			borrow = LibSystem.queryByIndex2(Integer.parseInt(index));
			try {
		    while (borrow.next()) {
					System.out.println(borrow.getString("bookname"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			session.setAttribute("SearchBorrow", borrow);
			request.getRequestDispatcher("admin/borrow1.jsp").forward(request, response);
	
		}
		else if(bookid!=null){
			back=admin.searchCurBor(bookid);
			try {
			    while (back.next()) {
						System.out.println(back.getString("bookname"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				session.setAttribute("SearchBack", back);
				request.getRequestDispatcher("admin/back2.jsp").forward(request, response);
		}
			
		
	}

	
	
}
