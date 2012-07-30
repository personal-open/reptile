package com.subject.database.utils.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class DataBaseDao extends SqlMapClientDaoSupport{

	
	/**
	 * 单条插入数据
	 * @param statementName sql语句
	 * @param parameterObject 参数
	 * @return Object
	 */
	public Object insertBatch(final String statementName,final List<Map<String,Object>> list) throws Exception{
	
		System.out.println("进入批处理操作");
			return this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
				public Object doInSqlMapClient(SqlMapExecutor exe)throws SQLException {
					// TODO Auto-generated method stub
					exe.startBatch();
					for (Map<String,Object> map : list) {
						exe.insert(statementName,map);
					}
					exe.executeBatch();
					return null;
				}});
	}
}
