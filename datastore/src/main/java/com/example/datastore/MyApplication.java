package com.example.datastore;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class MyApplication extends Application {

    private static MyApplication mApp;
    // 声明一个公共的信息映射对象,可当作全局变量使用
    public HashMap<String, String> infoMap = new HashMap<>();

    public static MyApplication getInstance() {
        return mApp;
    }

    // 在App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Log.d("gq", "MyApplication onCreate");
    }

    // 在App终止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("gq", "MyApplication onTerminate");
    }

    // 在配置改变时调用,例如从竖屏切换到横屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("gq", "MyApplication onConfigurationChanged");
    }
}
