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
 * ����4�����������ɽ���֤�����������룬ɨ�������룬�����������ݿ�
 * 
 * @author YSJ
 * @version 0.85 xlsToDB(��Ҫjar��poi��ͷ�İ�*6��;xmlbeans;)//
 * 
 */
public class Utils {

	public static void main(String a[]) {
		test_readXlsxOrXlsToDB();
	}

	/**
	 * �������ݿ�ķ�����������Ҫ�����˻���������
	 * 
	 * @param sql
	 *            ��һ���ַ������������ڲ�ѯ
	 */
	public static void conDB(String sql) {
		String driver = "com.mysql.jdbc.Driver";
		// URLָ��Ҫ���ʵ����ݿ���scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/library";
		// MySQL����ʱ���û���
		String user = "root";
		// MySQL����ʱ������
		String password = "7410";
		try {
			// ������������
			Class.forName(driver);
			// �������ݿ�
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
	 *            �ļ�·��������
	 * @return sum ��������������
	 * @throws IOException
	 */
	public static int readXlsxToDB(String path) throws IOException {

		int sum = 0;// �����У���������¼
		int cells = 0;// ÿ�еĵ�Ԫ�������
		String sqlstr = "INSERT INTO `user` (";
		String strtmp = "VALUES(";
		boolean read = false;
		String table[] = { "cardID", "name", "ID", "sex", "TypeOfCard", "Starttime", "StudentID", "per_of_val",
				"telephone", "money_Reserved", "password" };

		// ѭ��������Sheet
		try {
			InputStream is = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			// ��ȡ��Excel�ļ��е����е���
			XSSFRow firstRow = sheet.getRow(1);
			cells = firstRow.getPhysicalNumberOfCells();
			XSSFCell cell;
			for (Iterator ite = sheet.rowIterator(); ite.hasNext();) {
				// �������е���
				XSSFRow row = (XSSFRow) ite.next();
				if (!read) {
					read = true;
					continue;
				}
				for (int i = 0; i < cells; i++) {
					// ���ǿյ�Ԫ��ƴ��str�ַ���
					try {
						cell = row.getCell(i);
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_NUMERIC:
							// ��ȡ����
							strtmp = strtmp + (int) cell.getNumericCellValue();
							break;
						case XSSFCell.CELL_TYPE_STRING:
							// ��ȡString
							strtmp = strtmp + "'" + cell.getRichStringCellValue().toString() + "'";
							break;
						}
						sqlstr += table[i];//
						if (i == cells - 1) {
							// ���һ����Ԫ���ʱ��ƴ����Ҫע��һ��
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("���յ�sql���Ϊ��" + sqlstr);
						} else {
							// �������һ����Ԫ��ƴ�Ӷ���
							sqlstr += ",";
							strtmp += ",";
						}
					} catch (NullPointerException e) {
						if (i == cells - 1) {
							// ���һ����Ԫ���ʱ��ƴ����Ҫע��һ��
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("���յ�sql���Ϊ��" + sqlstr);
						} else {
							// ����Ҫ����
						}
					}
				}
				conDB(sqlstr);
				sqlstr = "INSERT INTO `user` (";// �ַ�������Ϊԭʼ����ʼ��һ��ƴ��
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
	 *            �ļ�·��������
	 * @return sum ��������������
	 * @throws IOException
	 */
	public static int readXlsToDB(String path) throws IOException {

		int sum = 0;// �����У���������¼
		int cells = 0;// ÿ�еĵ�Ԫ�������
		String sqlstr = "INSERT INTO `user` (";
		String strtmp = "VALUES(";
		boolean read = false;
		String table[] = { "cardID", "name", "ID", "sex", "TypeOfCard", "Starttime", "StudentID", "per_of_val",
				"telephone", "money_Reserved", "password" };

		// ѭ��������Sheet
		try {
			InputStream is = new FileInputStream(path);
			HSSFWorkbook wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);
			// ��ȡ��Excel�ļ��е����е���
			HSSFRow firstRow = sheet.getRow(1);
			cells = firstRow.getPhysicalNumberOfCells();
			HSSFCell cell;
			for (Iterator ite = sheet.rowIterator(); ite.hasNext();) {
				// �������е���
				HSSFRow row = (HSSFRow) ite.next();
				if (!read) {
					read = true;
					continue;
				}
				for (int i = 0; i < cells; i++) {
					// ���ǿյ�Ԫ��ƴ��str�ַ���
					try {
						cell = row.getCell(i);
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_NUMERIC:
							// ��ȡ����
							strtmp = strtmp + (int) cell.getNumericCellValue();
							break;
						case XSSFCell.CELL_TYPE_STRING:
							// ��ȡString
							strtmp = strtmp + "'" + cell.getRichStringCellValue().toString() + "'";
							break;
						}
						sqlstr += table[i];//
						if (i == cells - 1) {
							// ���һ����Ԫ���ʱ��ƴ����Ҫע��һ��
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("���յ�sql���Ϊ��" + sqlstr);
						} else {
							// �������һ����Ԫ��ƴ�Ӷ���
							sqlstr += ",";
							strtmp += ",";
						}
					} catch (NullPointerException e) {
						if (i == cells - 1) {
							// ���һ����Ԫ���ʱ��ƴ����Ҫע��һ��
							sqlstr += ")";
							strtmp += ")";
							sqlstr = sqlstr + strtmp;
							System.out.println("���յ�sql���Ϊ��" + sqlstr);
						} else {
							// ����Ҫ����
						}
					}
				}
				conDB(sqlstr);
				sqlstr = "INSERT INTO `user` (";// �ַ�������Ϊԭʼ����ʼ��һ��ƴ��
				strtmp = "VALUES(";
				sum++;
				// �������ݿ�֮�󣬴�ѭ����������ʼ��һ��ѭ��
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
		System.out.println("�������" + num + "������");
	}

	/**
	 * ����ı���ͼƬ���������ɽ���֤
	 * 
	 * @param pressText
	 *            ��Ҫ��ӵ�����
	 * @param srcImageFile
	 *            ԭͼƬ·��
	 * @param destImageFile
	 *            ���ɺ��ͼƬ·��
	 */
	public final static void addTextToPic(String pressText, String srcImageFile, String destImageFile) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			// �����ֿ���� ȥ����ë��
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g.drawImage(src, 0, 0, width, height, null);
			// ������ɫ
			g.setColor(Color.BLACK);
			// ���� Font
			g.setFont(new Font("������ͤ�к�_GBK", Font.BOLD, 54));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.9F));
			// ��һ����->���õ����ݣ�������������->������ͼƬ�ϵ�����λ��(x,y) .
			g.drawString(pressText, 100, 100);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// ������ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test_addTextToPic() {
		// ԭͼ
		String srcImgPath = "pic/jieyue.png";
		// �����ֵ�ͼ
		String targerPath = "pic/marked.jpg";
		// ��ͼƬ�����
		addTextToPic("������ƻ��򱨸�", srcImgPath, targerPath);// ����OK
		System.out.println("�������OK������Ŀ¼�µ�ͼƬ�Ƿ�ı�");
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
					null);//105��50��width��height
			Path path = new File(imgPath).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, "png", path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡͼƬ�е�������
	 * @param imgPath ͼƬ����·��
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
		System.out.println("�����������£�");
		System.out.println(decodeContent);
	}

}
