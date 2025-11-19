package com.example.highcontrols.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.highcontrols.R;
import com.example.highcontrols.bean.BillInfo;

import java.util.List;

public class BillPagerSysListAdapter extends BaseAdapter {

    private final Context context;
    private final List<BillInfo> billInfos;

    public BillPagerSysListAdapter(Context context, List<BillInfo> billInfos) {
        this.context = context;
        this.billInfos = billInfos;
    }

    @Override
    public int getCount() {
        return billInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return billInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return billInfos.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill, null);
            holder = new ViewHolder();
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_remark = convertView.findViewById(R.id.tv_remark);
            holder.tv_amount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BillInfo billInfo = billInfos.get(position);
        holder.tv_date.setText(billInfo.date);
        holder.tv_remark.setText(billInfo.remark);
        if (position == billInfos.size() - 1) {
            holder.tv_amount.setText("余额：" + billInfo.amount);
        } else {
            holder.tv_amount.setText(billInfo.type == BillInfo.BILL_TYPE_INCOME ? "收入：" + billInfo.amount : "支出：" + billInfo.amount);
        }
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_date;
        public TextView tv_remark;
        public TextView tv_amount;
    }
}
