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
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;

/**
 * HttpClient获取网页抓取
 * 提示：HttpClient包中,当执行get或者post方法时，会默认地提供对robots.txt的支持
 * 不抓取Disallow规定的目录下的网页。
 * @author ldl
 * @version 1.0
 * 2012-7-13
 */
public class HttpPostResponse extends AbstractHttpResponse{

	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(HttpPostResponse.class);
	/**
	 * url
	 */
	private String url;
	/**
	 * 构造函数
	 */
	public HttpPostResponse(String url) {
		this.url = url;
	}

	/**
	 * run函数
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		postMethod();
	}

	
	/**
	 * post访问
	 */
	public void postMethod() {
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
	
	/**
	 * 获取html内容
	 * @param postMethod PostMethod
	 * @throws Exception
	 */
	public void inpuStreamAsStream(PostMethod postMethod) throws Exception{
		InputStream inputStream = postMethod.getResponseBodyAsStream();
		String content = super.getInputStream(inputStream);
		if(content != null){
			parse(content);
		}
	}
	
	
	/**
	 * 对html内容进行解析(多线程方式)
	 * @param content String
	 */
	public void parse(String content){
		UrlParse urlp = new UrlParse(content,url);
		urlp.start();
		log.info("解析  url "+ url + "  下的url集合");
		ImageParse imagep = new ImageParse(content,url);
		imagep.start();
		log.info("解析  url "+ url + "  下的image集合");
	}
	
	
	public static void main(String[] args) {
		HttpPostResponse httpResponse = new HttpPostResponse("http://localhost/destoon/");
		//httpResponse.getMethod("http://localhost/destoon/");
		httpResponse.postMethod();
	}


}
