package com.kkpip2022.property.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kkpip2022.property.R;
import com.kkpip2022.property.api.Api;
import com.kkpip2022.property.api.ApiConfig;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.api.TtitCallback;
import com.kkpip2022.property.entity.LendGetFather;
import com.kkpip2022.property.entity.LendResponse;
import com.kkpip2022.property.entity.LendResult;
import com.kkpip2022.property.util.Capture;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LendFragment extends BaseFragment {
    static final int COMPLETED = 0;

    String ScanRes_str;
    TextView itemName_tv;
    TextView fatherName_tv;
    TextView Quantity_tv;
    EditText want_tv;

    Button Scan_btn;
    Button Confirm_btn;

    String FatherName;
    String ItemName;
    String TotalHave;

    public LendFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static LendFragment newInstance() {
        LendFragment fragment = new LendFragment();
        return fragment;
    }

    Handler ReFreshhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here","I am Here");
                itemName_tv.setText(ItemName);
                Quantity_tv.setText(TotalHave);
            }
        }
    };

    Handler ReFreshFNhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here","I am Here");
                fatherName_tv.setText(FatherName);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lend, null);
        Scan_btn = view.findViewById(R.id.lend_qrScan_btn);
        Confirm_btn = view.findViewById(R.id.lend_confirm_btn);
        itemName_tv = (TextView) view.findViewById(R.id.lend_item_title_tv);
        fatherName_tv = (TextView) view.findViewById(R.id.lend_fatherCate_tv);
        Quantity_tv = (TextView) view.findViewById(R.id.lend_have_tv);
        want_tv = (EditText) view.findViewById(R.id.lend_want_et);

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
                //
                intentIntegrator.setBarcodeImageEnabled(true);
                // 初始化扫描程序
                intentIntegrator.initiateScan();
            }
        });

        Confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NumTemp;
                NumTemp = want_tv.getText().toString().trim();
                // 首先需要传参，这里传递用户账号的权限说明，注：在Version 1 版本中无实质意义
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("Num",NumTemp);

                // 获取 网站地址
                String BaseURL = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerAdd
                );

                // 获取 网站地址对应的 端口号
                String ServerPort = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerPort
                );

                String stuID = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfigLoginStuID
                );

                // 传参部分代码
                String ExtURL = "num=" + NumTemp + "&sn=" + ScanRes_str + "&stuID=" + stuID;
                Api.config(BaseURL, ServerPort, ApiConfig.TAKE_OUT, ExtURL, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        // 网络没有问题
                        Gson gson = new Gson();
                        LendResult lendResult = gson.fromJson(res,LendResult.class);
                        if (lendResult.getData().getCode() == 1) {
                            // 借出成功
                            showToastSync("借出成功");
                        }
                        showToastSync("借出失败，请检查物品和数量");
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // 工作线程
    private class ReFreshNQ extends Thread {

        public void run() {
            //......处理比较耗时的操作

            //处理完成后给handler发送消息
            Message msg = new Message();
            msg.what = COMPLETED;
            ReFreshhandler.sendMessage(msg);
        }
    }

    private class ReFreshFN extends Thread {

        public void run() {
            //......处理比较耗时的操作

            //处理完成后给handler发送消息
            Message msg = new Message();
            msg.what = COMPLETED;
            ReFreshFNhandler.sendMessage(msg);
        }
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
                // 扫描的为 SN 码
                ScanRes_str = result.getContents();

                // 首先需要传参，这里传递用户账号的权限说明，注：在Version 1 版本中无实质意义
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("sn",ScanRes_str);

                // 获取 网站地址
                String BaseURL = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerAdd
                );

                // 获取 网站地址对应的 端口号
                String ServerPort = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerPort
                );

                // 传参部分代码
                String ExtURL = "sn=" + ScanRes_str;
                showToast(ScanRes_str);

                Api.config(BaseURL, ServerPort, ApiConfig.SEARCH_SNCODE, ExtURL, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        // 网络连接成功
                        Log.e("connection success:", res);
                        // 创建 Gson 方法
                        Gson gson = new Gson();
                        LendResponse lendResponse = gson.fromJson(res,LendResponse.class);
                        if (lendResponse.getData() != null) {
                            // data 数据返回不为空
                            ItemName = lendResponse.getData().getItemName();
                            TotalHave = lendResponse.getData().getQuantity();

                            // 刷新 View 需要使用创建 View 的线程，不能使用子线程，因此使用 WorkThread 方法
                            // 对 View 的数据进行刷新
                            new ReFreshNQ().start();

                            // 首先需要传参，这里传递用户账号的权限说明，注：在Version 1 版本中无实质意义
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("id",lendResponse.getData().getCateid());

                            // 获取 网站地址
                            String BaseURL = GetStringSharedPreferencesContains(
                                    SharedPreferenceDefault.SharedPreferenceSysConfName,
                                    SharedPreferenceDefault.SharedPreferenceSysConfServerAdd
                            );

                            // 获取 网站地址对应的 端口号
                            String ServerPort = GetStringSharedPreferencesContains(
                                    SharedPreferenceDefault.SharedPreferenceSysConfName,
                                    SharedPreferenceDefault.SharedPreferenceSysConfServerPort
                            );

                            String ExtURL = "id=" + lendResponse.getData().getCateid();
                            Api.config(BaseURL, ServerPort, ApiConfig.GET_FATHER_NAME, ExtURL, params).postRequest(new TtitCallback() {
                                @Override
                                public void onSuccess(String res) {
                                    Gson gson = new Gson();
                                    LendGetFather lendGetFather = gson.fromJson(res,LendGetFather.class);
                                    FatherName = lendGetFather.getData().getCategoryName();
                                    new ReFreshFN().start();
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });

                        }
                        // data 数据返回为空
                        // 说明数据库没有这个物品
                    }

                    @Override
                    public void onFailure(Exception e) {
                    }
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}