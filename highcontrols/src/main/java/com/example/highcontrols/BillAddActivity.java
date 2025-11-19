package com.example.highcontrols;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.highcontrols.bean.BillInfo;
import com.example.highcontrols.database.BillDBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BillAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView tv_date;
    private Calendar calendar;
    private RadioGroup rg_type;
    private EditText et_remark;
    private EditText et_amount;
    private BillDBHelper dbHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
/*        findViewById(R.id.iv_back).setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_option.setOnClickListener(this);
        tv_title.setText("请填写账单");
        tv_option.setText("账单列表");*/

        setupToolbar();

        tv_date = findViewById(R.id.tv_date);
        rg_type = findViewById(R.id.rg_type);
        et_remark = findViewById(R.id.et_remark);
        et_amount = findViewById(R.id.et_amount);
        findViewById(R.id.btn_save).setOnClickListener(this);

        // 显示当前日期
        calendar = Calendar.getInstance();
        tv_date.setText(getDate());
        // 点击弹出日期对话框
        tv_date.setOnClickListener(this);

        dbHelper = BillDBHelper.getInstance(this);
        dbHelper.openReadLink();
        dbHelper.openWriteLink();
        Log.d("gq", "BillAddActiviy onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeLink();
        Log.d("gq", "BillAddActiviy onDestroy");
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 隐藏标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 显示返回按钮（如果需要）
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 返回按钮的点击事件
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_owner: {
                Intent intent = new Intent(this, BillPagerActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_system: {
                Intent intent = new Intent(this, BillPagerSysActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date: { // 弹出日期对话框
                DatePickerDialog dialog = new DatePickerDialog(this, this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            }
            case R.id.btn_save: { // 保存订单信息
                BillInfo billInfo = new BillInfo();
                billInfo.date = tv_date.getText().toString();
                billInfo.type = rg_type.getCheckedRadioButtonId() == R.id.rb_income ? BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COST;
                billInfo.remark = et_remark.getText().toString();
                billInfo.amount = Double.parseDouble(et_amount.getText().toString());
                if (dbHelper.saveBillInfo(billInfo)>0) {
                    Toast.makeText(this, "添加账单成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.tv_option: {
                Intent intent = new Intent(this, BillPagerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.iv_back: {
                finish();
                break;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 设置给文本显示
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_date.setText(getDate());
    }

    private String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }
}