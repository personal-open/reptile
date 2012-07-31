package com.subject.reptile.protocol;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

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

	
	/**
	 * html内容io读写操作
	 * @param inputStream InputStream
	 * @return String
	 */
	public String getInputStream(InputStream inputStream) {
		try {
			InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
			StringWriter sw = new StringWriter();
			char[] buffer = new char[1024];
			int n = 0;
			while(-1 !=(n = reader.read(buffer))) {
				sw.write(buffer,0,n);
			}
			reader.close();
			sw.close();
			return sw.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
	
}
