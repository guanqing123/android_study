package com.example.highcontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.highcontrols.adapter.ConvertViewAdapter;
import com.example.highcontrols.adapter.PlanetBaseAdapter;
import com.example.highcontrols.bean.Planet;

import java.util.List;

public class ConvertViewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner sp_planet;
    private List<Planet> planets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_view);

        sp_planet = findViewById(R.id.sp_planet);
        planets = Planet.getDefaultList();
        ConvertViewAdapter adapter = new ConvertViewAdapter(this, planets);
        sp_planet.setAdapter(adapter);
        sp_planet.setSelection(0);
        sp_planet.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "您选择的是：" + planets.get(position).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}