package com.better.animator;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created by huiyong on 2017/12/27.
 */

public class MainApplication extends Application {
    private String BUGLY_ID = "306e322e87";

    @Override
    public void onCreate() {
        super.onCreate();

        //Bugly 异常上报
        Bugly.init(getApplicationContext(), BUGLY_ID, !BuildConfig.DEBUG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        //MultiDex.install(base);


        // 安装tinker
        Beta.installTinker();
    }
}
