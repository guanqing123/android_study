package com.example.simplecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ButtonEnableActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_enable;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_enable);
        Button to_enable_btn =  findViewById(R.id.to_enable_btn);
        to_enable_btn.setOnClickListener(this);
        Button to_disable_btn =  findViewById(R.id.to_disable_btn);
        to_disable_btn.setOnClickListener(this);
        btn_enable = findViewById(R.id.btn_enable);
        btn_enable.setOnClickListener(this);
        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.to_enable_btn) {
            btn_enable.setEnabled(true);
        } else if (v.getId() == R.id.to_disable_btn) {
            btn_enable.setEnabled(false);
        } else if (v.getId() == R.id.btn_enable) {
            String desc = String.format("%s 您点击了按钮 %s", new SimpleDateFormat("HH:mm:ss").format(new Date()), ((Button)v).getText());
            tv_result.setText(desc);
        }*/
        switch (v.getId()) {
            case R.id.to_enable_btn:
                btn_enable.setEnabled(true);
                break;
            case R.id.to_disable_btn:
                btn_enable.setEnabled(false);
                break;
            case R.id.btn_enable:
                String desc = String.format("%s 您点击了按钮 %s", new SimpleDateFormat("HH:mm:ss").format(new Date()), ((Button)v).getText());
                tv_result.setText(desc);
                break;
        }
    }
}