package com.example.middlecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_drawable_shape).setOnClickListener(this);
        findViewById(R.id.btn_drawable_nine).setOnClickListener(this);
        findViewById(R.id.btn_drawable_state).setOnClickListener(this);
        findViewById(R.id.btn_checkbox).setOnClickListener(this);
        findViewById(R.id.btn_switch_default).setOnClickListener(this);
        findViewById(R.id.btn_switch_ios).setOnClickListener(this);
        findViewById(R.id.btn_radio_horizontal).setOnClickListener(this);
        findViewById(R.id.btn_edit_simple).setOnClickListener(this);
        findViewById(R.id.btn_edit_border).setOnClickListener(this);
        findViewById(R.id.btn_edit_focus).setOnClickListener(this);
        findViewById(R.id.btn_alert_dialog).setOnClickListener(this);
        findViewById(R.id.btn_date_picker).setOnClickListener(this);
        findViewById(R.id.btn_time_picker).setOnClickListener(this);
        findViewById(R.id.btn_login_main).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_drawable_shape:{
                Intent intent = new Intent();
                intent.setClass(this, DrawableShapeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawable_nine:{
                Intent intent = new Intent();
                intent.setClass(this, DrawableNineActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_drawable_state:{
                Intent intent = new Intent();
                intent.setClass(this, DrawableStateActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_checkbox:{
                Intent intent = new Intent();
                intent.setClass(this, CheckBoxActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_switch_default:{
                Intent intent = new Intent();
                intent.setClass(this, SwitchDefaultActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_switch_ios:{
                Intent intent = new Intent();
                intent.setClass(this, SwitchIOSActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_radio_horizontal:{
                Intent intent = new Intent();
                intent.setClass(this, RadioHorizontalActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_edit_simple:{
                Intent intent = new Intent();
                intent.setClass(this, EditSimpleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_edit_border:{
                Intent intent = new Intent();
                intent.setClass(this, EditBorderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_edit_focus:{
                Intent intent = new Intent();
                intent.setClass(this, EditFocusActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_alert_dialog:{
                Intent intent = new Intent();
                intent.setClass(this, AlertDialogActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_date_picker:{
                Intent intent = new Intent();
                intent.setClass(this, DatePickerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_time_picker:{
                Intent intent = new Intent();
                intent.setClass(this, TimePickerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_login_main:{
                Intent intent = new Intent();
                intent.setClass(this, LoginMainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}