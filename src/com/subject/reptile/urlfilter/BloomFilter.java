package com.subject.reptile.urlfilter;

import java.util.BitSet;

/**
 * 布隆过滤器,过滤url是否已经被抓取过
 * @author ldl
 * @version 1.0
 * 2012-07-11
 */
public class BloomFilter{

	/**
	 * 初始化默认大小
	 */
	private static final int DEFAULT_SIZE = 5000 << 10000;
		
	private static final int[] seeds = new int[]{7,11,13,31,37,61};
	
	private static BitSet bits = new BitSet(DEFAULT_SIZE);
	
	private static SimpleHash[] simpleHash = new SimpleHash[seeds.length];
	
	
	static{
		for(int i = 0 ;i < seeds.length ;i++){
			simpleHash[i] = new SimpleHash(DEFAULT_SIZE,seeds[i]);
		}
	}

//	/**
//	 * 构造函数
//	 */
//	public BloomFilter(){
//			for(int i = 0 ;i < seeds.length ;i++){
//				simpleHash[i] = new SimpleHash(DEFAULT_SIZE,seeds[i]);
//			}
//	}
	
	
	/**
	 * 添加Url
	 * @param url Sting 
	 */
	public static void aadUrl(String url){
		for(SimpleHash sh : simpleHash){
			bits.set(sh.hash(url),true);
		}
	}
	
	/**
	 * 判断url是否已经抓取过
	 * @param url String url连接
	 * @return boolean true为url已经存在,false 为url不存在
	 */
	public static boolean contains(String url){
		
		if(url == null || url.equals("")){
			return false;
		}
		boolean ret = true;
		for(SimpleHash sh : simpleHash){
			ret = ret && bits.get(sh.hash(url));
		}
		return ret;
	}
	
	public static class SimpleHash{
		
		private int cap;
		
		private int seed;
		
		public SimpleHash(int cap ,int seed){
			this.cap = cap;
			this.seed = seed;
		}
		
		public int hash(String value){
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result =  seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}
	
	public static void main(String[] args) {
		
		
		//BloomFilter bf = new BloomFilter();
		System.out.println(contains("http://www.baidu.com"));
		aadUrl("http://www.baidu.com");
		System.out.println(contains("http://www.baidu.com"));
		
	}
	
}
