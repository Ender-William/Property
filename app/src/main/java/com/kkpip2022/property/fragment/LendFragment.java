package com.kkpip2022.property.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kkpip2022.property.R;
import com.kkpip2022.property.util.Capture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LendFragment extends BaseFragment {

    private Button Scan_btn;

    public LendFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static LendFragment newInstance() {
        LendFragment fragment = new LendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lend, null);
        Scan_btn = view.findViewById(R.id.lend_qrScan_btn);
        Scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 初始化 intent integrator
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(LendFragment.this);
                // 设置扫描界面的提示词
                intentIntegrator.setPrompt("For Flash use volume up key");
                // 设置蜂鸣器效果
                intentIntegrator.setBeepEnabled(true);
                // 停止界面旋转
                intentIntegrator.setOrientationLocked(true);
                // 设置扫描时用到的 Activity，在这个界面调用 Camera
                intentIntegrator.setCaptureActivity(Capture.class);
                // 初始化扫描程序
                intentIntegrator.initiateScan();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Log.e("扫码", result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}