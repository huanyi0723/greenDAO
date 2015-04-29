package com.example.greendaotest;

import java.util.ArrayList;
import java.util.List;

import com.cn.speedchat.greendao.DaoSession;
import com.cn.speedchat.greendao.MqttChatEntity;
import com.cn.speedchat.greendao.MqttChatEntityDao;
import com.cn.speedchat.greendao.MqttChatEntityDao.Properties;
import com.cn.speedchat.greendao.ReplayEntityDao;
import com.cn.speedchat.greendao.SessionEntity;
import com.cn.speedchat.greendao.SessionEntityDao;

import de.greenrobot.dao.query.QueryBuilder;

import android.content.Context;
import android.util.Log;

public class DBHelper {

	private static final String TAG = DBHelper.class.getSimpleName();
	private static DBHelper instance;
	private static Context appContext;
	private DaoSession mDaoSession;
	private MqttChatEntityDao chatDao;
	private SessionEntityDao sessionDao;

	private DBHelper() {
	}

	// 单例模式，DBHelper只初始化一次
	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper();
			if (appContext == null) {
				appContext = context.getApplicationContext();
			}
			instance.mDaoSession = ControlApp.getDaoSession(context);
			instance.chatDao = instance.mDaoSession.getMqttChatEntityDao();
			instance.sessionDao = instance.mDaoSession.getSessionEntityDao();
		}
		return instance;
	}

	// 删除Session表
	public void dropSessionTable() {
		SessionEntityDao.dropTable(mDaoSession.getDatabase(), true);
	}

	// 删除MqttChatEntity表
	public void dropChatTable() {
		MqttChatEntityDao.dropTable(mDaoSession.getDatabase(), true);
	}

	// 删除所有表
	public void dropAllTable() {
		MqttChatEntityDao.dropTable(mDaoSession.getDatabase(), true);
		SessionEntityDao.dropTable(mDaoSession.getDatabase(), true);
		ReplayEntityDao.dropTable(mDaoSession.getDatabase(), true);
	}

	// 创建所有表
	public void createAllTable() {
		MqttChatEntityDao.createTable(mDaoSession.getDatabase(), true);
		SessionEntityDao.createTable(mDaoSession.getDatabase(), true);
		ReplayEntityDao.createTable(mDaoSession.getDatabase(), true);
	}

	/**
	 * insert or update note
	 * 
	 * @param note
	 * @return insert or update note id
	 */
	// 插入或者删除session项
	public long saveSession(SessionEntity session) {
		return sessionDao.insertOrReplace(session);
	}

	// 获得所有的Session倒序排存到List列表里面
	public List<SessionEntity> loadAllSession() {
		List<SessionEntity> sessions = new ArrayList<SessionEntity>();
		List<SessionEntity> tmpSessions = sessionDao.loadAll();
		int len = tmpSessions.size();
		for (int i = len - 1; i >= 0; i--) {
			sessions.add(tmpSessions.get(i));
		}
		return sessions;
	}

	public void DeleteSession(SessionEntity entity) {
		sessionDao.delete(entity);
	}

	// 删除某一项Session
	public void DeleteNoteBySession(SessionEntity entity) {
		QueryBuilder<MqttChatEntity> mqBuilder = chatDao.queryBuilder();
		mqBuilder.where(Properties.Sessionid.eq(entity.getSessionid()));
		List<MqttChatEntity> chatEntityList = mqBuilder.build().list();
		chatDao.deleteInTx(chatEntityList);
	}

	// 根据id找到某一项
	public MqttChatEntity loadNote(long id) {
		return chatDao.load(id);
	}

	// 获得所有的MqttChatEntity列表
	public List<MqttChatEntity> loadAllNote() {
		return chatDao.loadAll();
	}

	/**
	 * query list with where clause ex: begin_date_time >= ? AND end_date_time
	 * <= ?
	 * 
	 * @param where
	 *            where clause, include 'where' word
	 * @param params
	 *            query parameters
	 * @return
	 */
	// 查询满足params条件的列表
	public List<MqttChatEntity> queryNote(String where, String... params) {
		ArrayList<MqttChatEntity> ad = new ArrayList<MqttChatEntity>();
		return chatDao.queryRaw(where, params);
	}

	// 不一一介绍了，大家可以自己写，有些比较难的查询可以使用QueryBuilder来查询
	public List<MqttChatEntity> loadLastMsgBySessionid(String sessionid) {
		QueryBuilder<MqttChatEntity> mqBuilder = chatDao.queryBuilder();
		mqBuilder.where(Properties.Sessionid.eq(sessionid)).orderDesc(Properties.Id).limit(1);
		return mqBuilder.list();
	}

	public List<MqttChatEntity> loadMoreMsgById(String sessionid, Long id) {
		QueryBuilder<MqttChatEntity> mqBuilder = chatDao.queryBuilder();
		mqBuilder.where(Properties.Id.lt(id)).where(Properties.Sessionid.eq(sessionid)).orderDesc(Properties.Id)
				.limit(20);
		return mqBuilder.list();
	}

	/**
	 * delete all note
	 */
	public void deleteAllNote() {
		chatDao.deleteAll();
	}

	/**
	 * delete note by id
	 * 
	 * @param id
	 */
	public void deleteNote(long id) {
		chatDao.deleteByKey(id);
		Log.i(TAG, "delete");
	}

	public void deleteNote(MqttChatEntity note) {
		chatDao.delete(note);
	}
}
