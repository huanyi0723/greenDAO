package com.cn.speedchat.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.cn.speedchat.greendao.MqttChatEntity;
import com.cn.speedchat.greendao.SessionEntity;
import com.cn.speedchat.greendao.ReplayEntity;
import com.cn.speedchat.greendao.Customer;
import com.cn.speedchat.greendao.Order;

import com.cn.speedchat.greendao.MqttChatEntityDao;
import com.cn.speedchat.greendao.SessionEntityDao;
import com.cn.speedchat.greendao.ReplayEntityDao;
import com.cn.speedchat.greendao.CustomerDao;
import com.cn.speedchat.greendao.OrderDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig mqttChatEntityDaoConfig;
    private final DaoConfig sessionEntityDaoConfig;
    private final DaoConfig replayEntityDaoConfig;
    private final DaoConfig customerDaoConfig;
    private final DaoConfig orderDaoConfig;

    private final MqttChatEntityDao mqttChatEntityDao;
    private final SessionEntityDao sessionEntityDao;
    private final ReplayEntityDao replayEntityDao;
    private final CustomerDao customerDao;
    private final OrderDao orderDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        mqttChatEntityDaoConfig = daoConfigMap.get(MqttChatEntityDao.class).clone();
        mqttChatEntityDaoConfig.initIdentityScope(type);

        sessionEntityDaoConfig = daoConfigMap.get(SessionEntityDao.class).clone();
        sessionEntityDaoConfig.initIdentityScope(type);

        replayEntityDaoConfig = daoConfigMap.get(ReplayEntityDao.class).clone();
        replayEntityDaoConfig.initIdentityScope(type);

        customerDaoConfig = daoConfigMap.get(CustomerDao.class).clone();
        customerDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        mqttChatEntityDao = new MqttChatEntityDao(mqttChatEntityDaoConfig, this);
        sessionEntityDao = new SessionEntityDao(sessionEntityDaoConfig, this);
        replayEntityDao = new ReplayEntityDao(replayEntityDaoConfig, this);
        customerDao = new CustomerDao(customerDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);

        registerDao(MqttChatEntity.class, mqttChatEntityDao);
        registerDao(SessionEntity.class, sessionEntityDao);
        registerDao(ReplayEntity.class, replayEntityDao);
        registerDao(Customer.class, customerDao);
        registerDao(Order.class, orderDao);
    }
    
    public void clear() {
        mqttChatEntityDaoConfig.getIdentityScope().clear();
        sessionEntityDaoConfig.getIdentityScope().clear();
        replayEntityDaoConfig.getIdentityScope().clear();
        customerDaoConfig.getIdentityScope().clear();
        orderDaoConfig.getIdentityScope().clear();
    }

    public MqttChatEntityDao getMqttChatEntityDao() {
        return mqttChatEntityDao;
    }

    public SessionEntityDao getSessionEntityDao() {
        return sessionEntityDao;
    }

    public ReplayEntityDao getReplayEntityDao() {
        return replayEntityDao;
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

}
