package com.uxian.foodgroup.FoodPostOperator;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uxian.foodgroup.FoodPost.HotConstituteItemsTest;
import com.uxian.foodgroup.util.JsonUtil;
import com.uxian.foodgroup.util.PropertiesHandle;
import com.uxian.foodgroup.util.RequestUtil;

import net.sf.json.JSONObject;

public class FoodpostShareTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String foodPostId = null;
	private static final Logger log = Logger.getLogger(HotConstituteItemsTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"进行帖子分享", "Success", } };
		
	}
	
	@DataProvider(name = "getData_noRequired")
	private Object[][] getData_noRequired() {
		return new Object[][] {{"FoodPostID为空", "", 1, 5, "出现错误。" }, {"PageNumber为空", "", 1, 5, "出现错误。" }, {"PageSize为空", "", 1, 5, "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("帖子分享接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		foodPostId = PropertiesHandle.readValue("FoodPostID");
		actionUrl = "/api/FoodPostOperator/FoodpostShare";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("帖子分享接口测试结束！");
	}
	
	@Test(dataProvider = "getData")
	public void foodpostShareTest(String testName, String expectedRes) {
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
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
		
	}
}
