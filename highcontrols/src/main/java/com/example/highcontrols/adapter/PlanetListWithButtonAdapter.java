package com.example.highcontrols.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.highcontrols.R;
import com.example.highcontrols.bean.Planet;

import java.util.List;

public class PlanetListWithButtonAdapter extends BaseAdapter {

    private Context context;
    private List<Planet> planetList;

    public PlanetListWithButtonAdapter(Context context, List<Planet> planetList) {
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
            convertView = View.inflate(context, R.layout.item_list_with_button, null);
            holder = new ViewHolder();
            holder.ll_item = convertView.findViewById(R.id.ll_item);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.btn_oper = convertView.findViewById(R.id.btn_oper);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控制设置好数据
        Planet planet = planetList.get(position);
        // 可阻止下级控件获得焦点,避免堵塞列表视图的点击事件
        holder.ll_item.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        holder.btn_oper.setOnClickListener(v -> {
            Toast.makeText(context, "按钮被点击了," + planet.name, Toast.LENGTH_SHORT).show();
        });
        return convertView;
    }

    public final class ViewHolder {
        public LinearLayout ll_item;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
        public Button btn_oper;
    }
}
