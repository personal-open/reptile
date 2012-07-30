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
	public String html;
	
	/**
	 * 构造函数
	 * @param _html String 抓取后的html原文
	 */
	public ImageParse(String _html){
		this.html = _html;
	}
	
	/**
	 * 图片解析函数
	 * @param html String html原文
	 */
	public void parseImageUrl(String html){
		Document doc = Jsoup.parse(html);
		Elements imgs  = doc.getElementsByTag("img");
		for (Element png : imgs) {
			System.out.println(png.attr("src") + " : " + png.attr("alt"));
		}
	}

	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Document doc = super.getDocument(html);
		Elements imgs  = doc.getElementsByTag("img");
		for (Element png : imgs) {
			String srcUrl = png.attr("src");
			String alt = png.attr("alt"); 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("URLIMAGE",srcUrl);
			System.out.println("向入库queue中放入消息"+png.attr("src") + " : " + png.attr("alt"));
			DataQueue.addQueueMessage(map);
		}
	}
	
	
}
