package com.example.highcontrols.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.highcontrols.R;
import com.example.highcontrols.adapter.BillPagerFragmentSubAdapter;
import com.example.highcontrols.bean.BillInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillPagerFragment extends Fragment {

    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "bills";

    public static BillPagerFragment newInstance(int position , ArrayList<BillInfo> bills) {
        BillPagerFragment fragment = new BillPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putParcelableArrayList(ARG_PARAM2, bills);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_pager, container, false);
        ListView lvBillInfo = view.findViewById(R.id.lv_bill_info);
        BillPagerFragmentSubAdapter adapter = new BillPagerFragmentSubAdapter(getActivity(), getArguments().getParcelableArrayList(ARG_PARAM2));
        lvBillInfo.setAdapter(adapter);
        return view;
    }
}