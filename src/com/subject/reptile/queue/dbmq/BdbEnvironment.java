package com.subject.reptile.queue.dbmq;

import java.io.File;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;


/**
 * BDB数据库环境,可以缓存StoredClassCatalog并共享
 * @author ldl
 * @version 1.0
 * 2012-07-20
 */
public class BdbEnvironment extends Environment{

	
	private StoredClassCatalog classCatalog;
	
	private Database classCatalogDB;
	
	/**
	 * 构造函数
	 * @param envHome File 数据库环境目录
	 * @param envConfig EnvironmentConfig 数据库环境配置
	 * @throws DatabaseException
	 */
	public BdbEnvironment(File envHome,EnvironmentConfig envConfig) throws DatabaseException {
		super(envHome, envConfig);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 返回StoredClassCatalog
	 * @return StoredClassCatalog
	 */
	public StoredClassCatalog getClassCatalog(){
		if(classCatalog == null){
			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			try {
				classCatalogDB = openDatabase(null,"classCatalog",dbConfig);
				classCatalog = new StoredClassCatalog(classCatalogDB);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return classCatalog;
	}
	
	@Override
	public synchronized void close() throws DatabaseException {
		// TODO Auto-generated method stub
		 if(classCatalogDB!=null) {
	            classCatalogDB.close();
	        }
	        super.close();
	}

	

}
