package com.subject.reptile.threadtask;

/**
 * 被监控线程抽象类
 * @author ldl
 * 2012-07-23
 */
public abstract class ThreadMonitor extends Thread{
	/**
	 * 超时时间
	 */
	protected long timeOutParamter;
	/**
	 * 
	 */
	protected long initialDelay = 1;;
	/**
	 * 周期
	 */
	protected long period;
	
	/**
	 * id
	 */
	protected String id;
	/**
	 * 线程类型
	 */
	protected String threadType;
	
	/**
	 * 
	 */
	public void backUpContext(){}
	
	/**
	 * 
	 */
	public void revertContext(){}
	
	/**
	 * 
	 * @return String
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * 
	 * @return long
	 */
	public long getInitialDelay() {
		return initialDelay;
	}
	
	/**
	 * 
	 * @return long
	 */
	public long getPeriod() {
		return period;
	}
	
	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @return String
	 */
	public String getThreadType() {
		return threadType;
	}

	/**
	 * 
	 * @param threadType String
	 */
	public void setThreadType(String threadType) {
		this.threadType = threadType;
	}

}

