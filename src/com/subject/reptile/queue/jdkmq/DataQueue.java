package com.subject.reptile.queue.jdkmq;

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 解析后入库Queue
 * @author ldl
 * @version 1.0
 * 2012-07-17
 */
public class DataQueue {
	
	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog( DataQueue.class );

	/**
	 * queue
	 */
	public static ConcurrentLinkedQueue<Map<String,Object>> queue = new ConcurrentLinkedQueue<Map<String,Object>>();
	
	
	/**
	 * 向queue中添加消息
	 * @param message String 
	 */
	public static void addQueueMessage(Map<String,Object> map){
		queue.add(map);
	}
	
	/**
	 * 从queue中获取消息
	 * @return String
	 */
	public static Map<String,Object> getQueueMessage(){
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
		//log.info("image queue 大小为 ：" + queue.size());
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
