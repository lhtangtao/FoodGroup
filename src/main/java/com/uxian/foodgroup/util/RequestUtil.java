package com.uxian.foodgroup.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

/**
 * @author iversoncl
 * @Date 2015年4月11日
 * @Project HiHealthTest
 */
public class RequestUtil {
	
	private static final Logger log = Logger.getLogger(RequestUtil.class);
	private static final String CONTENT_CHARSET = "UTF-8";
	
	/**
	 * @方法名：httpGet
	 * @描述：发送get请求
	 * @param url
	 * @return
	 * @输出：String
	 * @作者：fujiani
	 */
	@SuppressWarnings("deprecation")
	public static String httpGet(String url) {
		HttpClient hc = new DefaultHttpClient();
		HttpGet hg = new HttpGet(url);
		try {
			HttpResponse response = hc.execute(hg);
			HttpEntity entity = response.getEntity();
			String result = null;
			if(entity != null) {
				result = EntityUtils.toString(entity);
			}
			return result;
		}
		catch(ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hc.getConnectionManager().shutdown();
		return null;
	}
	
	/**
	 * @方法名：httpGet
	 * @描述：发送get请求,带cookie
	 * @param url
	 * @param cookies
	 * @return
	 * @输出：String
	 * @作者：fujiani
	 */
	public static String httpGet(String url, CookieStore cookies) {
		HttpClient hc = new DefaultHttpClient();
		HttpGet hg = new HttpGet(url);
		((AbstractHttpClient)hc).setCookieStore(cookies);
		try {
			HttpResponse response = hc.execute(hg);
			HttpEntity entity = response.getEntity();
			String result = null;
			if(entity != null) {
				result = EntityUtils.toString(entity);
			}
			return result;
		}
		catch(ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hc.getConnectionManager().shutdown();
		return null;
	}
	
	/**
	 * @方法名：postRequest
	 * @描述：获得cookie
	 * @param url
	 * @param jsonParam
	 * @return
	 * @输出：String
	 * @作者：fujiani
	 */
	/*
		public static CookieStore getCookies() {
			try {
				HttpClient hc = new DefaultHttpClient();
				String baseUrl = ProperstiesHandle.readValue("baseUrl");
				String deviceid = Base64Codec.Base64Encode(PropertiesHandle
						.readValue("deviceid"));
				String behaviorInfo = Base64Codec.Base64Encode(PropertiesHandle
						.readValue("behaviorInfo"));
				String url = baseUrl
						+ "/CloudHealth/mobile/common/user/login!thirdPartyLogin.action"
						+ "?deviceid=" + deviceid + "&behaviorInfo=" + behaviorInfo
						+ "&uid=iversoncl" + "&sourceType=1";
				url = url.replaceAll("\r", "");
				url = url.replaceAll("\n", "");
				HttpGet hg = new HttpGet(url);
				HttpResponse response = hc.execute(hg);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					log.info(EntityUtils.toString(entity));
				}
				CookieStore cookies = new BasicCookieStore();
				cookies = ((AbstractHttpClient) hc).getCookieStore();
				hc.getConnectionManager().shutdown();
				return cookies;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			return null;
		}
	*/
	/**
	 * @方法名：postRequest
	 * @描述：post请求，传入json参数
	 * @param url
	 * @param jsonParam
	 * @return
	 * @输出：String
	 * @作者：fujiani
	 */
	public static String postRequest(String url, JSONObject jsonParam) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(url);
		// 创建参数队列
		
		try {
			
			StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);
			// getEntity()
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = null;
			if(httpEntity != null) {
				result = EntityUtils.toString(httpEntity, "UTF-8");
			}
			// 释放资源
			closeableHttpClient.close();
			return result;
		}
		catch(ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void getRequest(String url) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		
		HttpGet httpGet = new HttpGet(url);
		System.out.println(httpGet.getRequestLine());
		try {
			// 执行get请求
			HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// 响应状态
			System.out.println("status:" + httpResponse.getStatusLine());
			// 判断响应实体是否为空
			if(entity != null) {
				System.out.println("contentEncoding:" + entity.getContentEncoding());
				System.out.println("response content:" + EntityUtils.toString(entity));
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				// 关闭流并释放资源
				closeableHttpClient.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
