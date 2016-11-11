package com.ichunming.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Ignore;
import org.junit.Test;

import com.ichunming.util.entity.User;

public class ExcelUtilTest {

    @Test
    public void writeTest()
    {
    	// prepared data
    	List<User> users = Arrays.asList(new User("ming", "1234"), new User("ning", "1234"));
    	
    	// -----------------------------------
    	
		HSSFWorkbook wb = null; // workbook
		Sheet sheet = null; // excel sheet
		Map<Integer, String> recode = new HashMap<Integer, String>();
		int line = 0;
		
		// create excel workbook
        wb = new HSSFWorkbook();
        // create excel sheet with name 'example'
        sheet = wb.createSheet("user");
		// set ExcelUtil target
        ExcelUtil.setTarget(wb, sheet);
		
        // head style
		CellStyle headStyle= ExcelUtil.createStyle();
		ExcelUtil.setFont(headStyle, Font.COLOR_NORMAL, Font.BOLDWEIGHT_BOLD);
		
		// data style
		CellStyle dataStyle = ExcelUtil.createStyle();
		ExcelUtil.setDefaultDataFormat(dataStyle);
		
		// write header
		recode.clear();
		setHeader(recode);
		ExcelUtil.writeLine(line++, recode);
		
		// write recode
		for(User user : users) {
			recode.clear();
			setRecode(recode, line, user);
			ExcelUtil.setRowStyle(line, dataStyle);
			ExcelUtil.writeLine(line++, recode);
		}
		
		// set columns width
		ExcelUtil.setColumnsWidth(new int[] {0, 1, 2, 3, 4}, new int[] {10, 20, 10, 20, 20});
		
		// set column style
		ExcelUtil.setRowStyle(0, headStyle);
		
		// save file
		String fold = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(fold + "out_users.xls");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    
    @Test
    public void readTest() {
    	
    	POIFSFileSystem fs = null; // file
    	HSSFWorkbook wb = null; // workbook
		Sheet sheet = null; // excel sheet
		List<User> users = null;
		
		try {
			// load file
			String file = this.getClass().getClassLoader().getResource("users.xls").getPath();
			fs = new POIFSFileSystem(new FileInputStream(file));
			// create excel workbook
	        wb = new HSSFWorkbook(fs);
	        // get sheet
	        sheet = wb.getSheetAt(0);
	        // set ExcelUtil target
	        ExcelUtil.setTarget(wb, sheet);
	        // convert to object
	        users = ExcelUtil.convertToList(User.class);
	        
	        // print user
	        for(User user : users) {
	        	System.out.println(user.toString());
	        }
		} catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Ignore
    private void setHeader(Map<Integer, String> recode) {
    	recode.put(0, "ID");
    	recode.put(1, "Name");
    	recode.put(2, "Password");
    }
    
    @Ignore
    private void setRecode(Map<Integer, String> recode, int line, User user) {
    	recode.put(0, "" + line);
    	recode.put(1, user.getName());
    	recode.put(2, user.getPassword());
    }
}
