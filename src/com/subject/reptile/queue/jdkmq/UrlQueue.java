package com.subject.reptile.queue.jdkmq;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * url链接队列
 * @version 1.0
 * @author ldl
 * 2012-7-23
 */
public class UrlQueue {

	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog( DataQueue.class );

	/**
	 * queue
	 */
	private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	
	
	/**
	 * 向queue中添加消息
	 * @param message String 
	 */
	public static void addQueueMessage(String message){
		queue.add(message);
	}
	
	/**
	 * 从queue中获取消息
	 * @return String
	 */
	public static String getQueueMessage(){
		if(queueIsEmpty()){
			return queue.poll();
		}
		return null;
	}
	
	/**
	 * 判断queue是否为空
	 * @return boolean
	 */
	public static boolean queueIsEmpty(){
		if(queue.isEmpty()){
			return false; 
		}
		return true;
	}
	
	/**
	 * 清楚queue消息
	 */
	public static void clearQueueMessage(){
		queue.clear();
	}
}
