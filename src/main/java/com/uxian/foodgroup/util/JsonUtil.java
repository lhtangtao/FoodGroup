package com.uxian.foodgroup.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @包名：com.uxian.foodgroup.util
 * @类名：JsonUtil
 * @描述：(描述这个类的作用) @作者：fujiani
 * @时间：2015年10月27日上午10:21:56 @版本：1.0.0
 */
public class JsonUtil {
	
	/**
	 * @方法名：Json2Map
	 * @描述：Json转化Map类型
	 * @param jsonStr
	 * @return
	 * @输出：Map<String,Object>
	 * @作者：fujiani
	 */
	@SuppressWarnings({"unchecked" })
	public static Map<String, Object> Json2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		for(Object k : json.keySet()) {
			Object v = json.get(k);
			if(v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray)v).iterator();
				while(it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(Json2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			}
			else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	
	/**
	 * @方法名：subJson2Map
	 * @描述：返回json的嵌套中只有一个json
	 * @param jsonStr
	 * @return
	 * @输出：Map<String,Object>
	 * @作者：fujiani
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> subJson2Map(String jsonStr) {
		Map<String, Object> map = JSONObject.fromObject(jsonStr);
		Map<String, Object> subMap = new HashMap<String, Object>();
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Object> entry = it.next();
			// System.out.println("key= " + entry.getKey() + " and value= "
			// + entry.getValue());
			if(entry.getValue() instanceof JSONObject) {
				subMap = JsonUtil.Json2Map(entry.getValue().toString());
			}
		}
		return subMap;
	}
	
	/**
	 * @方法名：subsJson2Map
	 * @描述：返回json的嵌套中的多个json对象
	 * @param jsonStr
	 * @return
	 * @输出：List<Map<String,Object>>
	 * @作者：fujiani
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> subsJson2Map(String jsonStr) {
		Map<String, Object> map = JSONObject.fromObject(jsonStr);
		List<Map<String, Object>> subMapList = new ArrayList<Map<String, Object>>();
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Object> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			if(entry.getValue() instanceof JSONObject) {
				subMapList.add(JsonUtil.Json2Map(entry.getValue().toString()));
			}
		}
		return subMapList;
	}
	
	/**
	 * @方法名：multiSubJson2Map
	 * @描述：json的值为jsonarray的话，返回该jsonarray
	 * @param jsonStr
	 * @return
	 * @输出：List<Map<String,Object>>
	 * @作者：fujiani
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> multiSubJson2Map(String jsonStr) {
		Map<String, Object> map = JSONObject.fromObject(jsonStr);
		List<Map<String, Object>> subMapList = new ArrayList<Map<String, Object>>();
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Object> entry = it.next();
			// System.out.println("key= " + entry.getKey() + " and value= "
			// + entry.getValue());
			if(entry.getValue() instanceof JSONArray) {
				JSONArray ay = JSONArray.fromObject(entry.getValue());
				for(int i = 0; i < ay.size(); i++) {
					subMapList.add(JsonUtil.Json2Map(ay.getString(i)));
				}
			}
		}
		return subMapList;
	}
	
}
