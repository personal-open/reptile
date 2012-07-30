package com.subject.reptile.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.concurrent.Callable;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.subject.reptile.parse.ImageParse;
import com.subject.reptile.parse.UrlParse;

/**
 * HttpClient获取网页抓取
 * 提示：HttpClient包中,当执行get或者post方法时，会默认地提供对robots.txt的支持
 * 不抓取Disallow规定的目录下的网页。
 * @author ldl
 * @version 1.0
 * 2012-7-13
 */
public class HttpResponse extends AbstractHttpResponse{

	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(HttpResponse.class);
	/**
	 * url
	 */
	private String url;
	/**
	 * 构造函数
	 */
	public HttpResponse(String url) {
		this.url = url;
	}

	/**
	 * get访问
	 * @param url url连接
	 */
	public void getMethod(String url) {
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
				String content = getInputStream(inputStream);
				if(content != null){
					//UrlParse httpParse = new UrlParse();
					//httpParse.parseUrl(content);
					//httpParse.parseImageUrl(content);
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

	
	/**
	 * post访问
	 * @param url url连接
	 */
	public void postMethod(String url) {
		HttpClient httpClient = super.getHttpClient();
		PostMethod postMethod = super.getPostMethod(url);
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			log.info("url : "+ url +" statusCode is "+ statusCode);
			if(statusCode == HttpStatus.SC_OK){
				inpuStreamAsStream(postMethod);
			}else if(statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY){
				//从头部取出转向的地址
				Header locationHeader = postMethod.getRequestHeader("location");
				if(locationHeader != null){
					String newUrl = locationHeader.getValue();
					if(newUrl == null || newUrl.equals("")){
						//使用post转向处理
						newUrl = "/";
						PostMethod redirect = new PostMethod(newUrl);
						//发送请求,做进一步处理
						statusCode = httpClient.executeMethod(redirect);
						if(statusCode == HttpStatus.SC_OK){
							inpuStreamAsStream(postMethod);
						}
					}
				}else{
					System.err.println("location is null");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
	}
	
	
	public void inpuStreamAsStream(PostMethod postMethod) throws Exception{
		InputStream inputStream = postMethod.getResponseBodyAsStream();
		String content = getInputStream(inputStream);
		if(content != null){
			parse(content);
		}
	}
	
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
	 * 对html内容进行解析(多线程方式)
	 * @param content String
	 */
	public void parse(String content){
		UrlParse urlp = new UrlParse(content);
		urlp.start();
		ImageParse imagep = new ImageParse(content);
		imagep.start();
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		HttpResponse httpResponse = new HttpResponse("");
		httpResponse.getMethod("http://localhost/destoon/");
		//httpResponse.postMethod("http://localhost/destoon/");
	}


}
