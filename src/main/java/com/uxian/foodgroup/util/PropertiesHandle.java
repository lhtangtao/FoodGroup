package com.uxian.foodgroup.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @包名：com.uxian.foodgroup.util
 * @类名：PropertiesHandle
 * @描述：(描述这个类的作用) @作者：fujiani
 * @时间：2015年10月27日上午10:22:14 @版本：1.0.0
 */
public class PropertiesHandle {
	
	/**
	 * @Description:读取配置文件信息
	 * @param key
	 * @return String
	 * @author: iversoncl
	 * @time:2015年4月12日 下午8:18:28
	 */
	public static String readValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
