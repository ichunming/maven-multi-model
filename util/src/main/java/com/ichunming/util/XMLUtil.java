/**
 * xml util
 * 2016/09/07 chunming
 * v0.1
 */
package com.ichunming.util;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLUtil {
	private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);
	
	// private constructor
	private XMLUtil() {};
	
	/**
	 * read XML and convert to object
	 * @param String path
	 * @param Class<T> clz
	 * @return List<T>
	 */
	public static <T>List<T> read(String path, Class<T> clz) {
		List<T> result = null;
		File file = new File(path);
		// file exist check
		if(!file.exists()) {
			logger.debug("file " + path + " not exist!");
			return result;
		}
		// read file
		SAXBuilder builder = new SAXBuilder();
		try {
			// Document
			Document doc = builder.build(file);
			// root element
			Element root = doc.getRootElement();
			// childern of root
			List<Element> list = root.getChildren();
			// target object information
			Field[] fields = clz.getDeclaredFields();
			String simpleName = simpleName(clz);
			String[] methods = getMethods(fields, 0);
			result = new LinkedList<T>();
			
			//convert to object
			for(Element e : list) {
				if(!e.getName().equals(simpleName)) {
					// if other object, skip
					continue;
				}
				// new instance
				T obj = clz.newInstance();
				for(int i = 0; i < fields.length; i++) {
					// set fields
					Method method = clz.getMethod(methods[i], String.class);
					// invoke
					method.invoke(obj, e.getChildText(fields[i].getName()));
				}
				// add to list
				result.add(obj);
			}
		} catch (Exception e) {
			logger.debug("read xml file failure");
		}
		
		logger.debug("read xml file success");
		return result;
	}
	
	/**
	 * write object to xml file
	 * @param List<T> objList
	 * @param String path
	 * @param Class<T> clz
	 */
	public static <T>void write(List<T> objList, String path, Class<T> clz) {
		File file = new File(path);
		if(file.exists()) {
			// delete file
			file.delete();
		}
		
		// document
		Document doc = new Document();
		// root
		Element root = new Element("root");
		// target object information
		String simpleName = simpleName(clz);
		Field[] fields = clz.getDeclaredFields();
		String[] methods = getMethods(fields, 1);
		
		try {
			// create new file
			file.createNewFile();
			
			for(T target : objList) {
				// target element
				Element e = new Element(simpleName);
				for(int i = 0; i < fields.length; i++) {
					// attribute element
					Element attrE = new Element(fields[i].getName());
					// get fields
					Method method = clz.getMethod(methods[i]);
					// invoke
					Object text = method.invoke(target);
					if(null == text) {
						// not add
						continue;
					}
					attrE.setText(text.toString());
					e.addContent(attrE);
				}
				// add to root
				root.addContent(e);
			}
			doc.addContent(root);
			// write to file
			XMLOutputter xmlOutputter = new XMLOutputter();
			xmlOutputter.setFormat(Format.getPrettyFormat());
			xmlOutputter.output(doc, new FileWriter(file));
			logger.debug("write xml file success");
		} catch (Exception e) {
			logger.debug("write xml file failure");
		}
	}
	
	/**
	 * get methods by fields
	 * @param Field[] fields
	 * @param int flg
	 * @return String[]
	 */
	private static String[] getMethods(Field[] fields, int flg) {
		String[] result = new String[fields.length];
		StringBuilder sb = null;
		int index = 0;
		String prefix = "";
		
		if(0 == flg) {
			// set method
			prefix = "set";
		}else if(1 == flg) {
			// get method
			prefix = "get";
		}
		for(Field field : fields) {
			sb = new StringBuilder(field.getName());
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			result[index++] = prefix + sb.toString();
		}
		return result;
	}
	
	/**
	 * get simple name
	 * @param Class<T> clz
	 * @return String
	 */
	private static <T>String simpleName(Class<T> clz) {
		return clz.getSimpleName().toLowerCase();
	}
}
