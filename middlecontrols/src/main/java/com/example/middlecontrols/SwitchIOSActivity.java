package com.example.middlecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class SwitchIOSActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_iosactivity);

        CheckBox sw_status = findViewById(R.id.ck_ios);
        sw_status.setOnCheckedChangeListener(this);

        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        tv_result.setText(isChecked ? "您已打开开关" : "您已关闭开关");
    }
}