package com.speedata.readpowermsg;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by 张明_ on 2018/5/2.
 * Email 741183142@qq.com
 */

public class MyApp extends Application {
    private static MyApp m_application; // 单例
    //greendao
    private static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        m_application = this;
        setupDatabase();
    }


    public static MyApp getInstance() {
        return m_application;
    }

    private void setupDatabase() {
        //创建数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "greendao.db", null);
        //获得可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获得数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获得dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
