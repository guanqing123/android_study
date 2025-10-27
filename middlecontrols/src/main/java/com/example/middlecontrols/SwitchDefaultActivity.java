package com.example.middlecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SwitchDefaultActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_default);

        Switch sw_status = findViewById(R.id.sw_status);
        sw_status.setOnCheckedChangeListener(this);

        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            tv_result.setText("您已打开开关");
        } else {
            tv_result.setText("您已关闭开关");
        }
    }
}