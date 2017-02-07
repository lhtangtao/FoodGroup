package com.uxian.foodgroup.FoodPostOperator;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uxian.foodgroup.util.DataBaseConnect;
import com.uxian.foodgroup.util.JsonUtil;
import com.uxian.foodgroup.util.PropertiesHandle;
import com.uxian.foodgroup.util.RequestUtil;

import net.sf.json.JSONObject;

public class FoodpostRewardTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String foodPostId = null;
	
	private static final Logger log = Logger.getLogger(FoodpostRewardTest.class);
	
	@DataProvider(name = "getData_normal")
	private Object[][] getData_normal() {
		return new Object[][] {{"打赏金额小于粮票金额", 0.01, 1, "Success", 0 }, {"打赏金额大于粮票金额，且支付方式为支付宝", 9.99, 1, "Failed", "WaitThirdPay" },
				{"打赏金额大于粮票金额，且支付方式为微信支付", 9.99, 4, "Failed", "WaitThirdPay" } };
				
	}
	
	@DataProvider(name = "getData_noPayMode")
	private Object[][] getData_noPayMode() {
		return new Object[][] {{"打赏金额大于粮票金额，且没有选择第三方支付方式", 9.9, "Failed", "NoPayMode" } };
	}
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {
				
				{"打赏金额为0", 0, 1, "出现错误。" }, {"打赏金额为0.009", 0.009, 1, "出现错误。" }, {"打赏金额为9.9901", 9.9901, 1, "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("帖子打赏接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		foodPostId = PropertiesHandle.readValue("FoodPostID");
		actionUrl = "/api/FoodPostOperator/FoodpostReward";
		String sql = "UPDATE [dbo].[CustomerInfo] SET money19dianRemained =9 WHERE mobilePhoneNumber = '18767138129'";
		DataBaseConnect.update(sql);
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("帖子打赏接口测试结束！");
	}
	
	@Test(dataProvider = "getData_normal")
	public void foodpostRewardTest(String testName, double amount, int payModeId, String expectedRes, Object expFailedRes) {
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
		jsonParam.put("Amount", amount);
		jsonParam.put("PayModeId", payModeId);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
		Object failRes = (Object)map.get("faildResult");
		Assert.assertEquals(failRes, expFailedRes);
	}
	
	@Test(dataProvider = "getData_noPayMode")
	public void noPayModeTest(String testName, double amount, String expectedRes, Object expFailedRes) {
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
		jsonParam.put("Amount", amount);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
		Object failRes = (Object)map.get("faildResult");
		Assert.assertEquals(failRes, expFailedRes);
	}
	
	@Test(dataProvider = "getData_unnormal")
	public void getData_unnormalTest(String testName, double amount, int payModeId, String expectedMes) {
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
		jsonParam.put("Amount", amount);
		jsonParam.put("PayModeId", payModeId);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
}
