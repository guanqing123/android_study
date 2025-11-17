package com.example.highcontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.highcontrols.adapter.PlanetGridAdapter;
import com.example.highcontrols.bean.Planet;

import java.util.List;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gv_planet;
    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        gv_planet = findViewById(R.id.gv_planet);
        planetList = Planet.getDefaultList();
        PlanetGridAdapter adapter = new PlanetGridAdapter(this, planetList);
        gv_planet.setAdapter(adapter);
        gv_planet.setOnItemClickListener(this);
        gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "您选择了" + planetList.get(position).name, Toast.LENGTH_SHORT).show();
    }
}