package com.uxian.foodgroup.FoodPostUserInfo;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uxian.foodgroup.util.ImageToString;
import com.uxian.foodgroup.util.JsonUtil;
import com.uxian.foodgroup.util.PropertiesHandle;
import com.uxian.foodgroup.util.RequestUtil;

import net.sf.json.JSONObject;

public class EditUserInfoTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String KaCustomerImage = null;
	private String HTTCustomerImage = null;
	private static final Logger log = Logger.getLogger(EditUserInfoTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"修改昵称1", "UserName", "HTT", "Success" }, {"修改性别1", "CustomerSex", "Female", "Success" },
				{"修改头像1", "CustomerImage", HTTCustomerImage, "Success" }, {"修改常居地1", "HomeCity", "上海市", "Success" },
				{"修改美食签名1", "FoodSignContent", "黄是红绿灯的黄，婷不是亭亭玉立的亭", "Success" }, {"修改昵称2", "UserName", "李一桶最正统", "Success" },
				{"修改性别2", "CustomerSex", "Male", "Success" }, {"修改头像2", "CustomerImage", KaCustomerImage, "Success" },
				{"修改常居地2", "HomeCity", "杭州市", "Success" }, {"修改美食签名2", "FoodSignContent", "李一桶最正统，超绝正统李一桶", "Success" }
				
		};
		
	}
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"昵称为31个中文字符", "UserName", "李一桶最正统李一桶最正统李一桶最正统李一桶最正统李一桶最正统啊", "出现错误。" },
				{"昵称为31个英文字符", "UserName", "huangtingtingyishengtuiliyitong", "出现错误。" },
				{"签名为48个字", "FoodSignContent", "李一桶最正统最正统李一桶李一桶最正统最正统李一桶李一桶最正统最正统李一桶李一桶最正统最正统李一桶啊", "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("编辑美食圈个人信息接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		KaCustomerImage = ImageToString.ImageString(PropertiesHandle.readValue("KaImageUrl"));
		HTTCustomerImage = ImageToString.ImageString(PropertiesHandle.readValue("HTTImageUrl"));
		actionUrl = "/api/FoodPostUserInfo/EditFoodPostUserInfo";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("编辑美食圈个人信息接口测试开始！");
	}
	
	@Test(dataProvider = "getData")
	public void editUserInfoTest(String testName, String key, String value, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put(key, value);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
	
	@Test(dataProvider = "getData_unnormal")
	public void noRequiredTest(String testName, String key, String value, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put(key, value);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
}
