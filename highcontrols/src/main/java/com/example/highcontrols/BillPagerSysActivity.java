package com.example.highcontrols;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.highcontrols.adapter.BillPagerSysAdapter;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BillPagerSysActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_month;
    private Calendar calendar;
    private ViewPager vp_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager_sys);

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_option.setOnClickListener(this);
        tv_title.setText("账单列表");
        tv_option.setText("添加账单");

        tv_month = findViewById(R.id.tv_month);
        // 显示当前日期
        calendar = Calendar.getInstance();
        tv_month.setText(format("yyyy-MM"));
        tv_month.setOnClickListener(this);

        // 初始化翻页视图
        initViewPager();
        Log.d("gq", "BillPagerSysActivity onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("gq", "BillPagerSysActivity onDestroy");
    }

    // 初始化翻页视图
    private void initViewPager() {
        // 从布局视图中获取名叫pts_bill的翻页标签栏
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        // 设置翻页标签栏的文本大小
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        vp_bill = findViewById(R.id.vp_bill);
        vp_bill.setAdapter(new BillPagerSysAdapter(getSupportFragmentManager(), calendar.get(Calendar.YEAR)));
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
    }

    private String format(String pattern) {
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_month: {
                // 创建 MonthYearPickerDialog
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(this, (selectedMonth, selectedYear) -> {
                    Log.d("selectedYear", selectedYear + "\t selectedMonth" + selectedMonth);
                    calendar.set(selectedYear, selectedMonth, 1);
                    tv_month.setText(format("yyyy-MM"));
                    // 显示翻页视图显示到哪一页
                    vp_bill.setCurrentItem(selectedMonth);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                builder.build().show();
                break;
            }
            case R.id.iv_back: {
                finish();
                break;
            }
            case R.id.tv_option: {
                Intent intent = new Intent(this, BillAddActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
        }
    }
}