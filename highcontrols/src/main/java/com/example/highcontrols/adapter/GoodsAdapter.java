package com.example.highcontrols.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.highcontrols.R;
import com.example.highcontrols.ShoppingChannelActivity;
import com.example.highcontrols.ShoppingDetailActivity;
import com.example.highcontrols.bean.GoodsInfo;

import java.util.List;

public class GoodsAdapter extends BaseAdapter {

    private Context context;
    private List<GoodsInfo> goodsInfos;

    public GoodsAdapter(Context context, List<GoodsInfo> goodsInfos, AddCartListener addCartListener) {
        this.context = context;
        this.goodsInfos = goodsInfos;
        this.addCartListener = addCartListener;
    }

    @Override
    public int getCount() {
        return goodsInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return goodsInfos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // 获取布局文件 item_goods.xml 的根视图
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods,  null);
            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.btn_add = convertView.findViewById(R.id.btn_add);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置商品图片、名称、价格
        GoodsInfo info = goodsInfos.get(position);
        holder.iv_thumb.setImageBitmap(BitmapFactory.decodeFile(info.getPicPath()));
        holder.tv_name.setText(info.getName());
        holder.tv_price.setText("￥" + info.getPrice());

        // 商品图片点击
        holder.iv_thumb.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShoppingDetailActivity.class);
            intent.putExtra("goods_id", info.getId());
            context.startActivity(intent);
        });

        // 加入购物车按钮点击
        holder.btn_add.setOnClickListener(v -> {
            /*ShoppingChannelActivity activity = (ShoppingChannelActivity) context;
            activity.addCart(info);*/
            addCartListener.addCart(info);
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_name;
        public ImageView iv_thumb;
        public TextView tv_price;
        public Button btn_add;
    }

    // 声明一个加入购物车的监听器对象
    private AddCartListener addCartListener;

    // 定义一个加入购物车的监听器接口
    public interface AddCartListener {
        void addCart(GoodsInfo info);
    }
}
