package com.subject.reptile.protocol;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 抽象http访问
 * @author ldl
 * @version 1.0
 * 2012-07-11
 */
public abstract class AbstractHttpResponse extends Thread{

	
	/**
	 * 返回HttpClient 对象
	 * @return HttpClient对象
	 */
	public HttpClient getHttpClient(){
		return new HttpClient();
	}
	
	/**
	 * 根据url实例化GetMethod方法
	 * @param url String url连接
	 * @return GetMethod
	 */
	public GetMethod getGetMethod(String url){
		return new GetMethod(url);
	}
	
	/**
	 * 根据url实例化PostMethod方法
	 * @param url String url连接
	 * @return PostMethod
	 */
	public PostMethod getPostMethod(String url){
		return new PostMethod(url);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
	
}
