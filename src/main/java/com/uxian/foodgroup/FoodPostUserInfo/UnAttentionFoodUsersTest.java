package com.uxian.foodgroup.FoodPostUserInfo;

import java.io.UnsupportedEncodingException;
import java.util.List;
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

public class UnAttentionFoodUsersTest {
	private String baseUrl = null;
	private String actionUrl = null;
	private String deviceNumber = null;
	private String foodUserId = null;
	private String deviceType = null;
	private static final Logger log = Logger.getLogger(UnAttentionFoodUsersTest.class);
	
	@DataProvider(name = "getData")
	private Object[][] getData() {
		return new Object[][] {{"获取未关注好友列表", 1, 5, "Success", 5 } };
		
	}
	
	@BeforeClass
	public void setUp() throws UnsupportedEncodingException {
		log.info("待关注的好友列表接口测试开始！");
		baseUrl = PropertiesHandle.readValue("baseUrl");
		deviceNumber = PropertiesHandle.readValue("DeviceNumber");
		foodUserId = PropertiesHandle.readValue("FoodUserId");
		deviceType = PropertiesHandle.readValue("DeviceType");
		actionUrl = "/api/FoodPostUserInfo/UnAttentionFoodUsers";
	}
	
	@AfterClass
	public void tearDown() {
		log.info("待关注的好友列表接口接口测试结束！");
	}
	
	@Test(dataProvider = "getData")
	public void unAttentionFoodUsersTest(String testName, int pageNumber, int pageSize, String expectedRes, int expectedCount) {
		log.info("测试名:" + testName);
		String url = baseUrl + actionUrl;
		url = url.replaceAll("\r", "");
		url = url.replaceAll("\n", "");
		log.info("url:" + url);
		JSONObject jsonParam = new JSONObject();
		// String jsonMessage =
		// "[{\"UserName\":\"李路冉\",\"MobilePhoneNumber\":\"18537629559\"},{\"UserName\":\"宋广志\",\"MobilePhoneNumber\":\"18501070002\"},{\"UserName\":\"军哥\",\"MobilePhoneNumber\":\"13803833466\"},{\"UserName\":\"肖宇\",\"MobilePhoneNumber\":\"18668239923\"},{\"UserName\":\"大姐\",\"MobilePhoneNumber\":\"13581971938\"},{\"UserName\":\"远哥\",\"MobilePhoneNumber\":\"15968803036\"},{\"UserName\":\"汪源\",\"MobilePhoneNumber\":\"18236253632\"},{\"UserName\":\"二姐\",\"MobilePhoneNumber\":\"18958063077\"},{\"UserName\":\"程辰\",\"MobilePhoneNumber\":\"15558188080\"},{\"UserName\":\"郝老三\",\"MobilePhoneNumber\":\"13173661471\"},{\"UserName\":\"二爹\",\"MobilePhoneNumber\":\"13575724866\"},{\"UserName\":\"房东\",\"MobilePhoneNumber\":\"13606700954\"},{\"UserName\":\"叶子\",\"MobilePhoneNumber\":\"15939017021\"},{\"UserName\":\"骚恒\",\"MobilePhoneNumber\":\"13613828270\"},{\"UserName\":\"团结老姑父\",\"MobilePhoneNumber\":\"13537746588\"},{\"UserName\":\"孙鹏\",\"MobilePhoneNumber\":\"13103822855\"},{\"UserName\":\"宝贝\",\"MobilePhoneNumber\":\"13733815242\"},{\"UserName\":\"辉哥\",\"MobilePhoneNumber\":\"18638616155\"},{\"UserName\":\"康宁\",\"MobilePhoneNumber\":\"15936272104\"},{\"UserName\":\"程先生，金毛\",\"MobilePhoneNumber\":\"13675881915\"},{\"UserName\":\"张宇\",\"MobilePhoneNumber\":\"18103760090\"},{\"UserName\":\"胡余涛\",\"MobilePhoneNumber\":\"18697909403\"},{\"UserName\":\"威少\",\"MobilePhoneNumber\":\"15981952742\"},{\"UserName\":\"琼琼\",\"MobilePhoneNumber\":\"13673612984\"},{\"UserName\":\"老妈\",\"MobilePhoneNumber\":\"13526871763\"},{\"UserName\":\"老爸\",\"MobilePhoneNumber\":\"13523716298\"},{\"UserName\":\"杨\",\"MobilePhoneNumber\":\"18768156108\"},{\"UserName\":\"珠妈\",\"MobilePhoneNumber\":\"13523422863\"},{\"UserName\":\"刘星峰\",\"MobilePhoneNumber\":\"13106858777\"},{\"UserName\":\"汤教练\",\"MobilePhoneNumber\":\"13967150017\"}]";
		
		// jsonParam.put("AddressBookJsonValue", "\"" + jsonMessage + "\"");
		jsonParam.put("DeviceNumber", deviceNumber);
		jsonParam.put("FoodUserId", foodUserId);
		jsonParam.put("DeviceType", deviceType);
		jsonParam.put("PageNumber", pageNumber);
		jsonParam.put("PageSize", pageSize);
		log.info(jsonParam);
		String resultData = RequestUtil.postRequest(url, jsonParam);
		log.info("响应数据:" + resultData);
		Map<String, Object> map = JsonUtil.Json2Map(resultData);
		
		String res = (String)map.get("result");
		Assert.assertEquals(res, expectedRes);
		Map<String, Object> mapOne = (Map<String, Object>)JsonUtil.subJson2Map(resultData);
		JSONObject jsonObject1 = JSONObject.fromObject(mapOne);
		String jsonString1 = jsonObject1.toString();
		Map<String, Object> mapTwo = (Map<String, Object>)JsonUtil.subJson2Map(jsonString1);
		JSONObject jsonObject2 = JSONObject.fromObject(mapTwo);
		String jsonString2 = jsonObject2.toString();
		List<Map<String, Object>> list = (List<Map<String, Object>>)JsonUtil.multiSubJson2Map(jsonString2);
		// log.info("list列表长度" + list.size());
		Assert.assertEquals(list.size(), expectedCount);
		
	}
}
