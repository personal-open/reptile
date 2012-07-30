package com.subject.reptile.common;

import java.io.InputStream;
import java.util.Properties;

import com.subject.reptile.metadata.Constrants;

/**
 * 属性文件帮助函数
 * @author ldl
 * @version 1.0
 * 2012-07-23
 */
public class PropertiesHelper {

	/**
	 * 属性
	 */
	private static Properties props = new Properties();
	
	
	static {
		InputStream in = PropertiesHelper.class.getResourceAsStream(Constrants.PROPERTIES_PATH);
		try{
			props.load(in);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 根据key值获取value
	 * @param key String
	 * @return String
	 */
	public static String getPorperties(String key){
		return props.getProperty(key);
	}
	
}
