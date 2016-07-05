package com.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 包含4个方法：生成借阅证，生成条形码，扫描条形码，批量导入数据库
 * 
 * @author YSJ
 * @version 0.8 xlsToDB(需要jar包poi开头的包*6个;xmlbeans;)//
 * 
 */
public class Utils {

	public static void main(String a[]) {
		int num = 0;
		String fp = "doc/daoru.xlsx";
		try {
			num = readXlsx(fp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int readXlsx(String path) throws IOException {

		int sum = 0;// 多少行，共几条记录
		int cells = 0;// 每行的单元格的数量
		String sqlstr = "INSERT INTO `user` VALUES (";
		boolean read = false;
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
				for (int i = 1; i <= cells; i++) {
					try {
						cell = row.getCell(i - 1);// 获取当前行的第i个单元格

						switch (cell.getCellType()) {

						case XSSFCell.CELL_TYPE_NUMERIC:
							// 读取数字
							sqlstr = sqlstr + (int) cell.getNumericCellValue();
							break;
						case XSSFCell.CELL_TYPE_STRING:
							// 读取String
							sqlstr = sqlstr + "'" + cell.getRichStringCellValue().toString() + "'";
							break;
						}
						if (i == cells) {
							// 最后一个单元格的时候，拼接需要注意一下
							sqlstr += ")";
						} else {
							// 不是最后一个单元格，拼接逗号
							sqlstr += ",";
						}
					} catch (NullPointerException e) {
						sqlstr += "' '";
					}
				}

				System.out.println("拼接出来的sqlstr为：" + sqlstr + "   数据库已经连接");
				// conDB(sqlstr);// 至此，单个sqlstr拼接完成，调用数据库
				sqlstr = "INSERT INTO `user` VALUES (";// 字符串设置为原始，开始新一轮拼接
				sum++;
				// 连接数据库之后，此循环结束，开始下一轮循环
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

}
