package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.broadcast.receiver.AlarmReceiver;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private AlarmReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        findViewById(R.id.btn_alarm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        receiver.sendAlarm();
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new AlarmReceiver(getApplicationContext());
        IntentFilter filter = new IntentFilter(AlarmReceiver.ALARM_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}