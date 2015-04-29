package com.example.greendaoexample.util;

import java.util.List;

import android.content.Context;

import com.example.greendaoexample.GreenDaoApplication;
import com.example.greendaoexample.dao.DaoSession;
import com.example.greendaoexample.dao.StudentsInfo;
import com.example.greendaoexample.dao.StudentsInfoDao;
import com.example.greendaoexample.dao.StudentsInfoDao.Properties;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

public class DBHelper {
	private static Context mContext;
	private static DBHelper instance;
	private static DaoSession daoSession;

	private DBHelper() {
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper();
			if (mContext == null) {
				mContext = context;
			}

			// 数据库对象
			daoSession = GreenDaoApplication.getDaoSession();
		}
		return instance;
	}

	/**
	 * 添加数据
	 * 
	 * @param item
	 */
	public void addToStudentsInfoTable(StudentsInfo item) {
		daoSession.getStudentsInfoDao().insert(item);
	}

	/**
	 * 查询表所有数据
	 * 
	 * @return
	 */
	public List<StudentsInfo> getStudentsInfoList() {
		QueryBuilder<StudentsInfo> qb = daoSession.getStudentsInfoDao().queryBuilder();
		return qb.list();
	}

	/**
	 * 查询表所有数据
	 * 
	 * @return
	 */
	public List<StudentsInfo> getStudentsInfo() {
		return daoSession.getStudentsInfoDao().loadAll();
	}

	/**
	 * 查询是否保存
	 * 
	 * @param Id
	 * @return
	 */
	public boolean isStudentInfoSaved(int Id) {
		QueryBuilder<StudentsInfo> qb = daoSession.getStudentsInfoDao().queryBuilder();
		qb.where(Properties.Id.eq(Id));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;
	}

	/** 删除 */
	public void deleteStudentsInfoList(long Id) {
		QueryBuilder<StudentsInfo> qb = daoSession.getStudentsInfoDao().queryBuilder();
		DeleteQuery<StudentsInfo> bd = qb.where(Properties.Id.eq(Id)).buildDelete();
		bd.executeDeleteWithoutDetachingEntities();
	}

	//删除全部数据
	public void clearStudentsInfo() {
		daoSession.getStudentsInfoDao().deleteAll();
	}

	/** 通过姓名查找其性别 */
	public String getSexByName(String name) {
		QueryBuilder<StudentsInfo> qb = daoSession.getStudentsInfoDao().queryBuilder();
		qb.where(Properties.Name.eq(name));
		if (qb.list().size() > 0) {
			return qb.list().get(0).getSex();
		} else {
			return "";
		}
	}
	
	//根据年龄查找
	public List<StudentsInfo> getListByAge(String age) {
		QueryBuilder<StudentsInfo> qb = daoSession.getStudentsInfoDao().queryBuilder();
		qb.where(Properties.Age.eq(age));
		if (qb.list().size() > 0) {
			return qb.list();
		} else {
			return null;
		}
	}

	/** 多重查询 */
	public List<StudentsInfo> getIphRegionList(String name, String sex) {
		QueryBuilder<StudentsInfo> qb = daoSession.getStudentsInfoDao().queryBuilder();
		qb.where(qb.and(Properties.Name.eq(name), Properties.Sex.eq(sex)));
		qb.orderAsc(Properties.Id);// 排序依据
		return qb.list();
	}
}
