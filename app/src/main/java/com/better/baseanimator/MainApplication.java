package com.better.baseanimator;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by huiyong on 2017/12/27.
 */

public class MainApplication extends Application {
    private String BUGLY_ID = "306e322e87";

    @Override
    public void onCreate() {
        super.onCreate();

        //Bugly 异常上报
        CrashReport.initCrashReport(getApplicationContext(), BUGLY_ID, BuildConfig.DEBUG);
    }
}
