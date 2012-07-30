package com.subject.reptile.queue.dbmq;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.EnvironmentConfig;
import com.subject.reptile.common.FileHelper;
import com.subject.reptile.common.PropertiesHelper;
import com.subject.reptile.metadata.Constrants;

/**
 * DBD队列操作(封装一些Queue操作)
 * @author ldl
 * @version 1.0
 * 2012-7-20
 * @param <E>
 */
public class BdbPersistentQueue<E extends Serializable> extends AbstractQueue<E> implements Serializable {

	private static final long serialVersionUID = 3427799316155220967L;

	private transient BdbEnvironment dbEnv;         // 数据库环境,无需序列化
    private transient Database queueDb;             // 数据库,用于保存值,使得支持队列持久化,无需序列化
    private transient StoredMap<Long,E> queueMap;   // 持久化Map,Key为指针位置,Value为值,无需序列化
    private transient String dbDir;                 // 数据库所在目录
    private transient String dbName;				// 数据库名字
    private AtomicLong headIndex;                   // 头部指针
    private AtomicLong tailIndex;                   // 尾部指针
    private transient E peekItem=null;              // 当前获取的值

	
    /**
     * 构造函数,传入BDB数据库
     * @param db Database数据库
     * @param valueClass
     * @param classCatalog
     */
    public BdbPersistentQueue(Database db,Class<E> valueClass,StoredClassCatalog classCatalog){
        //this.queueDb=db;
        try {
        	//获取当前数据库名称
			this.dbName=db.getDatabaseName();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        headIndex=new AtomicLong(0);
        tailIndex=new AtomicLong(0);
        bindDatabase(queueDb,valueClass,classCatalog);
    }

    
    /**
     * 构造函数,传入BDB数据库位置和名字,自己创建数据库
     * 
     * @param dbDir
     * @param dbName
     * @param valueClass
     */
    public BdbPersistentQueue(String dbName,Class<E> valueClass){
        headIndex=new AtomicLong(0);
        tailIndex=new AtomicLong(0);
        this.dbDir=PropertiesHelper.getPorperties(Constrants.FILE_NAME);
        //文件不存在，创建文件
        FileHelper.getFile(this.dbDir);
        this.dbName=dbName;
        try {
			createAndBindDatabase(dbDir,dbName,valueClass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * 绑定数据库
     * @param db Database 内存数据库
     * @param valueClass
     * @param classCatalog
     */
    public void bindDatabase(Database db, Class<E> valueClass, StoredClassCatalog classCatalog){
        EntryBinding<E> valueBinding = TupleBinding.getPrimitiveBinding(valueClass);
        if(valueBinding == null) {
            valueBinding = new SerialBinding<E>(classCatalog, valueClass);   // 序列化绑定
        }
        queueDb = db;
        // db Key Value allow write
        queueMap = new StoredSortedMap<Long,E>(db,TupleBinding.getPrimitiveBinding(Long.class),valueBinding,true);                                          
    }

    /**
     * 创建以及绑定数据库
     * @param dbDir
     * @param dbName
     * @param valueClass
     * @throws Exception
     */
    private void createAndBindDatabase(String dbDir, String dbName,Class<E> valueClass) throws Exception{
        // 数据库位置
        File envFile = new File(dbDir);
        //数据库环境
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        //关闭事务
        envConfig.setTransactional(false);
        // 数据库配置
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true); //true为不存在创建一个，false不存在打开环境失败
        dbConfig.setTransactional(false);
        dbConfig.setDeferredWrite(true);
        // 创建环境
        dbEnv = new BdbEnvironment(envFile, envConfig);
        // 打开数据库
        Database db = dbEnv.openDatabase(null, dbName, dbConfig);
        // 绑定数据库
        bindDatabase(db,valueClass,dbEnv.getClassCatalog());
    }

	
    /**
     * 便利器
     */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return queueMap.values().iterator();
	}

	/**
	 * 大小
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		synchronized(tailIndex){
            synchronized(headIndex){
                return (int)(tailIndex.get()-headIndex.get());
            }
        }
	}

	/**
	 * 向队列中添加数据
	 */
	public boolean offer(E e) {
		// TODO Auto-generated method stub
		synchronized(tailIndex){
            queueMap.put(tailIndex.getAndIncrement(), e);   // 从尾部插入
        }
        return true;

	}

	/**
	 * 获取值,从头部获取
	 */
	public E peek() {
		// TODO Auto-generated method stub
		 synchronized(headIndex){
	            if(peekItem!=null){
	                return peekItem;
	            }
	            E headItem=null;
	            while(headItem==null&&headIndex.get()<tailIndex.get()){ // 没有超出范围
	                headItem=queueMap.get(headIndex.get());
	                if(headItem!=null){
	                    peekItem=headItem;
	                    continue;
	                } 
	                headIndex.incrementAndGet();    // 头部指针后移
	            }
	            return headItem;
	        }

	}

	/**
	 * 移出元素,移出头部元素
	 */
	public E poll() {
		// TODO Auto-generated method stub
		 synchronized(headIndex){
	            E headItem=peek();
	            if(headItem!=null){
	                queueMap.remove(headIndex.getAndIncrement());
	                peekItem=null;
	                return headItem;
	            }
	        }
	        return null;
	}
	
	 /**
     * 关闭,也就是关闭所是用的BDB数据库但不关闭数据库环境
     */
    public void close(){
        try {
            if(queueDb!=null){
                queueDb.sync();
                queueDb.close();
            }
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 清理,会清空数据库,并且删掉数据库所在目录,慎用.如果想保留数据,请调用close()
     */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		try {
	    	   close();
	    	   if(dbEnv!=null&&queueDb!=null){
					dbEnv.removeDatabase(null, dbName==null?queueDb.getDatabaseName():dbName); 
	                dbEnv.close();
	           }
		    } catch (DatabaseNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (DatabaseException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } finally{
		    	try {
		    		if(this.dbDir!=null){
		    			FileUtils.deleteDirectory(new File(this.dbDir));
		    		}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	}
}
