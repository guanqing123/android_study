package com.example.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// 定义一个标准广播的接收器
public class StandardReceiver extends BroadcastReceiver {

    public static final String STANDARD_ACTION = "com.example.broadcast.standard";

    // 一旦接收到标准广播，马上触发接收器的onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(STANDARD_ACTION)) {
            Log.d("gq", "StandardReceiver 接收到标准广播");
        }
    }
}
