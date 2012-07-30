package com.subject.reptile.crawl;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 根url缓存操作函数
 * @author ldl
 * @version 1.0
 * 2012-07-30
 */
public class UrlContext {

	/**
	 * 保存根url及抓取深度
	 */
	public static ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<String,Integer>();
	
	
	/**
	 * 添加url
	 * @param urls String 要抓取的url根目录
	 * @param depth int 深度
	 */
	public static void addUrlAndDepth(String [] urls,int depth){
		if(urls.length != 0){
			for (int i = 0; i < urls.length; i++) {
				map.put(urls[i],depth);
			}
		}
	}
	
	/**
	 * 判断抓取到的url是否在根目录下
	 * @param url
	 * @return boolean
	 */
	public static boolean isStartUrl(String url){
		for (String key : map.keySet()) {
			if(url.startsWith(key)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断是否已经包含url
	 * @param url
	 * @return boolean
	 */
	public static boolean isContentUrl(String url){
		if(map.contains(url)){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据Key url 移除map
	 * @param url String
	 */
	public static void removeUrl(String url){
		if(isContentUrl(url)){
			map.remove(url);
		}
	}
	
	/**
	 * 清除所有缓存
	 */
	public static void clear(){
		map.clear();
	}
	
	
}
