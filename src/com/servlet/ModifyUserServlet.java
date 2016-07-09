package com.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.User;


public class ModifyUserServlet extends HttpServlet{
	HttpSession session = null;
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user=new User(123);
		
		request.setCharacterEncoding("utf-8");//防止中文乱码问题
		session = request.getSession();
		
	//	String cardID = request.getParameter("cardID");
		String name=  request.getParameter("name");
		String sex =  request.getParameter("sex");
		String tel =  request.getParameter("tel");
		String stu_ID =  request.getParameter("stu_ID");
		String pwd =  request.getParameter("pwd");
		
		System.out.println(name);
		
		if(name!=""&&sex!=""&&tel!=""&&stu_ID!=""){
			user.updateUser(name, sex, Integer.parseInt(stu_ID), tel);
		}
//		if(name!=null){
//			user.updateUsername(name);
//		}
//		if(sex!=null){
//			user.updateUserSex(sex);
//		}
//		if(tel!=null){
//			user.updateUserTel(tel);
//		}
//		if(stu_ID!=null){
//			user.updateUserStuID(Integer.parseInt(stu_ID));
//		}
		if(pwd!=""){
			user.updateUserPwd(pwd);
		}

		request.getRequestDispatcher("/vip/information.jsp").forward(request, response);
	
	}

	
	
}