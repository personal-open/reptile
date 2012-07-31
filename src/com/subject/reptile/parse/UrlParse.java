package com.subject.reptile.parse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(UrlParse.class);
	
	/**
	 * html原文
	 */
	private String html;
	
	/**
	 * url连接
	 */
	private String url;
	
	/**
	 * 构造函数
	 * @param _html String html原文
	 * @param _url String url连接
	 */
	public UrlParse(String _html,String _url){
		this.html = _html;
		this.url = _url;
	}
	
	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Document doc = Jsoup.parse(html,url);
		Elements links = doc.getElementsByTag("a");
		for (Element link  : links) {
			//String linkHerf = link.attr("abs:href");
			String linkHerf = link.absUrl("href");
			String linkText = link.text();
			//log.info("向 url队列中放入消息   "+linkHerf  + "   " + linkText);
			BdbQueue.addMessage(linkHerf);
		}
	}
	
	
	
	
	
	
}
