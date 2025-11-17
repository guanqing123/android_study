package com.example.highcontrols.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.highcontrols.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DynamicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DynamicFragment extends Fragment {

    private static final String TAG = "DynamicFragment";

    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "image_id";
    private static final String ARG_PARAM3 = "desc";

    public static DynamicFragment newInstance(int position, int image_id, String desc) {
        DynamicFragment fragment = new DynamicFragment();
        // 把参数打包,传入fragment中
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putInt(ARG_PARAM2, image_id);
        args.putString(ARG_PARAM3, desc);
        fragment.setArguments(args);
        return fragment;
    }

    // 从包裹取出位置序号
    private int getPosition() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getInt(ARG_PARAM1);
        }
        return 0;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "Fragment onAttach position=" + getPosition());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Fragment onCreate position=" + getPosition());
    }

    // 创建碎片视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 根据布局文件 fragment_dynamic.xml 生成视图对象
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            ImageView iv_pic = view.findViewById(R.id.iv_pic);
            iv_pic.setImageResource(arguments.getInt(ARG_PARAM2));
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            tv_desc.setText(arguments.getString(ARG_PARAM3));
        }
        Log.d(TAG, "Fragment onCreateView position=" + getPosition());
        return view;
    }

    // 在活动页面创建之后
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Fragment onActivityCreated position="+ getPosition());
    }

    // 页面启动
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment onStart position="+ getPosition());
    }

    // 页面恢复
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment onResume position="+ getPosition());
    }

    // 页面暂停
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Fragment onPause position="+ getPosition());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Fragment onStop position="+ getPosition());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Fragment onDestroyView position=" + getPosition());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment onDestroy position=" + getPosition());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Fragment onDetach position=" + getPosition());
    }
}