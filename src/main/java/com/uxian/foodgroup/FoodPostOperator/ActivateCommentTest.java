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

public class ActivateCommentTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private static final Logger log = Logger.getLogger(ActivateCommentTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"输入正确的激活码", "菠萝菠萝蜜", "Success" }, {"输入错误的激活码", "wrong", "Failed" }, {"输入空的激活码", "", "Failed" } };
		
	}
	
	@DataProvider(name = "getData_noRequired")
	private Object[][] getData_noRequired() {
		return new Object[][] {{"DeviceNumber不输入", "", foodUserId, deviceType, "菠萝菠萝蜜", "出现错误。" },
				{"FoodUserId不输入", deviceNumber, "", deviceType, "菠萝菠萝蜜", "出现错误。" },
				{"DeviceType不输入", deviceNumber, foodUserId, "", "菠萝菠萝蜜", "出现错误。" } };
				
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("评论激活接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		actionUrl = "/api/FoodPostOperator/ActivateComment";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("评论激活接口测试结束！");
	}
	
	@Test(dataProvider = "getData")
	public void activateCommentTest(String testName, String activateCode, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("ActivateCode", activateCode);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
	
	@Test(dataProvider = "getData_noRequired")
	public void noRequiredTest(String testName, String deviceNumber, String foodUserId, String deviceType, String activateCode, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("ActivateCode", activateCode);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
}
