package com.subject.reptile.threadtask.queuedisposal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.subject.reptile.common.PropertiesHelper;
import com.subject.reptile.protocol.HttpResponse;
import com.subject.reptile.queue.dbmq.BdbQueue;
import com.subject.reptile.threadtask.ThreadMonitor;

/**
 * url待下载队列线程
 * @author ldl
 * @version 1.0
 * 2012-7-30
 */
public class UrlDisposalThread extends ThreadMonitor{

	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(UrlDisposalThread.class);
	
	/**
	 * 构造函数
	 */
	public UrlDisposalThread(){
		super.initialDelay = Integer.valueOf(PropertiesHelper.getPorperties("urlinitialDelay_DataQueue"));
		super.period = Integer.valueOf(PropertiesHelper.getPorperties("urlperiod_DataQueue"));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(BdbQueue.queueIsEmpety()){
			String url = BdbQueue.getMessage();
			System.out.println("从url queue中获取消息 ：" +url);
			HttpResponse httpResponse = new HttpResponse(url);
			httpResponse.start();
		}
	}

	
	
}
