package com.uxian.foodgroup.City;

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

public class CityListTest {
	private String baseUrl = null;
	private String actionUrl = null;
	
	private static final Logger log = Logger.getLogger(CityListTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"获取所有城市列表", "ALL", "Success" }, {"获取已入住城市列表", "Enter", "Success" } };
	}
	
	@DataProvider(name = "getData_unnormal")
	private Object[][] getData_unnormal() {
		return new Object[][] {{"SelectType不输入", "", "出现错误。" } };
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("获取城市列表接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		
		actionUrl = "/api/City/CityList";
		
	}
	
	@AfterClass
	public void tearDown() {
		log.info("获取城市列表接口测试开始！");
	}
	
	@Test(dataProvider = "getData")
	public void cityListTest(String testName, String SelectType, String expectedRes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		
		jsonParam.put("SelectType", SelectType);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
	}
	
	@Test(dataProvider = "getData_unnormal")
	public void noRequiredTest(String testName, String SelectType, String expectedMes) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		
		jsonParam.put("SelectType", SelectType);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		String mes = (String)map.get("message");
		Assert.assertEquals(mes, expectedMes);
	}
}
