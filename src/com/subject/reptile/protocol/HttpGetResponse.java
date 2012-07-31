package com.subject.reptile.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpGetResponse extends AbstractHttpResponse{

	
	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(HttpPostResponse.class);
	
	/**
	 * url连接
	 */
	private String url;
	
	
	/**
	 * 构造函数
	 * @param _url String 传入url连接
	 */
	public HttpGetResponse(String _url){
		this.url = _url;
	}

	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		getMethod();
	}
	
	/**
	 * get访问
	 * @param url url连接
	 */
	public void getMethod() {
		HttpClient httpClient = super.getHttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		GetMethod getMethod = super.getGetMethod(url);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次,get默认支持数据转发
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			log.info("url : " + url +" statusCode is " + statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream inputStream = getMethod.getResponseBodyAsStream();
				String content = super.getInputStream(inputStream);
				if(content != null){
					
				}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			//发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//网络异常处理
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
	}

	
	
}
