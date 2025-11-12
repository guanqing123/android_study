package com.example.highcontrols.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.highcontrols.R;
import com.example.highcontrols.bean.Planet;

import java.util.List;

public class ConvertViewAdapter extends BaseAdapter {

    private Context context;
    private List<Planet> planetList;

    public ConvertViewAdapter(Context context, List<Planet> planetList) {
        this.context = context;
        this.planetList = planetList;
    }

    @Override
    public int getCount() {
        return planetList.size();
    }

    @Override
    public Object getItem(int position) {
        return planetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = View.inflate(context, R.layout.item_list, null);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Planet planet = planetList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        return convertView;
    }

    public final class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }
}
