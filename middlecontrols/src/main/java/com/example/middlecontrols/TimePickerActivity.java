package com.example.middlecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_time;
    private TimePicker tp_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        findViewById(R.id.btn_time).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        tv_time = findViewById(R.id.tv_time);
        tp_time = findViewById(R.id.tp_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:{
                String time = String.format("您选择的时间为: %02d:%02d", tp_time.getCurrentHour(), tp_time.getCurrentMinute());
                tv_time.setText(time);
                break;
            }
            case R.id.btn_time:{
                // 获取日历的一个实例,里面包含了当前的时分秒
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                    String time = String.format("您选择时间是: %02d:%02d", hourOfDay, minute);
                    tv_time.setText(time);
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                dialog.show();
                break;
            }
        }
    }
}