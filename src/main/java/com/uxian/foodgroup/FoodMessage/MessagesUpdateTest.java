package com.uxian.foodgroup.FoodMessage;

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

public class MessagesUpdateTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String MessageId = null;
	private static final Logger log = Logger.getLogger(MessagesUpdateTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"输入存在的消息ID", MessageId, "Success" } };
	}
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"输入不存在的消息ID", deviceNumber, foodUserId, deviceType, "wrong", "出现错误。" },
				{"消息ID不输入", deviceNumber, foodUserId, deviceType, "", "出现错误。" }, {"DeviceNumber不输入", "", foodUserId, deviceType, MessageId, "出现错误。" },
				{"FoodUserId不输入", deviceNumber, "", deviceType, MessageId, "出现错误。" },
				{"DeviceType不输入", deviceNumber, foodUserId, "", MessageId, "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("更新消息状态--是否查看接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		MessageId = PropertiesHandle.readValue("MessageId");
		actionUrl = "/api/FoodMessage/FoodPostMessagesUpdate";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("更新消息状态--是否查看接口测试开始！");
	}
	
	@Test(dataProvider = "getData")
	public void messagesUpdateTest(String testName, String MessageId, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("MessageId", MessageId);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
	
	@Test(dataProvider = "getData_unnormal")
	public void noRequiredTest(String testName, String deviceNumber, String foodUserId, String deviceType, String MessageId, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("MessageId", MessageId);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
}
