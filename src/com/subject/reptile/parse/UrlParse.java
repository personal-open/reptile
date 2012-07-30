package com.subject.reptile.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.subject.reptile.queue.dbmq.BdbQueue;

/**
 * url链接解析
 * @author ldl
 * @version 1.0
 * 2012-07-17
 */
public class UrlParse extends AbstractParse{

	
	private String html;
	
	/**
	 * 构造函数
	 * @param _html String html原文
	 */
	public UrlParse(String _html){
		this.html = _html;
	}
	
	/**
	 * url链接解析
	 * @param html String html原文
	 */
	
	public void parseUrl(String html){
		Document doc = Jsoup.parse(html);
		Elements links = doc.getElementsByTag("a");
		for (Element link  : links) {
			String linkHerf = link.attr("href");
			String linkText = link.text();
			System.out.println(linkHerf  + "   " + linkText);
		}
	}

	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Document doc = super.getDocument(html);
		Elements links = doc.getElementsByTag("a");
		for (Element link  : links) {
			String linkHerf = link.attr("href");
			String linkText = link.text();
			System.out.println("向 url队列中放入消息 "+linkHerf  + "   " + linkText);
			BdbQueue.addMessage(linkHerf);
		}
	}
	
	
	
	
	
	
}
