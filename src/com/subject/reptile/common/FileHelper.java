package com.subject.reptile.common;

import java.io.File;

/**
 * 文件帮助函数
 * @author ldl
 * @version 1.0
 * 2012-7-23
 */
public class FileHelper {

	
	/**
	 * 文件不存在,则创建
	 * @param filePath
	 * @return
	 */
	public static File getFile(String filePath){
		File file = new File(filePath);
		if(!file.exists()|| !file.isDirectory()){
			file.mkdirs();
		}
		return file;
		
	}
}
