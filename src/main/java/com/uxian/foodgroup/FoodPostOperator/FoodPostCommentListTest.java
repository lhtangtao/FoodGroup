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

public class FoodPostCommentListTest {
	private String baseUrl = null;
	private String actionUrl = null;
	
	private String FoodPostID = null;
	
	private static final Logger log = Logger.getLogger(FoodPostCommentListTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"获取帖子评论列表", FoodPostID, 1, 1, "Success" } };
	}
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"输入不存在的帖子ID", "wrong", 1, 1, "出现错误。" }, {"帖子ID不输入", "", 1, 1, "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("帖子评论列表接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		
		FoodPostID = PropertiesHandle.readValue("FoodPostID");
		actionUrl = "/api/FoodPostOperator/FoodPostCommentList";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("帖子评论列表接口测试开始！");
	}
	
	@Test(dataProvider = "getData")
	public void activateCommentTest(String testName, String FoodPostID, int pageNumber, int pageSize, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		
		jsonParam.put("FoodPostID", FoodPostID);
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
	public void noRequiredTest(String testName, String FoodPostID, int pageNumber, int pageSize, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		
		jsonParam.put("FoodPostID", FoodPostID);
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
