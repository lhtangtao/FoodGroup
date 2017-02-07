package com.uxian.foodgroup.FoodMessage;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uxian.foodgroup.MyBatisMapper.MessageIdMapper;
import com.uxian.foodgroup.util.JsonUtil;
import com.uxian.foodgroup.util.MyBatis;
import com.uxian.foodgroup.util.PropertiesHandle;
import com.uxian.foodgroup.util.RequestUtil;

import net.sf.json.JSONObject;

public class PostMessagesDelTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private String FoodPostID = null;
	private String DelMesUserID = null;
	private String MessageId = null;
	private MessageIdMapper MessageIdMapper;
	private SqlSession sqlSession = null;
	
	private static final Logger log = Logger.getLogger(PostMessagesDelTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"输入存在的消息ID", MessageId, "DelById", "User", "Success" } };
	}
	
	@DataProvider(name = "getData2")
	private Object[][] getData2() {
		return new Object[][] {{"清空消息", "DelByUser", "User", "Success" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("消息删除接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		actionUrl = "/api/FoodMessage/FoodPostMessagesDel";
		DelMesUserID = PropertiesHandle.readValue("DelMesUserID");
		FoodPostID = PropertiesHandle.readValue("FoodPostID");
		sqlSession = MyBatis.getSessionFactory("VAGourmet").openSession();
		MessageIdMapper = sqlSession.getMapper(MessageIdMapper.class);
		MessageId = MessageIdMapper.selectID(FoodPostID);
	}
	
	@AfterClass
	public void tearDown() {
		log.info("消息删除接口测试开始！");
	}
	
	@Test(dataProvider = "getData")
	public void postMessagesDelTest(String testName, String MessageId, String DelType, String MessageType, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", DelMesUserID);
		jsonParam.put("DeviceType", deviceType);
		
		jsonParam.put("MessageId", MessageId);
		jsonParam.put("DelType", DelType);
		jsonParam.put("MessageType", MessageType);
		
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
	
	@Test(dataProvider = "getData2")
	public void noRequiredTest(String testName, String DelType, String MessageType, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("DelType", DelType);
		jsonParam.put("MessageType", MessageType);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
}
