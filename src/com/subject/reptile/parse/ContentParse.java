package com.subject.reptile.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 文本内容解析
 * @author ldl
 * @version 1.0
 * 2012-07-17
 */
public class ContentParse extends AbstractParse{

	/**
	 * 抓取后的html原文
	 */
	private String html;
	
	/**
	 * url连接
	 */
	private String url;
	
	/**
	 * 构造函数
	 * @param _html String html原文
	 */
	public ContentParse(String _html,String _url){
		this.html = _html;
		this.url = _url;
	}
	
	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Document doc = super.getDocument(html,url);
		Elements imgs  = doc.getElementsByTag("img");
		for (Element png : imgs) {
			System.out.println(png.attr("src") + " : " + png.attr("alt"));
		}
	}
	
	
	
}
