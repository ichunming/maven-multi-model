/**
 * excel util
 * 2016/09/07 ming
 * v0.1
 */
package com.ichunming.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	// private constructor
	private ExcelUtil() {}
	
	/**
	 * Excel写入一行
	 * @param sheet
	 * @param line
	 * @param recode
	 */
	public static void writeLine(Sheet sheet, int line, Map<Integer, String> recode) {
		// 创建Row
		Row row = sheet.createRow(line);
		Cell cell = null;
		
		// 单元格设值
		for(int column : recode.keySet()) {
			cell = row.createCell(column);
			cell.setCellValue(recode.get(column));
		}
	}
	
	/**
	 * 设置行属性
	 * @param sheet
	 * @param line
	 * @param style
	 */
	public static void setRowStyle(Sheet sheet, int line, CellStyle style) {
		// 取得Row
		Row row = sheet.getRow(line);
		// 设置属性
		for(int column = row.getFirstCellNum(); column < row.getLastCellNum(); column++) {
			setCellStyle(sheet, line, column, style);
		}
	}
	
	/**
	 * 设置单元格属性
	 * @param sheet
	 * @param line
	 * @param column
	 * @param style
	 */
	public static void setCellStyle(Sheet sheet, int line, int column, CellStyle style) {
		// 取得单元格
		Cell cell = sheet.getRow(line).getCell(column);
		// 设置单元格属性
		cell.setCellStyle(style);
	}
	
	/**
	 * 设置列宽
	 * @param sheet
	 * @param column
	 * @param width
	 */
	public static void setColumnWidth(Sheet sheet, int column, int width) {
		sheet.setColumnWidth(column, 256 * width);
	}
	
	/**
	 * 设置列宽
	 * @param sheet
	 * @param column
	 * @param width
	 */
	public static void setColumnsWidth(Sheet sheet, int[] columns, int[] widths) {
		for(int i = 0; i < columns.length; i++) {
			setColumnWidth(sheet, columns[i], widths[i]);
		}
	}
	
	/**
	 * excel文件内容转换成对象list
	 * @param sheet
	 * @param clz
	 * @param ignoreId
	 * @return
	 */
	public static <T> List<T> convertToList(Sheet sheet, Class<T> clz, boolean ignoreId) {
		List<T> result = null; // result
		T obj = null; // object
		Field[] fields = null; // fields
		String[] methods = null; // methods
		Row row = null; // row
		
		try {
			// excel information
			Row header = sheet.getRow(0);
			int colFrom = header.getFirstCellNum();
			int colTo = header.getLastCellNum();
			int rowFrom = sheet.getFirstRowNum() + 1;
			int rowTo = sheet.getLastRowNum();
			colFrom = ignoreId ? colFrom + 1 : colFrom;
			
			// target object information
			fields = clz.getDeclaredFields();
			methods = getMethods(fields, header, ignoreId);
			result = new LinkedList<T>();
			String param;
			
			//convert to object
			for(int rownum = rowFrom; rownum <= rowTo; rownum++) {
				row = sheet.getRow(rownum);
				// new instance
				obj = clz.newInstance();
				for(int colnum = colFrom; colnum < colTo; colnum++) {
					// method
					Method method = clz.getMethod(methods[colnum], java.lang.String.class);
					// parameter
					switch(row.getCell(colnum).getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						// 数值型
						param = row.getCell(colnum).getNumericCellValue() + "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						// 布尔型
						param = row.getCell(colnum).getBooleanCellValue() ? "true" : "false";
						break;
					case Cell.CELL_TYPE_STRING:
						// 字符串型
						param = row.getCell(colnum).getStringCellValue();
						break;
					default:
						// 默认
						param = "";
					}
					// invoke
					method.invoke(obj, param);
				}
				// add to list
				result.add(obj);
			}
		} catch (Exception e) {
			logger.debug("fail to convert excel file to object list");
		}
		
		logger.debug("success to convert excel file to object list");
		return result;
	}
	
	/**
	 * get methods
	 * @param fields
	 * @param header
	 * @param ignoreId
	 * @return
	 */
	private static String[] getMethods(Field[] fields, Row header, boolean ignoreId) {
		// cloFrom, colTo
		int colFrom = header.getFirstCellNum();
		int colTo = header.getLastCellNum();
		// ID process
		colFrom = ignoreId ? colFrom + 1 : colFrom;
		
		String[] result = new String[colTo];
		StringBuilder sb = null;
		String property;
		String prefix = "set"; // prefix

		for(int colnum = colFrom; colnum < colTo; colnum++) {
			// header规则:类属性(大小写) + '_' + ' '混合
			property = header.getCell(colnum).getStringCellValue().trim().replace("_", "");
			for(Field field : fields) {
				if(!field.getName().toLowerCase().equals(property.toLowerCase())) {
					continue;
				} else {
					sb = new StringBuilder(field.getName());
					sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
					result[colnum] = prefix + sb.toString();
					break;
				}
			}
		}
		
		return result;
	}
}
