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
import com.uxian.foodgroup.util.JsonUtil;
import com.uxian.foodgroup.util.MyBatis;
import com.uxian.foodgroup.util.PropertiesHandle;
import com.uxian.foodgroup.util.RequestUtil;

import net.sf.json.JSONObject;

public class FoodPostDelTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String FoodPostID = null;
	private FoodPostIDMapper foodPostIDMapper;
	private SqlSession sqlSession = null;
	private static final Logger log = Logger.getLogger(FoodPostDelTest.class);
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"输入不存在的帖子ID", deviceNumber, foodUserId, deviceType, "wrong", "出现错误。" },
				{"帖子ID不输入", deviceNumber, foodUserId, deviceType, "", "出现错误。" },
				{"DeviceNumber不输入", "", foodUserId, deviceType, FoodPostID, "出现错误。" },
				{"FoodUserId不输入", deviceNumber, "", deviceType, FoodPostID, "出现错误。" },
				{"DeviceType不输入", deviceNumber, foodUserId, "", FoodPostID, "出现错误。" } };
	}
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"输入存在的帖子ID", FoodPostID, "Success" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("帖子删除接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		actionUrl = "/api/FoodPostOperator/FoodPostDel";
		sqlSession = MyBatis.getSessionFactory("VAGourmet0").openSession();
		foodPostIDMapper = sqlSession.getMapper(FoodPostIDMapper.class);
		FoodPostID = foodPostIDMapper.selectIDByContent();
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("帖子删除接口测试开始！");
	}
	
	@Test(dataProvider = "getData_unnormal", priority = 1)
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
	
	@Test(dataProvider = "getData", priority = 2)
	public void activateCommentTest(String testName, String FoodPostID, String expectedRes) {
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
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
}
