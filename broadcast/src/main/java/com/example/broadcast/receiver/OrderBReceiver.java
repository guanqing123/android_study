package com.example.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.broadcast.BroadOrderActivity;

public class OrderBReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(BroadOrderActivity.ORDER_ACTION)) {
            Log.d("gq", "OrderBReceiver 接收到有序广播");
            abortBroadcast();
        }
    }
}
