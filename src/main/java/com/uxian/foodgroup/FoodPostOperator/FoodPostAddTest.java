package com.uxian.foodgroup.FoodPostOperator;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uxian.foodgroup.MyBatisMapper.FoodPostIDMapper;
import com.uxian.foodgroup.util.ImageToString;
import com.uxian.foodgroup.util.JsonUtil;
import com.uxian.foodgroup.util.MyBatis;
import com.uxian.foodgroup.util.PropertiesHandle;
import com.uxian.foodgroup.util.RequestUtil;

import net.sf.json.JSONObject;

public class FoodPostAddTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String FoodPostImage = null;
	private SqlSession sqlSession = null;
	private static final Logger log = Logger.getLogger(FoodPostAddTest.class);
	
	/*
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"输入不存在的帖子ID", deviceNumber, foodUserId, deviceType, "wrong", "出现错误。" },
				{"帖子ID不输入", deviceNumber, foodUserId, deviceType, "", "出现错误。" },
				{"DeviceNumber不输入", "", foodUserId, deviceType, FoodPostIDdelete, "出现错误。" },
				{"FoodUserId不输入", deviceNumber, "", deviceType, FoodPostIDdelete, "出现错误。" },
				{"DeviceType不输入", deviceNumber, foodUserId, "", FoodPostIDdelete, "出现错误。" } };
	}
	*/
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"进行发帖测试", "iPhone 5c", "发帖接口测试", FoodPostImage, 105, 5, 5, "Success" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("发帖接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		FoodPostImage = ImageToString.ImageString(PropertiesHandle.readValue("KaImageUrl"));
		actionUrl = "/api/FoodPostOperator/FoodPostAdd";
		sqlSession = MyBatis.getSessionFactory("VAGourmet0").openSession();
	}
	
	@AfterClass
	public void tearDown() {
		log.info("发帖接口测试开始！");
	}
	
	/*
	@Test(dataProvider = "getData_unnormal")
	public void noRequiredTest(String testName, String deviceNumber, String foodUserId, String deviceType, String UserPhoneType, String Content,
			String FoodPostImage, int FoodPostCityID, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("UserPhoneType", UserPhoneType);
		jsonParam.put("Content", Content);
		jsonParam.put("FoodPostImage", FoodPostImage);
		jsonParam.put("FoodPostCityID", FoodPostCityID);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
	*/
	@Test(dataProvider = "getData")
	public void foodPostAddTest(String testName, String UserPhoneType, String Content, String FoodPostImage, int FoodPostCityID, int RatioScore,
			int TasteScore, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("UserPhoneType", UserPhoneType);
		jsonParam.put("Content", Content);
		jsonParam.put("FoodPostImage", FoodPostImage);
		jsonParam.put("FoodPostCityID", FoodPostCityID);
		jsonParam.put("RatioScore", RatioScore);
		jsonParam.put("TasteScore", TasteScore);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
		FoodPostIDMapper foodPostIDMapper = sqlSession.getMapper(FoodPostIDMapper.class);
		String dbFoodPostID = foodPostIDMapper.selectIDByContent();
		Map<String, Object> map2 = JsonUtil.subJson2Map(resultData);
		String resFoodPostId = (String)map2.get("foodPostId");
		log.info(resFoodPostId.toUpperCase());
		Assert.assertEquals(resFoodPostId.toUpperCase(), dbFoodPostID);
		
	}
}
