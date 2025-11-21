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
        }
    }
}