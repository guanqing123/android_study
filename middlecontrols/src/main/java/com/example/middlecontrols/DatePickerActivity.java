package com.example.middlecontrols;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePicker dp_date;
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_date).setOnClickListener(this);
        tv_date = findViewById(R.id.tv_date);
        dp_date = findViewById(R.id.dp_date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok: {
                String desc = String.format("您选择的日期是%d年%d月%d日", dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth());
                tv_date.setText(desc);
                break;
            }
            case R.id.btn_date: {
                /*Calendar calendar = Calendar.getInstance();
                calendar.get(Calendar.YEAR);
                calendar.get(Calendar.MONTH);
                calendar.get(Calendar.DAY_OF_MONTH);*/
                DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                    String desc = String.format("您选择的日期是%d年%d月%d日", year, month + 1, dayOfMonth);
                    tv_date.setText(desc);
                }, 2025, 10, 29);
                dialog.show();
                break;
            }
        }
    }
}