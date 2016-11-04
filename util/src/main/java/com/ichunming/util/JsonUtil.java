/**
 * json util
 * created 2016/10/19
 * by ming
 */
package com.ichunming.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {
	
	// private constructor
	private JsonUtil() {}
	
	/**
	 * convert object to json
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	/**
	 * convert json to object
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clz);
	}
	
	/**
	 * convert json's member to object list
	 * @param json
	 * @param memberName
	 * @param clz
	 * @return
	 */
	public static <T> List<T> fromJson(String json, String memberName, Class<T> clz) {
		T obj = null;
		List<T> result = new ArrayList<T>();
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray jsonArray = jsonObject.getAsJsonArray(memberName);
		for(int i = 0; i < jsonArray.size(); i++) {
			JsonElement je = jsonArray.get(i);
			obj = gson.fromJson(je, clz);
			result.add(obj);
		}
		
		return result;
	}
}
