package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_broad_standard).setOnClickListener(this);
        findViewById(R.id.btn_broad_order).setOnClickListener(this);
        findViewById(R.id.btn_broad_static).setOnClickListener(this);
        findViewById(R.id.btn_broad_minute).setOnClickListener(this);
        findViewById(R.id.btn_broad_network).setOnClickListener(this);
        findViewById(R.id.btn_broad_alarm).setOnClickListener(this);
        findViewById(R.id.btn_life_cycle).setOnClickListener(this);
        findViewById(R.id.btn_change_direction).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_broad_standard:{
                Intent intent = new Intent(this, BroadStandardActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_broad_order:{
                Intent intent = new Intent(this, BroadOrderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_broad_static:{
                Intent intent = new Intent(this, BroadStaticActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_broad_minute:{
                Intent intent = new Intent(this, SystemMinuteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_broad_network:{
                Intent intent = new Intent(this, SystemNetworkActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_broad_alarm:{
                Intent intent = new Intent(this, AlarmActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_life_cycle:{
                Intent intent = new Intent(this, LifeCycleTestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_change_direction:{
                Intent intent = new Intent(this, ChangeDirectionActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}