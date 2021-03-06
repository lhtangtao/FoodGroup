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

public class AttentionUsersTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String requestFoodUserId = null;
	private static final Logger log = Logger.getLogger(AttentionUsersTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"获取关注列表", requestFoodUserId, "Attention", 1, 1, "Success" },
				{"获取粉丝列表", requestFoodUserId, "Fans", 1, 1, "Success" } };
	}
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"输入不存在的用户ID", deviceNumber, foodUserId, deviceType, "wrong", "Attention", 1, 1, "出现错误。" },
				{"用户ID不输入", deviceNumber, foodUserId, deviceType, "", "Attention", 1, 1, "出现错误。" },
				{"操作类型不输入", deviceNumber, foodUserId, deviceType, requestFoodUserId, "", 1, 1, "出现错误。" },
				{"DeviceNumber不输入", "", foodUserId, deviceType, requestFoodUserId, "Attention", 1, 1, "出现错误。" },
				{"FoodUserId不输入", deviceNumber, "", deviceType, requestFoodUserId, "Attention", 1, 1, "出现错误。" },
				{"DeviceType不输入", deviceNumber, foodUserId, "", requestFoodUserId, "Attention", 1, 1, "出现错误。" }
				
		};
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("关注用户接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		requestFoodUserId = PropertiesHandle.readValue("RequestFoodUserId");
		actionUrl = "/api/FoodPostUserInfo/AttentionUsers";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("关注用户接口测试开始！");
	}
	
	@Test(dataProvider = "getData")
	public void activateCommentTest(String testName, String requestFoodUserId, String attentionType, int pageNumber, int pageSize,
			String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("RequestFoodUserId", requestFoodUserId);
		jsonParam.put("AttentionType", attentionType);
		jsonParam.put("PageNumber", pageNumber);
		jsonParam.put("PageSize", pageSize);
		
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
	
	@Test(dataProvider = "getData_unnormal")
	public void noRequiredTest(String testName, String deviceNumber, String foodUserId, String deviceType, String requestFoodUserId,
			String attentionType, int pageNumber, int pageSize, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("RequestFoodUserId", requestFoodUserId);
		jsonParam.put("AttentionType", attentionType);
		jsonParam.put("PageNumber", pageNumber);
		jsonParam.put("PageSize", pageSize);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
	
}
