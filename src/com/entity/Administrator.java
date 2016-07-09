package com.entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.test.LibConnection;

/**
 * 
 * @author ZM
 *
 */
public class Administrator {
	String userId = null;
	static Connection connection = null;
	static {
		connection = LibConnection.getConnection();
	}

	public Administrator() {
		try {
			Statement sta = LibConnection.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Administrator(String userId) {
		this.userId = userId;
		try {
			Statement sta = LibConnection.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertUser(String name, int ID, String sex, int stuID, String type, String tel, int pwd) {
		// 插入单个用户
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date start = new Date(System.currentTimeMillis());// 借阅证日期，系统当前日期
		Date end = new Date(start.getYear() + 1, start.getMonth(), start.getDay());// 失效日期，过一年

		String sql = "INSERT INTO `user`(NAME,ID,sex,TypeOfCard,starttime,per_of_val,studentID,telephone,password)"
				+ "VALUES('" + name + "'," + ID + ",'" + sex + "','" + type + "','" + start + "','" + end + "'," + stuID
				+ ",'" + tel + "'," + pwd + ")";
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public ResultSet searchAdmin() {
		// 通过id查询管理员
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT username,password,name,sex FROM administrator WHERE username=" + userId;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void updateUser(String name, int ID, String sex, int stuID, String type, String tel, double money,
			int cardID) {
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "update user set NAME='" + name + "',ID=" + ID + ",sex='" + sex + "',TypeOfCard='" + type
				+ "',studentID=" + stuID + ",telephone=" + tel + ",money_Reserved=" + money + " where cardID=" + cardID;
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateUser(double money, int cardID) {
		// 修改预存金额
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "update user set money_Reserved=" + money + " where cardID=" + cardID;
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultSet searchCanBorrow(int index) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String sql = "";

		sql = "(SELECT distinct bookid FROM book,borrow WHERE bo_bookid=bookid "
				+ "and return_date is not null and cateindex ="+index+")"
				+ "UNION(SELECT bookid FROM book WHERE bookid NOT IN"
				+ "(SELECT DISTINCT bo_bookid FROM borrow) and cateindex ="+index+")";

		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet searchUser(int cardID) {
		// 通过借阅证卡号查询用户
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT * FROM `user` WHERE cardID=" + cardID;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public void insertBooktype(String name, String author, String press, String type, Date pressdate, String edition,
			String presstime, String size, int pages, double price) {
		// 插入单本图书类别
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "INSERT INTO `category` (bookname,author,press,booktype,pressdate,edition,presstime,size,pages,price)"
				+ "VALUES('" + name + "','" + author + "','" + press + "','" + type + "','" + pressdate + "','"
				+ edition + "','" + presstime + "','" + size + "'," + pages + "," + price + ")";
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertBook(int bookid, int index) {
		// 添加图书，bookid是条形码
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "insert into book values(" + bookid + "," + index + ")";
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBook(String name, String author, String press, String type, double price, int index) {
		// 修改图书信息
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "update category set bookname='" + name + "',author='" + author + "',press='" + press
				+ "',booktype='" + type + "', price=" + price + " where `index`=" + index;
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void borrow(String bookid, int cardid) {
		// 借书,是否要判断这本书能不能借？如果在borrow表中，归还日期为null，则不能借
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date bo_date = new Date(System.currentTimeMillis());// 借阅日期，系统当前日期
		Date dead = new Date(bo_date.getYear(), bo_date.getMonth() + 1, bo_date.getDay());// 截止日期，过一个月
		String sql = "insert into borrow (bo_bookid,bo_cardID,bo_date,deadline) values('" + bookid + "'," + cardid
				+ ",'" + bo_date + "','" + dead + "')";
		try {
			sta.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public boolean canborrow(String bookid) {
	// // 判断这本书能不能借
	// boolean canborrow = true;
	// String sql = "select return_date from borrow where bo_bookid='" + bookid
	// + "'";
	// ResultSet rs = null;
	// try {
	// rs = sta.executeQuery(sql);
	// while (rs.next()) {
	// String ret_date = rs.getString("return_date");
	// if (ret_date == null) {
	// canborrow = false;
	// return canborrow;
	// }
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return canborrow;
	// }
	public int searchBorrowNum(int index) {
		// 一共有几本index类的书
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int num = 0;
		String sql = "SELECT COUNT(bookid) as num FROM book,category,borrow WHERE bookid=bo_bookid AND cateindex=`index` AND return_date IS NULL AND `index`="
				+ index;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	public int searchNum(int index) {
		// 一共借出了几本index类的书
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int num = 0;
		String sql = "select count(bookid) as num from category,book where `index`=cateindex and `index`=" + index;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	public void back(String bookid, int cardid) {
		// 还书
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date back = new Date(System.currentTimeMillis());// 归还日期，系统当前日期
		String sql = "update borrow set return_date='" + back + "' where bo_bookid='" + bookid + "' and bo_cardID="
				+ cardid;
		try {
			sta.execute(sql);
			System.out.println("成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet searchHisBor() {
		// 查询所有历史借阅记录
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID";
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet searchCurBor() {
		// 查询所有当前借阅
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID AND return_date IS NULL";
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet searchCurBor(String bookid) {
		// 查询所有当前借阅
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT `index`,bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID AND return_date IS NULL and bookid='"
				+ bookid + "'";
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet searchHisBorByBook(int index) {
		// 查询每本书的借阅记录
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT `index`,bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID and `index`="
				+ index;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet searchHisBorByUser(int cardID) {
		// 查询每个人的借阅记录
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "SELECT `index`,bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID and cardID="
				+ cardID;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet searchOverdate() {
		// 查询所有违章记录，归还日期为空并且deadline小于当前日期，或者，归还日期大于deadline
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date date = new Date(System.currentTimeMillis());// 系统当前日期
		String sql = "SELECT bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID and (return_date > deadline or (deadline<'"
				+ date + "' and return_date is null))";
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet searchOverdateByUser(int cardID) {
		// 查询某个人的违章记录
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date date = new Date(System.currentTimeMillis());// 系统当前日期
		String sql = "SELECT bookid,bookname,cardID,`name`,bo_date,deadline,return_date FROM borrow,book,`user`,category WHERE bo_bookid=bookid AND `index`=cateindex AND bo_cardID=cardID and (return_date > deadline or (deadline<'"
				+ date + "' and return_date is null))" + " and cardID=" + cardID;
		ResultSet rs = null;
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public double fine() {
		// 超期天数
		Statement sta = null;
		try {
			sta = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		double fine = 0;
		Date date = new Date(System.currentTimeMillis());// 系统当前日期
		String sql = "SELECT deadline,return_date FROM borrow WHERE return_date > deadline";
		String sql2 = "select deadline from borrow where (deadline<'" + date + "' and return_date is null)";
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			rs = sta.executeQuery(sql);
			while (rs.next()) {
				Date deadline = rs.getDate("deadline");
				Date return_date = rs.getDate("return_date");
				long beginTime = deadline.getTime();
				long endTime = return_date.getTime();
				long betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24) + 0.5);
				System.out.println(betweenDays);
			}
			rs1 = sta.executeQuery(sql2);
			while (rs1.next()) {
				Date deadline2 = rs1.getDate("deadline");
				long dead = deadline2.getTime();
				long today = date.getTime();
				long betweenDays = (long) ((today - dead) / (1000 * 60 * 60 * 24) + 0.5);
				System.out.println(betweenDays);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fine;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Administrator admin = new Administrator("sdf");
		String name = "bob";
		int ID = 19951016;
		String sex = "man";
		int stuID = 20130530;
		String type = "stu";
		String tel = "15165110710";
		int pwd = stuID;
		double money = 100;
		int cardID = 668;

		// 测试添加会员方法
		// admin.insertUser(name, ID, sex, stuID, type, tel, pwd);
		// System.out.println("添加成功");

		// 测试修改会员方法
		// admin.updateUser(name, ID, sex, stuID, type, tel,money, cardID);
		// System.out.println("修改成功");

		// 测试搜索会员方法
		// ResultSet rs = admin.searchUser(cardID);
		// try {
		// while (rs.next()) {
		// String cID = rs.getString("cardID");
		// String username = rs.getString("name");
		// String date = rs.getString("per_of_val");
		// System.out.println(username + "," + date);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 测试添加类别的方法
		String bname = "java2";
		String author = "ABCD";
		String press = "SDDX";
		String booktype = "IT";
		Date date = new Date(System.currentTimeMillis());
		String edition = "3";
		String presstime = "2";
		String size = "16";
		int pages = 200;
		double price = 50;
		int index = 121322;
		// admin.insertBooktype(bname, author, press, booktype,date, edition,
		// presstime, size, pages, price);

		// 测试添加单本图书
		// admin.insertBook(0002,121322);

		// 测试修改图书信息
		// admin.updateBook(name, author, press, type, price, index);

		// 测试借书功能
		// admin.borrow("001", 669);

		// 测试该书能不能借
		// boolean b=admin.canborrow("12333");
		// System.out.println(b);

		// int num=admin.seachNum(121322);
		// System.out.println(num);
		// 测试还书功能
		// admin.back("001", 668);

		// 测试查询所有借阅记录
		// ResultSet rs=admin.searchHisBor();
		// try {
		// while(rs.next()){
		// String bid=rs.getString("bookname");
		// String cid=rs.getString("name");
		// System.out.println(bid+","+cid);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 测试查询当前借阅记录
		// ResultSet rs=admin.searchCurBor("001");
		// try {
		// while(rs.next()){
		// String bid=rs.getString("bookname");
		// String cid=rs.getString("name");
		// System.out.println(bid+","+cid);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// 测试查询某本书的借阅记录
		// ResultSet rs=admin.searchHisBorByBook(index);
		// try {
		// while(rs.next()){
		// String bid=rs.getString("bookname");
		// String cid=rs.getString("name");
		// System.out.println(bid+","+cid);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 测试查询某个人的借阅记录
		// ResultSet rs=admin.searchHisBorByUser(cardID);
		// try {
		// while(rs.next()){
		// String bid=rs.getString("bookname");
		// String cid=rs.getString("name");
		// System.out.println(bid+","+cid);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 测试查询某个人的违章记录
		// ResultSet rs=admin.searchOverdate();
		// try {
		// while(rs.next()){
		// String bid=rs.getString("bookname");
		// String cid=rs.getString("name");
		// System.out.println(bid+","+cid);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 超期天数
		// double fine=admin.fine();
		// System.out.println(fine);

		ResultSet rs1 = admin.searchCanBorrow(1);
		try {
			while (rs1.next()) {
				String bid = rs1.getString("bookid");

				System.out.println(bid);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
