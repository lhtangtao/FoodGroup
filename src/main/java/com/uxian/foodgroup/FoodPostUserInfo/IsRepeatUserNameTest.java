package com.uxian.foodgroup.FoodPostUserInfo;

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

public class IsRepeatUserNameTest {
	private String baseUrl = null;
	private String foodUserNameExist = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private static final Logger log = Logger.getLogger(IsRepeatUserNameTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"未使用的昵称", "notest", "Success", false }, {"已使用的昵称", foodUserNameExist, "Success", true }, };
		
	}
	
	@DataProvider(name = "getData_noRequired")
	private Object[][] getData_noRequired() {
		return new Object[][] {{"昵称为空", deviceNumber, foodUserId, deviceType, "", "出现错误。" },
				{"DeviceNumber不输入", "", foodUserId, deviceType, foodUserNameExist, "出现错误。" },
				{"FoodUserId不输入", deviceNumber, "", deviceType, foodUserNameExist, "出现错误。" },
				{"DeviceType不输入", deviceNumber, foodUserId, "", foodUserNameExist, "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("昵称重复性判断接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		foodUserNameExist = PropertiesHandle.readValue("FoodUserNameExist");
		actionUrl = "/api/FoodPostUserInfo/IsRepeatUserName";
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
	}
	
	@AfterClass
	public void tearDown() {
		log.info("昵称重复性判断接口测试结束！");
	}
	
	@Test(dataProvider = "getData")
	public void isRepeatTest(String testName, String foodUserName, String expectedRes, boolean expected) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("FoodUserName", foodUserName);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> mapOne = JsonUtil.Json2Map(resultData);
		Map<String, Object> mapTwo = JsonUtil.subJson2Map(resultData);
		String res = (String)mapOne.get("result");
		boolean isrepeat = Boolean.parseBoolean(mapTwo.get("isRepeat").toString());
		Assert.assertEquals(res, expectedRes);
		Assert.assertEquals(isrepeat, expected);
	}
	
	@Test(dataProvider = "getData_noRequired")
	public void noRequired(String testName, String deviceNumber, String foodUserId, String deviceType, String foodUserName, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("FoodUserName", foodUserName);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> mapOne = JsonUtil.Json2Map(resultData);
		String mes = (String)mapOne.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
	
}
