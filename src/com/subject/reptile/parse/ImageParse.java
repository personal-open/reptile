package com.subject.reptile.parse;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.subject.reptile.queue.jdkmq.DataQueue;

/**
 * 图片url解析
 * @author ldl
 * @version 1.0
 * 2012-07-17
 */
public class ImageParse extends AbstractParse{

	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(ImageParse.class);
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
	 * @param _html String 抓取后的html原文
	 * @param _url String url连接
	 */
	public ImageParse(String _html,String _url){
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
		Elements imgs  = doc.getElementsByTag("img");
		for (Element png : imgs) {
			String srcUrl = png.attr("abs:src");
			String alt = png.attr("alt"); 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("URLIMAGE",srcUrl);
			log.info("向入库queue中放入入库信息  "+srcUrl + " : " + alt);
			//DataQueue.addQueueMessage(map);
		}
	}
	
	
}
