package com.subject.test;

import java.util.HashMap;
import java.util.Map;

import com.subject.database.utils.init.InitSpring;
import com.subject.reptile.queue.jdkmq.DataQueue;
import com.subject.reptile.threadtask.ThreadTaskManager;
import com.subject.reptile.threadtask.queuedisposal.ImgDisposalThread;

public class DataQueueTest {

	public static void main(String[] args) {
		InitSpring.initialization();
		initDataMqThreadTask();
		setDataMq();
	}
	
	
	
	/**
	 * 初始化数据mq线程任务
	 */
	public static void initDataMqThreadTask(){
		ImgDisposalThread eventRunQueue = new ImgDisposalThread();
		ThreadTaskManager.runTaskThread(eventRunQueue);
	}
	
	public static void setDataMq(){
		
		for (int i = 0; i < 100; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("URLIMAGE","http://www.baidu.com/"+i);
			System.out.println("向mq中放入消息");
			DataQueue.addQueueMessage(map);
		}
		
	}
}
