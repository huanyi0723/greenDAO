
package com.example.greendaoexample;

import android.app.Application;

import com.example.greendaoexample.dao.DaoMaster;
import com.example.greendaoexample.dao.DaoMaster.OpenHelper;
import com.example.greendaoexample.dao.DaoSession;
import com.example.greendaoexample.util.Constants;

public class GreenDaoApplication extends Application {
    private static GreenDaoApplication appContext;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if (appContext == null) {
            appContext = this;
        }
    }

    /**
     * 取得DaoMaster
     * 
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            OpenHelper helper = new DaoMaster.DevOpenHelper(appContext, Constants.DATABASE_NAME,
                    null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     * 
     * @param context
     * @return
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
