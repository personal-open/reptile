package com.subject.reptile.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 抽象html解析函数
 * @author ldl
 * @version 1.0
 * 2012-07-17
 */
public abstract class AbstractParse extends Thread{

	/**
	 * 返回Jsoup Document对象
	 * @param html String html原文
	 * @return Document
	 */
	protected Document getDocument(String html){
		Document doc = Jsoup.parse(html);
		return doc;
	}

	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
	
	
}
