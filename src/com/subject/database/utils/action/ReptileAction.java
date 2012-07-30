package com.subject.database.utils.action;

import java.util.List;
import java.util.Map;

import com.subject.database.utils.dao.ReptileDao;

public class ReptileAction {

	private ReptileDao reptileDao;

	public void setReptileDao(ReptileDao reptileDao) {
		this.reptileDao = reptileDao;
	}
	
	public void insertBatch(List<Map<String,Object>> list){
		reptileDao.insertBatch(list);
	}
}
