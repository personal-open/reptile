package com.subject.reptile.threadtask;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池任务
 * @author ldl
 * @version 1.0 
 * 2012-07-23
 */
public class ThreadTaskManager {

	/**
	 * 数据队列入库线程池
	 */
	public static ScheduledThreadPoolExecutor taskThread = new ScheduledThreadPoolExecutor(5);

	
	
	/**
	 * url队列线程
	 */
	public static ScheduledThreadPoolExecutor  urlTaskThread = new ScheduledThreadPoolExecutor(20);
	
	/**
	 * 运行数据队列线程池
	 * @param task ThreadMonitor 线程任务
	 */
	public static void runTaskThread(ThreadMonitor task) {
		final ScheduledFuture<?> data = taskThread.scheduleWithFixedDelay(task, task.getInitialDelay(),task.getPeriod(),TimeUnit.SECONDS);
	}
	
	
	/**
	 * 运行线程池
	 * @param task ThreadMonitor 线程任务
	 */
	public static void runUrlTaskThread(ThreadMonitor task) {
		final ScheduledFuture<?> url = taskThread.scheduleWithFixedDelay(task, task.getInitialDelay(),task.getPeriod(),TimeUnit.SECONDS);
	}
	
	/**
	 * 关闭线程任务
	 */
	public static void stopThreadTask() {

		try {
			taskThread.shutdownNow();
		
			if (!taskThread.awaitTermination(15, TimeUnit.SECONDS)) {
				taskThread.shutdownNow(); 
				Thread.currentThread().interrupt();
				if (!taskThread.awaitTermination(15, TimeUnit.SECONDS)) {
					System.err.println("Pool did not terminate");
				}
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			taskThread.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			taskThread.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

}
