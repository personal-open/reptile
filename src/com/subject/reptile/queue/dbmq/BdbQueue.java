package com.subject.reptile.queue.dbmq;

import java.util.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.subject.reptile.crawl.UrlContext;
import com.subject.reptile.metadata.Constrants;
import com.subject.reptile.urlfilter.BloomFilter;

/**
 * Bdb队列操作函数
 * @author ldl
 * @version 1.0
 * 2012-7-23
 */
public class BdbQueue {
	
	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(BdbQueue.class);
	
	/**
	 * queue
	 */
	public final static Queue<String> queue = new BdbPersistentQueue(Constrants.BDB_NAME,String.class);
	
	/**
	 * 向queue中添加消息
	 * @param message
	 */
	public static void addMessage(String message){
		
		if(UrlContext.isContentUrl(message)){
			if(!BloomFilter.contains(message)){
				BloomFilter.aadUrl(message);
				queue.add(message);
			}
		}else{
			log.info("Url : " + message + " 不在设置抓取url根范围内");
		}
	}
	
	/**
	 * 获取消息
	 * @return String
	 */
	public static String getMessage(){
		if(queueIsEmpety()){
			return queue.poll();
		}
		return null;
	}
	
	/**
	 * 判断队列是否为空
	 * @return boolean
	 */
	public static boolean queueIsEmpety(){
		if(queue.isEmpty()){
			return false;
		}
		return true;
	} 
	
	
	/**
	 * 移除消息
	 */
	public static void moveQueue(){
		queue.remove();
	}
	
	/**
	 * 清除所有消息
	 */
	public static void clearQueue(){
		queue.clear();
	}
	
}
