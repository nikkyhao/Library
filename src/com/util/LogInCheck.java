 package com.util;
import java.sql.*;

import com.test.LibConnection;

public class LogInCheck {
    static Statement  stat = LibConnection.getStatement();
    
    public static String isLogin(String identity,String cardID,String password){
	if(identity.equals("user")){
	    ResultSet result = null;
	    try {
		result = stat.executeQuery("select * from user where cardID ='"+cardID+"' and password ='"+password+"'");
		System.out.println("cardID:"+cardID+"password"+password);
	    if(result.next()==false)  return "用户名或密码错误"; 
	    	else return "VALIDUSER";
	    } catch (SQLException e) {
	    	return e.getMessage();
	    }
	}
	if(identity.equals("admin")){
	    ResultSet result = null;
	    try {
		result = stat.executeQuery("select password from administrator where username ='"+cardID+"' and password ='"+password+"'");
	    if(result.next()==false)  return "用户名或密码错误"; 
	    	else return "VALIDUSER";
	    } catch (SQLException e) {
	    	return e.getMessage();
	    }
	}
	return "WRONG_LOGIC";
    }
    
    
    
    public static void main(String[] args) throws SQLException {
    	System.out.println(isLogin("会员","124", "123"));
    }

    
}
