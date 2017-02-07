package com.uxian.foodgroup.FoodPost;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uxian.foodgroup.util.JsonUtil;
import com.uxian.foodgroup.util.PropertiesHandle;
import com.uxian.foodgroup.util.RequestUtil;

import net.sf.json.JSONObject;

public class HotConstituteItemsTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String foodPostId = null;
	private static final Logger log = Logger.getLogger(HotConstituteItemsTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"查看存在的帖子的1个热度来源", 1, 1, "Success", 1 }, {"查看存在的帖子的5个热度来源", 1, 5, "Success", 5 } };
	}
	
	@DataProvider(name = "getData_noRequired")
	private Object[][] getData_noRequired() {
		return new Object[][] {{"FoodPostID为空", "", 1, 5, "出现错误。" }, {"PageNumber为空", "", 1, 5, "出现错误。" }, {"PageSize为空", "", 1, 5, "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("热度来源列表接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		foodPostId = PropertiesHandle.readValue("FoodPostID");
		actionUrl = "/api/FoodPost/HotConstituteItems";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("热度来源列表接口测试结束！");
	}
	
	@Test(dataProvider = "getData")
	public void hotConstituteItems(String testName, int pageNumber, int pageSize, String expectedRes, int expectedCount) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("FoodPostID", foodPostId);
		jsonParam.put("PageNumber", pageNumber);
		jsonParam.put("PageSize", pageSize);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
		Map<String, Object> mapOne = (Map<String, Object>)JsonUtil.subJson2Map(resultData);
		JSONObject jsonObject1 = JSONObject.fromObject(mapOne);
		String jsonString1 = jsonObject1.toString();
		Map<String, Object> mapTwo = (Map<String, Object>)JsonUtil.subJson2Map(jsonString1);
		JSONObject jsonObject2 = JSONObject.fromObject(mapTwo);
		String jsonString2 = jsonObject2.toString();
		List<Map<String, Object>> list = (List<Map<String, Object>>)JsonUtil.multiSubJson2Map(jsonString2);
		log.info("list列表长度" + list.size());
		Assert.assertEquals(list.size(), expectedCount);
	}
}
