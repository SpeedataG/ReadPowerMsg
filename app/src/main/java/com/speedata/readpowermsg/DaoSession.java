package com.speedata.readpowermsg;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.speedata.readpowermsg.Power;

import com.speedata.readpowermsg.PowerDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig powerDaoConfig;

    private final PowerDao powerDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        powerDaoConfig = daoConfigMap.get(PowerDao.class).clone();
        powerDaoConfig.initIdentityScope(type);

        powerDao = new PowerDao(powerDaoConfig, this);

        registerDao(Power.class, powerDao);
    }
    
    public void clear() {
        powerDaoConfig.clearIdentityScope();
    }

    public PowerDao getPowerDao() {
        return powerDao;
    }

}
