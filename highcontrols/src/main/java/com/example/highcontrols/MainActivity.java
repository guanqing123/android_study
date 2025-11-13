package com.example.highcontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_spinner_dropdown).setOnClickListener(this);
        findViewById(R.id.btn_spinner_dialog).setOnClickListener(this);
        findViewById(R.id.btn_spinner_icon).setOnClickListener(this);
        findViewById(R.id.btn_base_adapter).setOnClickListener(this);
        findViewById(R.id.btn_convert_view).setOnClickListener(this);
        findViewById(R.id.btn_list_view).setOnClickListener(this);
        findViewById(R.id.btn_list_focus).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_spinner_dropdown:{
                Intent intent = new Intent(this, SpinnerDropdownActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_spinner_dialog:{
                Intent intent = new Intent(this, SpinnerDialogActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_spinner_icon:{
                Intent intent = new Intent(this, SpinnerIconActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_base_adapter:{
                Intent intent = new Intent(this, BaseAdapterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_convert_view:{
                Intent intent = new Intent(this, ConvertViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_list_view:{
                Intent intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_list_focus:{
                Intent intent = new Intent(this, ListFocusActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}