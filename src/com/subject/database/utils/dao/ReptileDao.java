package com.subject.database.utils.dao;

import java.util.List;
import java.util.Map;

import com.subject.database.utils.database.DataBaseDao;

public class ReptileDao {

	/**
	 * 
	 */
	private DataBaseDao dataBaseDao;

	/**
	 * set注入
	 * @param dataBaseDao DataBaseDao
	 */
	public void setDataBaseDao(DataBaseDao dataBaseDao) {
		this.dataBaseDao = dataBaseDao;
	}
	
	/**
	 * 批处理插入
	 * @param list List<Map<String,Object>>
	 */
	public void insertBatch(List<Map<String,Object>> list){
		try {
			dataBaseDao.insertBatch("reptileSqlMap.insertBatchData",list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
