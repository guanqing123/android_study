package com.example.highcontrols.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.highcontrols.R;
import com.example.highcontrols.bean.CartInfo;

import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {


    private final Context context;

    private List<CartInfo> cartInfos;

    public void setCartInfos(List<CartInfo> cartInfos) {
        this.cartInfos = cartInfos;
    }

    public ShoppingCartAdapter(Context  context, List<CartInfo> cartInfos) {
        this.context = context;
        this.cartInfos = cartInfos;
    }

    @Override
    public int getCount() {
        return cartInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return cartInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cartInfos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, null);
            holder = new ViewHolder();
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.tv_count = convertView.findViewById(R.id.tv_count);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.tv_sum = convertView.findViewById(R.id.tv_sum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartInfo cartInfo = cartInfos.get(position);
        holder.iv_thumb.setImageURI(Uri.parse(cartInfo.getGoodsInfo().getPicPath()));
        holder.tv_name.setText(cartInfo.getGoodsInfo().getName());
        holder.tv_desc.setText(cartInfo.getGoodsInfo().getDescription());
        holder.tv_count.setText("x" + cartInfo.getCount());
        holder.tv_price.setText(cartInfo.getGoodsInfo().getPrice()+"");
        // 设置商品总价
        holder.tv_sum.setText(cartInfo.getGoodsInfo().getPrice() * cartInfo.getCount()+"");
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_desc;
        public TextView tv_count;
        public TextView tv_price;
        public TextView tv_sum;
    }
}
