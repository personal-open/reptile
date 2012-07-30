package com.subject.reptile.crawl;

import com.subject.database.utils.init.InitSpring;
import com.subject.reptile.common.PropertiesHelper;
import com.subject.reptile.queue.dbmq.BdbQueue;
import com.subject.reptile.threadtask.ThreadTaskManager;
import com.subject.reptile.threadtask.queuedisposal.DataDisposalThread;
import com.subject.reptile.threadtask.queuedisposal.UrlDisposalThread;
import com.subject.reptile.urlfilter.BloomFilter;

/**
 * 爬虫人口函数(主函数)
 * @author ldl
 * @version 1.0
 * 2012-07-23
 */
public class Crawl {

	/**
	 * **********************************************************
	 * 深度还没有考虑，继续考虑优化中
	 * ***********************************************************
	 * @param args
	 */
	public static void main(String[] args) {
		
		initSpring();
		initDataMqThreadTask();
		
		String [] urls = new String[]{"http://localhost/destoon"};
		initialize(urls, 3, 0);
	}
	
	
	/**
	 * 初始化Spring配置
	 */
	public static void initSpring(){
		InitSpring.initialization();
	}
	
	/**
	 * 初始化数据入库mq线程任务
	 */
	public static void initDataMqThreadTask(){
		DataDisposalThread disposalThread = new DataDisposalThread();
		ThreadTaskManager.runTaskThread(disposalThread);
	}
	
	
	/**
	 * 初始url带下载列表线程
	 * @param depth
	 */
	public static void initUrlMqThreadTask(int depth){
		for (int i = 0; i < depth; i++) {
			UrlDisposalThread urlDisposalThread = new UrlDisposalThread();
			ThreadTaskManager.runUrlTaskThread(urlDisposalThread);
		}
	}
	
	/**
	 * 初始化URL集合
	 * @param urls url集合
	 * @param depth int 抓取深度
	 */
	public static void initialize(String [] urls,int depth,int threads){
		
		System.out.println("url集合为" + urls.length);
		
		UrlContext.addUrlAndDepth(urls, depth); //向缓存中放入根Url和要抓取的深度
		//初始化过滤器
		for (int i = 0; i < urls.length; i++) {
				BloomFilter.aadUrl(urls[i]);
				BdbQueue.addMessage(urls[i]);
		}

         //抓取队列线程池，目前设置为20
		if(threads == 0){ //设置下载队列线程， 其线程为线程池，线程执行时间可在配置文件更改
			threads = Integer.parseInt(PropertiesHelper.getPorperties("threads")); //如果传入线程数为0，则使用默认线程
		}
		initUrlMqThreadTask(threads); //初始化待下载url队列
	}
}
