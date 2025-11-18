package com.example.highcontrols.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.highcontrols.bean.BillInfo;
import com.example.highcontrols.fragment.BillPagerFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillPagerAdapter extends FragmentPagerAdapter {

    private final Map<String, List<BillInfo>> billInfosMap;
    private final List<String> keys = new ArrayList<>();

    public BillPagerAdapter(FragmentManager supportFragmentManager, Map<String, List<BillInfo>> billInfosMap) {
        super(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.billInfosMap = billInfosMap;
        List<String> sortedKeys;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sortedKeys = billInfosMap.keySet().stream()
                    .sorted()
                    .collect(Collectors.toList());
        } else {
            sortedKeys = new ArrayList<>(billInfosMap.keySet());
            Collections.sort(sortedKeys);
        }
        keys.addAll(sortedKeys);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ArrayList<BillInfo> billInfos = new ArrayList<>();
        billInfos.addAll(billInfosMap.get(keys.get(position)));
        return BillPagerFragment.newInstance(position, billInfos);
    }

    @Override
    public int getCount() {
        return billInfosMap.keySet().size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return keys.get(position);
    }
}
