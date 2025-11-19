package com.example.highcontrols.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.highcontrols.R;
import com.example.highcontrols.adapter.BillPagerSysListAdapter;
import com.example.highcontrols.bean.BillInfo;
import com.example.highcontrols.database.BillDBHelper;

import java.util.List;


public class BillPagerSysFragment extends Fragment {

    private static final String ARG_PARAM1 = "yearMonth";

    public static BillPagerSysFragment newInstance(String yearMonth) {
        BillPagerSysFragment fragment = new BillPagerSysFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, yearMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_pager_sys, container, false);
        ListView lv_bill = view.findViewById(R.id.lv_bill);
        BillDBHelper dbHelper = BillDBHelper.getInstance(getContext());
        String yearMonth = getArguments().getString(ARG_PARAM1);
        List<BillInfo> billInfos = dbHelper.queryByMonth(yearMonth);
        BillInfo billInfo = new BillInfo();
        billInfo.date = "合计";
        double balance = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            billInfo.remark = " 收入：" + billInfos.stream()
                    .filter(b -> b.type == BillInfo.BILL_TYPE_INCOME)
                    .mapToDouble(BillInfo::getAmount)
                    .sum() + "元 \n 支出：" + billInfos.stream()
                    .filter(b -> b.type == BillInfo.BILL_TYPE_COST)
                    .mapToDouble(b -> -b.getAmount())
                    .sum() + "元";
            balance = billInfo.amount = billInfos.stream()
                    .mapToDouble(b -> b.type == BillInfo.BILL_TYPE_INCOME ? b.amount : -b.amount)
                    .sum();
        } else {
            double income = 0;
            double cost = 0;
            for (BillInfo bill : billInfos) {
                if (bill.type == BillInfo.BILL_TYPE_INCOME) {
                    income += bill.amount;
                    balance += bill.amount;
                } else {
                    cost += bill.amount;
                    balance -= bill.amount;
                }
            }
            billInfo.remark = " 收入：" + income + "元 \n 支出：" + cost + "元";
        }
        billInfo.amount = balance;
        billInfos.add(billInfo);
        BillPagerSysListAdapter adapter = new BillPagerSysListAdapter(getContext(), billInfos);
        lv_bill.setAdapter(adapter);
        return view;
    }
}