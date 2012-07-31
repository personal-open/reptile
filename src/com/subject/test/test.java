package com.subject.test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.subject.reptile.urlfilter.BloomFilter;

public class test {

	
	public static void main(String[] args) {
		
//		for (int i = 0; i < 1000; i++) {
//			BdbQueue.addMessage("消息"+i);
//		}
//		
//		while(BdbQueue.queueIsEmpety()){
//			System.out.println(BdbQueue.getMessage());
//		}
		
		
		//setQueue();
		
		
		
	 //   System.out.println(getDomain("http://www.baidu.com"));
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("http://nanjing.mallog.com.cn", "http://nanjing.mallog.com.cn");
		
		String s = "http://nanjing.mallog.com.cn";
		
		
		
	}
	
	public static void setQueue(){
		
		BloomFilter bf = new BloomFilter();
		bf.aadUrl("http://www.baidu.com");
		bf.aadUrl("http://www.google.com");
		
		String url = "http://www.baidu.com/hr";
		System.out.println(bf.contains(url));
		
	}
	/**
	 * 验证域名合法性
	 * @param strHomePage
	 * @return
	 */
	public static String getDomain(String strHomePage) {
		String reg = "(?<=http\\://[a-zA-Z0-9]{0,100}[.]{0,1})[^.\\s]*?\\.(com|cn|net|org|biz|info|cc|tv)";
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(strHomePage);
		boolean blnp = m.find();
		if (blnp == true) {
			return m.group(0);
		}
		return null;
	}
}
