package com.example.highcontrols;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.highcontrols.adapter.ConvertViewAdapter;
import com.example.highcontrols.bean.Planet;
import com.example.highcontrols.util.Utils;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    private List<Planet> planets;
    private CheckBox cb_divider;
    private CheckBox cb_background;
    private ListView lv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv_planet = findViewById(R.id.lv_planet);
        planets = Planet.getDefaultList();
        ConvertViewAdapter adapter = new ConvertViewAdapter(this, planets);
        lv_planet.setAdapter(adapter);
        lv_planet.setSelection(0);
        lv_planet.setOnItemClickListener(this);

        cb_divider = findViewById(R.id.cb_divider);
        cb_divider.setOnCheckedChangeListener(this);
        cb_background = findViewById(R.id.cb_background);
        cb_background.setOnCheckedChangeListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "您选择了" + planets.get(position).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_divider: {
                // 显示分隔线
                if (isChecked) {
                    Drawable drawable = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        drawable = getResources().getDrawable(R.color.black, getTheme());
                    } else {
                        // 从资源文件获取图形对象
                        drawable = getResources().getDrawable(R.color.black);
                    }
                    lv_planet.setDivider(drawable);
                    // 设置列表视图的分隔线高度
                    lv_planet.setDividerHeight(Utils.dip2px(this, 1f));
                } else {
                    lv_planet.setDivider(null);
                    lv_planet.setDividerHeight(0);
                }
                break;
            }
            case R.id.cb_background: {
                // 显示按压背景
                if (cb_background.isChecked()) {
                    lv_planet.setSelector(R.drawable.list_selector);
                } else {
                    Drawable drawable = getResources().getDrawable(R.color.transparent);
                    lv_planet.setSelector(drawable);
                }
                break;
            }
        }
    }
}