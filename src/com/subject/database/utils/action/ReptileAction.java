package com.subject.database.utils.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.subject.database.utils.dao.ReptileDao;

public class ReptileAction {
	
	/**
	 * log日志
	 */
	private static Log log = LogFactory.getLog(ReptileAction.class);

	private ReptileDao reptileDao;

	public void setReptileDao(ReptileDao reptileDao) {
		this.reptileDao = reptileDao;
	}
	
	public void insertBatch(List<Map<String,Object>> list){
		reptileDao.insertBatch(list);
	}
}
