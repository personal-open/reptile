package com.subject.reptile.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * MD5算法
 * 传入参数:一个字节数组
 * 传出参数:字节数组的MD5 结果字符串
 * @author ldl
 * @version 1.0
 * 2012-07-13
 */
public class MD5 {

	
	public static String getMD5(byte[] source){
		String s = null;
		//用来将字节转换成十六进制表示的字符
		char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','b','c','d','e','f'};
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			//MD5的计算结果是一个128位的长整数，用字节表示就是16个字节
			byte [] tmp = md.digest();
			//每个字节用十六进制表示的话，使用两个字符，所以表示成十六进制需要32个字符
			char str[] = new char[16*2];
			//表示转换结果中对应的字符位置
			int k=0;
			//从第一个字节开始，将MD5的每一个字节转换成十六进制字符
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				//取字节中高4位的数字转换，>>>为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				//取字节中低4位的数字转换
				str[k++] = hexDigits[byte0 & 0xf];
			}
			//将转换结果转换成字符串
			s = new String(str);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public static void main(String[] args) {
		
	}
}
