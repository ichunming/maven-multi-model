/**
 * excel util
 * created 2016/09/07
 * by ming
 */
package com.ichunming.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
		// 创建row
		sheet.createRow(line);
		// 单元格设值
		for(int column : recode.keySet()) {
			writeCell(sheet, line, column, recode.get(column));
		}
	}
	
	/**
	 * Excel写入单元格
	 * @param sheet
	 * @param rownum
	 * @param column
	 * @param value
	 */
	public static void writeCell(Sheet sheet, int rownum, int column, String value) {
		Cell cell = sheet.getRow(rownum).createCell(column);
		cell.setCellValue(value);
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
	 * @param columns
	 * @param widths
	 */
	public static void setColumnsWidth(Sheet sheet, int[] widths) {
		for(int i = 0; i < widths.length; i++) {
			setColumnWidth(sheet, i, widths[i]);
		}
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
	 * 设置行高
	 * @param sheet
	 * @param line
	 * @param height
	 */
	public static void setRowHeight(Sheet sheet, int line, int height) {
		sheet.getRow(line).setHeight((short)height);
	}
	
	/**
	 * 取得默认标题样式
	 * @param wb
	 * @return
	 */
	public static CellStyle getDefaultHeadStyle(Workbook wb) {
		Font font = wb.createFont();
		font.setColor(Font.COLOR_NORMAL);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 字体：加粗
		CellStyle headStyle= wb.createCellStyle();
		headStyle.setFont(font);
		// 居中
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 自动换行
		headStyle.setWrapText(true);
		return headStyle;
	}
	
	/**
	 * 合并单元格
	 * @param sheet
	 * @param region
	 */
	public static void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		sheet.addMergedRegion(region);
	}
	
	
	/**
	 * excel文件内容转换成对象list
	 * @param sheet
	 * @param clz
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static <T> List<T> convertToList(Sheet sheet, Class<T> clz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		List<T> result = null; // result
		T obj = null; // object
		
		int rowFrom = sheet.getFirstRowNum() + 1;
		int rowTo = sheet.getLastRowNum();
		
		// target object information
		result = new LinkedList<T>();
		
		//convert to object
		for(int rownum = rowFrom; rownum <= rowTo; rownum++) {
			// new instance
			obj = convertToObj(sheet, clz, rownum);
			
			if(null == obj) {
				// convert fail
				logger.debug("convert fail[rownum:" + rownum + "]");
			}
			// add to list
			result.add(obj);
		}
		
		logger.debug("success to convert excel file to object list");
		return result;
	}
	
	/**
	 * excel中行记录转换成对象
	 * @param sheet
	 * @param clz
	 * @param rownum
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static <T> T convertToObj(Sheet sheet, Class<T> clz, int rownum) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		T obj = null; // object
		Field[] fields = null; // fields
		String[] methods = null; // methods
		Row row = null; // row
		
		// excel information
		Row header = sheet.getRow(0);
		int colFrom = header.getFirstCellNum();
		int colTo = header.getLastCellNum();
		
		// target object information
		fields = clz.getDeclaredFields();
		methods = getMethods(fields, header);
		String param;
		
		//convert to object
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
		
		logger.debug("success to convert excel file to object list");
		return obj;
	}
	
	/**
	 * get methods
	 * @param fields
	 * @param header
	 * @param ignoreId
	 * @return
	 */
	private static String[] getMethods(Field[] fields, Row header) {
		// cloFrom, colTo
		int colFrom = header.getFirstCellNum();
		int colTo = header.getLastCellNum();
		
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
