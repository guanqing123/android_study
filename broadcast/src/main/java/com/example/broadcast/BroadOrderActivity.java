package com.example.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.broadcast.receiver.OrderAReceiver;
import com.example.broadcast.receiver.OrderBReceiver;

public class BroadOrderActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ORDER_ACTION = "com.example.broadcast.order";
    private OrderAReceiver receiverA;
    private OrderBReceiver receiverB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_order);
        findViewById(R.id.btn_send_order_broad).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_order_broad:{
                // 创建一个指定动作的意图
                Intent intent = new Intent(ORDER_ACTION);
                // 发送有序广播
                sendOrderedBroadcast(intent, null);
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 多个接收器处理有序广播的顺序规则为:
        // 1.优先级越大的接收器,越早收到有序广播
        // 2.优先级相同的接收器,越早注册的越早收到有序广播
        receiverA = new OrderAReceiver();
        IntentFilter filterA = new IntentFilter(ORDER_ACTION);
        filterA.setPriority(8);
        registerReceiver(receiverA, filterA);

        receiverB = new OrderBReceiver();
        IntentFilter filterB = new IntentFilter(ORDER_ACTION);
        filterB.setPriority(10);
        registerReceiver(receiverB, filterB);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiverA);
        unregisterReceiver(receiverB);
    }
}