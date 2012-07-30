package com.subject.reptile.metadata;

/**
 * Http常用状态码(HTTP状态码通常分为5种类型,分别以1~5五个数字开头,由3位整数组成....)
 * @author ldl
 * @version 1.0
 * 2012-07-05
 */
public interface HttpStatus {

	/**
	 * 代码描述 : 请求成功
	 * 处理方式 : 获取响应的内容，进行处理
	 */
	public static final int STATUS_200 = 200;
	
	/**
	 * 代码描述 ：请求完成，结果是创建了新资源。新创建资源的URL可在响应的实体中得到
	 * 处理方式 : 爬虫中不会遇到
	 */
	public static final int STATUS_201 = 201;
	
	/**
	 * 代码描述 : 请求被接受，但处理尚未完成
	 * 处理方式 : 阻塞等待
	 */
	public static final int STATUS_202 = 202;
	
	/**
	 * 代码描述 ： 服务器端已经实现了请求，但是没有返回新的信息，如果客户是用户代理，则无须为此更新自身的文档视图
	 * 处理方式 ： 丢弃
	 */
	public static final int STATUS_204 = 204;
	
	/**
	 * 代码描述 ： 该状态码不被HTTP/1.0的用应程序直接使用，只是作为3XX类型回应的默认解释。存在多个可用的被请求资源
	 * 处理方式 ： 若程序中能够处理，则进行进一步处理，如果程序中不能处理，则丢弃
	 */
	public static final int STATUS_300 = 300;
	
	/**
	 * 代码描述 : 请求到的资源会分配一个永久的URL，这样就可以在将来通过该URL来访问此资源
	 * 处理方式 ：重定向到分配的URL
	 */
	public static final int STATUS_301 = 301;
	
	/**
	 * 代码描述：请求到的资源在一个不同的URL处临时保存
	 * 处理方式：重定向到临时的URL
	 */
	public static final int STATUS_302 = 302;
	
	/**
	 * 代码描述：请求的资源未更新
	 * 处理方式：丢弃
	 */
	public static final int STATUS_304 = 304;
	
	/**
	 * 代码描述：非法请求
	 * 处理方式：丢弃
	 */
	public static final int STATUS_400 = 400;
	
	/**
	 * 代码描述：未授权
	 * 处理方式：丢弃
	 */
	public static final int STATUS_401 = 401;

	/**
	 * 代码描述：禁止
	 * 处理方式：丢弃
	 */
	public static final int STATUS_403 = 403;
	
	/**
	 * 代码描述：没有找到
	 * 处理方式：丢弃
	 */
	public static final int STATUS_404 = 404;
	
	/**
	 * 代码描述：回应以“5”开头的状态码表示服务器端发现自己出现错误，不能继续执行请求。
	 * 处理方式：丢弃
	 */
	public static final int STATUS_500 = 500;
}
