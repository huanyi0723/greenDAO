package com.example.greendaoexample.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.greendaoexample.dao.StudentsInfo;

import com.example.greendaoexample.dao.StudentsInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig studentsInfoDaoConfig;

    private final StudentsInfoDao studentsInfoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        studentsInfoDaoConfig = daoConfigMap.get(StudentsInfoDao.class).clone();
        studentsInfoDaoConfig.initIdentityScope(type);

        studentsInfoDao = new StudentsInfoDao(studentsInfoDaoConfig, this);

        registerDao(StudentsInfo.class, studentsInfoDao);
    }
    
    public void clear() {
        studentsInfoDaoConfig.getIdentityScope().clear();
    }

    public StudentsInfoDao getStudentsInfoDao() {
        return studentsInfoDao;
    }

}
