package com.subject.reptile.threadtask.queuedisposal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.subject.database.utils.action.ReptileAction;
import com.subject.database.utils.init.InitSpring;
import com.subject.reptile.common.PropertiesHelper;
import com.subject.reptile.queue.jdkmq.DataQueue;
import com.subject.reptile.threadtask.ThreadMonitor;


/**
 * 数据队列处理线程（数据入库）
 * @author ldl
 * @version 1.0
 * 2012-07-24
 */
public class ImgDisposalThread extends ThreadMonitor{

	/**
	 * log日志
	 */
	private static Logger log = LoggerFactory.getLogger(ImgDisposalThread.class);
	
	/**
	 * 数据操作Action
	 */
	private ReptileAction reptileAction; 
	/**
	 * 构造函数
	 */
	public ImgDisposalThread(){
		reptileAction = (ReptileAction)InitSpring.getBean("reptileAction");
		super.initialDelay = Integer.valueOf(PropertiesHelper.getPorperties("initialDelay_DataQueue"));
		super.period = Integer.valueOf(PropertiesHelper.getPorperties("period_DataQueue"));
	}

	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(DataQueue.queueIsEmpty()){
			log.info("入库队列线程is Runing");
			reptileAction.insertBatch(getList(DataQueue.queue));
		}
	}
	
	/**
	 * 每次从数据队列中获取若干条记录封装入库
	 * @param queue ConcurrentLinkedQueue<Map<String,Object>>
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String,Object>> getList(ConcurrentLinkedQueue<Map<String,Object>> queue){
		log.info("取到 data  queue 的大小为 " + queue.size());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			int number = Integer.parseInt(PropertiesHelper.getPorperties("number"));
			for (int i = 0; i < number; i++) {
				Map<String,Object> map = DataQueue.getQueueMessage();
				if(map != null){
					list.add(map);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("入库入库代大小为 ：" + list.size());
		return list;
		
	}
	
}
