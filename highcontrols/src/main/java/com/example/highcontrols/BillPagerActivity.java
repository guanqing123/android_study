package com.example.highcontrols;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.highcontrols.adapter.BillPagerAdapter;
import com.example.highcontrols.bean.BillInfo;
import com.example.highcontrols.database.BillDBHelper;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private BillDBHelper dbHelper;
    private Calendar calendar;
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager);
        findViewById(R.id.iv_back).setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("账单列表");
        TextView tv_option = findViewById(R.id.tv_option);
        tv_option.setText("添加账单");
        tv_option.setOnClickListener(this);

        tv_date = findViewById(R.id.tv_date);
        calendar = Calendar.getInstance();
        tv_date.setText(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        tv_date.setOnClickListener(this);

        dbHelper = BillDBHelper.getInstance(this);
        dbHelper.openWriteLink();
        dbHelper.openReadLink();
        Log.d("gq", "BillPagerActivity onCreate");

        initViewPager();
        initPagerTabStrip();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("gq", "BillPagerActivity onDestroy");
    }

    private void initViewPager() {
        ViewPager vp_bill = findViewById(R.id.vp_bill);

        // 获取当前年月 2025-06
        Calendar calendar = Calendar.getInstance();
        String begin = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH);
        String middle = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
        String end = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 2);
        List<BillInfo> billInfos = dbHelper.getBillInfos(begin, end);

        Map<String, List<BillInfo>> billInfosMap = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                billInfosMap = billInfos.stream()
                    .collect(Collectors.groupingBy(billInfo -> billInfo.date.substring(0, 7)));
        } else {
            billInfosMap = new HashMap<>();
            for (BillInfo billInfo : billInfos) {
                String key = billInfo.date.substring(0, 7);
                if (!billInfosMap.containsKey(key)) {
                    List<BillInfo> list = new ArrayList<>();
                    list.add(billInfo);
                    billInfosMap.put(key, list);
                } else {
                    billInfosMap.get(key).add(billInfo);
                }
            }
        }
        // 如果 billInfosMap 的 keys 没有全部包含 begin, middle, end, 需要添加进去
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Map<String, List<BillInfo>> finalBillInfosMap = billInfosMap;
            Arrays.asList(begin, middle, end).forEach(month -> {
                if (!finalBillInfosMap.containsKey(month)) {
                    finalBillInfosMap.put(month, new ArrayList<>());
                }
            });
        } else {
            for (String month : Arrays.asList(begin, middle, end)) {
                if (!billInfosMap.containsKey(month)) {
                    billInfosMap.put(month, new ArrayList<>());
                }
            }
        }
        BillPagerAdapter adapter = new BillPagerAdapter(getSupportFragmentManager(), billInfosMap);
        vp_bill.setAdapter(adapter);
        vp_bill.setCurrentItem(1);
    }

    private void initPagerTabStrip() {
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        pts_bill.setTextColor(Color.BLACK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.tv_date: {
                // 创建 MonthYearPickerDialog
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(this, (selectedMonth, selectedYear) -> {
                    Log.d("selectedYear", selectedYear + "\t selectedMonth" + selectedMonth);
                    calendar.set(selectedYear, selectedMonth, 1);
                    tv_date.setText(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                builder.build().show();
                break;
            }
        }
    }
}