package com.subject.database.utils.init;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 初始化Spring操作
 * @author ldl
 * @version 1.0
 * 2012-07-11
 */
public class InitSpring {
	
	/**
	 * log日志
	 */
	private static Logger log = Logger.getLogger(InitSpring.class);
	
	/**
	 * spring上下文对象
	 */
	private static ApplicationContext context;
	
	
	/**
	 * 初始化
	 */
	public static void initialization(){
		context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
		log.info("spring environment initialization complete");
	}
	
	/**
	 * 获取业务bean实例
	 * @param beanId String beanid 
	 * @return Object 业务bean
	 */
	public static Object getBean(String beanId) {
		Object bean = context.getBean(beanId);
		return bean;
	}
	
}


