package com.example.highcontrols.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.highcontrols.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchFragment extends Fragment {

    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "count";
    private static final String ARG_PARAM3 = "image_id";
    public static LaunchFragment newInstance(int position, int count, int image_id) {
        LaunchFragment fragment = new LaunchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putInt(ARG_PARAM2, count);
        args.putInt(ARG_PARAM3, image_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Context context = getContext();
            int position = arguments.getInt(ARG_PARAM1);
            int count = arguments.getInt(ARG_PARAM2);
            int image_id = arguments.getInt(ARG_PARAM3);
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);
            iv_launch.setImageResource(image_id);
            // 每个页面都分配一组对应的单选按钮
            for (int j = 0; j < count; j++) {
                RadioButton radio = new RadioButton(context);
                radio.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                radio.setPadding(10, 10, 10, 10);
                rg_indicate.addView(radio);
            }

            // 当前位置的单选按钮要高亮显示,比如第二个引导页就高亮第二个单选按钮
            rg_indicate.check(rg_indicate.getChildAt(position).getId());

            // 如果是最后一个引导页,则显示入口按钮,以便用户点击按钮进入主页
            if (position == count - 1) {
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(v -> {
                    Toast.makeText(view.getContext(), "欢迎开启美好生活", Toast.LENGTH_SHORT).show();
                });
            }
        }
        return view;
    }
}