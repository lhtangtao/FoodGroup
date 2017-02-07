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

public class PostSearChByTagTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private static final Logger log = Logger.getLogger(PostSearChByTagTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"通过店铺tag进行搜索", "外婆家", 1, 5, "Success", 5 }, {"通过菜名tag进行搜索", "茶香鸡", 1, 5, "Success", 5 },
				{"输入不存在的店铺或者菜名", "不存在", 1, 5, "Success", 0 }, {"tag为空", "", 1, 5, "Success", 0 } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("根据tag（店铺或菜品）进行帖子搜索接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		actionUrl = "/api/FoodPost/PostSearChByTag";
	}
	
	@AfterClass
	public void tearDown() {
		log.info("根据tag（店铺或菜品）进行帖子搜索接口测试结束！");
	}
	
	@Test(dataProvider = "getData")
	public void postSearChByTagTest(String testName, String condition, int pageNumber, int pageSize, String expectedRes, int expectedCount) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("Condition", condition);
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
		// log.info("list列表长度" + list.size());
		Assert.assertEquals(list.size(), expectedCount);
	}
}
