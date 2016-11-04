/**
 * properties util
 * created 2016/08/07
 * by ming
 */
package com.ichunming.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropUtil {

	// private constructor
	private PropUtil() {}
	
	/**
	 * 加载properties文件，转换成map类型返回
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, String> read(String filePath) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		
		Properties prop = new Properties();
		InputStream is = null;
		InputStreamReader isr = null;
		
		try {
			is = new FileInputStream(filePath);
			isr = new InputStreamReader(is, "UTF-8");
			prop.load(isr);
			// key set
			Set<Object> keySet = prop.keySet();
			Iterator<Object> it = keySet.iterator();
			String key = "";
			map.clear();
			// add properties to map
			while(it.hasNext()) {
				key = (String)it.next(); 
				map.put(key, prop.getProperty(key));
			}
		} catch (FileNotFoundException e) {
			// print status
			throw e;
		} catch (IOException e) {
			// print status
			throw e;
		} finally {
			if(null != is) {
				try {
					is.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if(null != isr) {
				try {
					isr.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		
		return map;
	}
}
