package com.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 包含4个方法：生成借阅证，生成条形码，扫描条形码，批量导入数据库
 * 
 * @author YSJ
 * @version 0.85 xlsToDB(需要jar包poi开头的包*6个;xmlbeans;)//
 * 
 */
public class Utils {

	public static void main(String a[]) {
		test_readXlsxOrXlsToDB();
	}

	/**
	 * 连接数据库的方法，可能需要更改账户名和密码
	 * 
	 * @param sql
	 *            传一个字符串语句进来用于查询
	 */
	public static void conDB(String sql) {
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/library";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "7410";
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			Connection conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			Statement statement = conn.createStatement();

			boolean rs = statement.execute(sql);

			conn.close();

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * 
	 * @param path
	 *            文件路径传进来
	 * @return sum 导入数据总条数
	 * @throws IOException
	 */
	public static int readXlsxToDB(String path) throws IOException {

		int sum = 0;// 多少行，共几条记录
		int cells = 0;// 每行的单元格的数量
		String sqlstr = "INSERT INTO `user` (";
		String strtmp = "VALUES(";
		boolean read = false;
		String table[] = { "cardID", "name", "ID", "sex", "TypeOfCard", "Starttime", "StudentID", "per_of_val",
				"telephone", "money_Reserved", "password" };

		// 循环工作表Sheet
		try {
			InputStream is = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			// 获取到Excel文件中的所有的列
			XSSFRow firstRow = sheet.getRow(1);
			cells = firstRow.getPhysicalNumberOfCells();
			XSSFCell cell;
			for (Iterator ite = sheet.rowIterator(); ite.hasNext();) {
				// 解析所有的行
				XSSFRow row = (XSSFRow) ite.next();
				if (!read) {
					read = true;
					continue;
				}
				for (int i = 0; i < cells; i++) {
					// 检测非空单元格，拼接str字符串
					try {
						cell = row.getCell(i);
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_NUMERIC:
							// 读取数字
							strtmp = strtmp + (int) cell.getNumericCellValue();
							break;
						case XSSFCell.CELL_TYPE_STRING:
							// 读取String
							strtmp = strtmp + "'" + cell.getRichStringCellValue().toString() + "'";
							break;
						}
						sqlstr += table[i];//
						if (i == cells - 1) {
							// 最后一个单元格的时候，拼接需要注意一下
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("最终的sql语句为：" + sqlstr);
						} else {
							// 不是最后一个单元格，拼接逗号
							sqlstr += ",";
							strtmp += ",";
						}
					} catch (NullPointerException e) {
						if (i == cells - 1) {
							// 最后一个单元格的时候，拼接需要注意一下
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("最终的sql语句为：" + sqlstr);
						} else {
							// 不需要处理
						}
					}
				}
				conDB(sqlstr);
				sqlstr = "INSERT INTO `user` (";// 字符串设置为原始，开始新一轮拼接
				strtmp = "VALUES(";
				sum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

	/**
	 * 
	 * @param path
	 *            文件路径传进来
	 * @return sum 导入数据总条数
	 * @throws IOException
	 */
	public static int readXlsToDB(String path) throws IOException {

		int sum = 0;// 多少行，共几条记录
		int cells = 0;// 每行的单元格的数量
		String sqlstr = "INSERT INTO `user` (";
		String strtmp = "VALUES(";
		boolean read = false;
		String table[] = { "cardID", "name", "ID", "sex", "TypeOfCard", "Starttime", "StudentID", "per_of_val",
				"telephone", "money_Reserved", "password" };

		// 循环工作表Sheet
		try {
			InputStream is = new FileInputStream(path);
			HSSFWorkbook wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);
			// 获取到Excel文件中的所有的列
			HSSFRow firstRow = sheet.getRow(1);
			cells = firstRow.getPhysicalNumberOfCells();
			HSSFCell cell;
			for (Iterator ite = sheet.rowIterator(); ite.hasNext();) {
				// 解析所有的行
				HSSFRow row = (HSSFRow) ite.next();
				if (!read) {
					read = true;
					continue;
				}
				for (int i = 0; i < cells; i++) {
					// 检测非空单元格，拼接str字符串
					try {
						cell = row.getCell(i);
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_NUMERIC:
							// 读取数字
							strtmp = strtmp + (int) cell.getNumericCellValue();
							break;
						case XSSFCell.CELL_TYPE_STRING:
							// 读取String
							strtmp = strtmp + "'" + cell.getRichStringCellValue().toString() + "'";
							break;
						}
						sqlstr += table[i];//
						if (i == cells - 1) {
							// 最后一个单元格的时候，拼接需要注意一下
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("最终的sql语句为：" + sqlstr);
						} else {
							// 不是最后一个单元格，拼接逗号
							sqlstr += ",";
							strtmp += ",";
						}
					} catch (NullPointerException e) {
						if (i == cells - 1) {
							// 最后一个单元格的时候，拼接需要注意一下
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("最终的sql语句为：" + sqlstr);
						} else {
							// 不需要处理
						}
					}
				}
				conDB(sqlstr);
				sqlstr = "INSERT INTO `user` (";// 字符串设置为原始，开始新一轮拼接
				strtmp = "VALUES(";
				sum++;
				// 连接数据库之后，此循环结束，开始下一轮循环
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

	public static void test_readXlsxOrXlsToDB() {
		String fp = "doc/daoru.xls";
		int num = 0;
		try {
			if (fp.endsWith("xls")) {
				num = readXlsToDB(fp);
			} else {
				num = readXlsxToDB(fp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("共添加了" + num + "条数据");
	}

	/**
	 * 添加文本到图片，用于生成借阅证
	 * 
	 * @param pressText
	 *            想要添加的文字
	 * @param srcImageFile
	 *            原图片路径
	 * @param destImageFile
	 *            生成后的图片路径
	 */
	public final static void addTextToPic(String pressText, String srcImageFile, String destImageFile) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			// 开文字抗锯齿 去文字毛刺
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g.drawImage(src, 0, 0, width, height, null);
			// 设置颜色
			g.setColor(Color.BLACK);
			// 设置 Font
			g.setFont(new Font("方正兰亭中黑_GBK", Font.BOLD, 54));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9F));
			// 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
			g.drawString(pressText, 100, 100);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test_addTextToPic() {
		// 原图
		String srcImgPath = "pic/jieyue.png";
		// 加了字的图
		String targerPath = "pic/marked.jpg";
		// 给图片添加字
		addTextToPic("李辰的饮酒基因报告", srcImgPath, targerPath);// 测试OK
		System.out.println("添加文字OK，请检查目录下的图片是否改变");
	}
	
	/**
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public static void generateBarcode(String contents, String imgPath) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, 105);//width
		try {
			/*
			 * BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
			 * BarcodeFormat.EAN_13, codeWidth, height, null);
			 */

			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.CODE_128, 105, 50,
					null);//105和50是width和height
			Path path = new File(imgPath).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, "png", path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取图片中的条形码
	 * @param imgPath 图片所在路径
	 * @return
	 */
	public static String readBarcode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void test_generateBarcode() {
		String imgPath = "pic/barcode4.png";
		// String contents = "6943620593115";
		String contents = "9787121072970";
		
		generateBarcode(contents, imgPath);
		System.out.println("ENCODE OK");

		String imgPath2 = "pic/barcode4.png";
		String decodeContent = readBarcode(imgPath2);
		System.out.println("解码内容如下：");
		System.out.println(decodeContent);
	}

}
