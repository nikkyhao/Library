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
 * ����4�����������ɽ���֤�����������룬ɨ�������룬�����������ݿ�
 * 
 * @author YSJ
 * @version 0.8 xlsToDB(��Ҫjar��poi��ͷ�İ�*6��;xmlbeans;)//
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

		int sum = 0;// �����У���������¼
		int cells = 0;// ÿ�еĵ�Ԫ�������
		String sqlstr = "INSERT INTO `user` VALUES (";
		boolean read = false;
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
				for (int i = 1; i <= cells; i++) {
					try {
						cell = row.getCell(i - 1);// ��ȡ��ǰ�еĵ�i����Ԫ��

						switch (cell.getCellType()) {

						case XSSFCell.CELL_TYPE_NUMERIC:
							// ��ȡ����
							sqlstr = sqlstr + (int) cell.getNumericCellValue();
							break;
						case XSSFCell.CELL_TYPE_STRING:
							// ��ȡString
							sqlstr = sqlstr + "'" + cell.getRichStringCellValue().toString() + "'";
							break;
						}
						if (i == cells) {
							// ���һ����Ԫ���ʱ��ƴ����Ҫע��һ��
							sqlstr += ")";
						} else {
							// �������һ����Ԫ��ƴ�Ӷ���
							sqlstr += ",";
						}
					} catch (NullPointerException e) {
						sqlstr += "' '";
					}
				}

				System.out.println("ƴ�ӳ�����sqlstrΪ��" + sqlstr + "   ���ݿ��Ѿ�����");
				// conDB(sqlstr);// ���ˣ�����sqlstrƴ����ɣ��������ݿ�
				sqlstr = "INSERT INTO `user` VALUES (";// �ַ�������Ϊԭʼ����ʼ��һ��ƴ��
				sum++;
				// �������ݿ�֮�󣬴�ѭ����������ʼ��һ��ѭ��
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

}
