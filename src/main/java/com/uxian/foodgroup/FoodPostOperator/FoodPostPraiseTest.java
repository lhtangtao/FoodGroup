package com.uxian.foodgroup.FoodPostOperator;

import java.io.UnsupportedEncodingException;
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

public class FoodPostPraiseTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String FoodPostID = null;
	private String FoodPostID2 = null;
	private static final Logger log = Logger.getLogger(FoodPostPraiseTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"输入存在的ID", FoodPostID, "Success", "凑数的" }, {"输入已经被点赞的帖子ID", FoodPostID2, "Failed", "Praise" },
				{"输入已被删除的ID", "0050A100-2C53-49E8-BDB8-50C1C16B1B8F", "Failed", "NoFoodPost" } };
	}
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"输入不存在的帖子ID", deviceNumber, foodUserId, deviceType, "wrong", "出现错误。" },
				{"帖子ID不输入", deviceNumber, foodUserId, deviceType, "", "出现错误。" },
				{"DeviceNumber不输入", "", foodUserId, deviceType, FoodPostID, "出现错误。" },
				{"FoodUserId不输入", deviceNumber, "", deviceType, FoodPostID, "出现错误。" },
				{"DeviceType不输入", deviceNumber, foodUserId, "", FoodPostID, "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("帖子点赞接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		FoodPostID = PropertiesHandle.readValue("FoodPostID");
		actionUrl = "/api/FoodPostOperator/FoodPostPraise";
		FoodPostID2 = PropertiesHandle.readValue("FoodPostID2");
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("帖子点赞接口测试开始！");
	}
	
	@Test(dataProvider = "getData")
	public void activateCommentTest(String testName, String foodPostID, String expectedRes, String expFaildResult) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("FoodPostID", foodPostID);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
		if(res.equals("Success")) {
			int resfaildResult = (int)map.get("faildResult");
			Assert.assertEquals(resfaildResult, 0);
		}
		else
			if(res.equals("Failed")) {
				String resfaildResult = (String)map.get("faildResult");
				Assert.assertEquals(resfaildResult, expFaildResult);
			}
	}
	
	@Test(dataProvider = "getData_unnormal")
	public void noRequiredTest(String testName, String deviceNumber, String foodUserId, String deviceType, String FoodPostID, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("FoodPostID", FoodPostID);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
}
