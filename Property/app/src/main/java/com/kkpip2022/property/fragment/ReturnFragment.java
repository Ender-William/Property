package com.kkpip2022.property.fragment;

import android.annotation.SuppressLint;
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
import com.kkpip2022.property.entity.ReturninResponse;
import com.kkpip2022.property.util.Capture;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReturnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReturnFragment extends BaseFragment {

    static final int COMPLETED = 0;

    EditText returnNum_et;

    TextView fatherName_tv;
    TextView itemName_tv;

    Button qrScan_btn;
    Button confirm_btn;

    String ScanRes_str;
    String FatherName_str;
    String ItemName_str;

    public ReturnFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ReturnFragment newInstance() {
        ReturnFragment fragment = new ReturnFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return,null);

        // 绑定控件
        qrScan_btn = view.findViewById(R.id.return_qrScan_btn);
        confirm_btn = view.findViewById(R.id.return_confirm_btn);

        itemName_tv = view.findViewById(R.id.return_itemName_tv);
        returnNum_et = view.findViewById(R.id.return_return_num_et);

        fatherName_tv = view.findViewById(R.id.return_father_cate_tv);

        qrScan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 初始化 intent integrator
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ReturnFragment.this);
                // 设置扫码类型，其中 QR_CODE_TYPES 表示二维码，ONE_D_CODE_TYPES 表示条形码
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                // 设置扫描界面的提示词
                intentIntegrator.setPrompt("按下音量上键以开启闪光灯");
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

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NumTemp;
                NumTemp = returnNum_et.getText().toString().trim();
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

                // 传参
                String ExtURL = "num=" + NumTemp + "&sn=" + ScanRes_str + "&stuID=" + stuID;
                Api.config(BaseURL, ServerPort, ApiConfig.RETURN_IN, ExtURL, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        // 网络没有问题
                        Gson gson = new Gson();

                        LendResult lendResult = gson.fromJson(res,LendResult.class);
                        if (lendResult.getData().getCode() == 1) {
                            // 借出成功
                            showToastSync("归还成功");
                        }
                        showToastSync("归还失败，请检查物品和数量");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // 网络连接失败

                    }
                });


            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // 数据展示
    @SuppressLint("HandlerLeak")
    Handler LoadItemNamehandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here","I am Here");
                itemName_tv.setText(ItemName_str);
            }
        }
    };

    // 数据展示
    @SuppressLint("HandlerLeak")
    Handler LoadFatherNamehandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here","I am Here");
                fatherName_tv.setText(FatherName_str);
            }
        }
    };

    // 数据刷新线程跳板
    private class LoadItemNameTh extends Thread {

        public void run() {
            //......处理比较耗时的操作

            //处理完成后给handler发送消息
            Message msg = new Message();
            msg.what = COMPLETED;
            LoadItemNamehandler.sendMessage(msg);
        }
    }

    // 数据刷新线程跳板
    private class LoadFatherNameTh extends Thread {

        public void run() {
            //......处理比较耗时的操作

            //处理完成后给handler发送消息
            Message msg = new Message();
            msg.what = COMPLETED;
            LoadFatherNamehandler.sendMessage(msg);
        }
    }

    // 使用 ZXing 之后获取参数
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // 没有扫描到任何数据
                //Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            } else {
                // 扫描到数据
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Log.e("Line Bar Scan Res: ", result.getContents());
                // 将扫描结果赋值给 ScanRes_str
                ScanRes_str = result.getContents();

                new LoadItemNameTh().start();

                // 首先需要传参，这里传递用户账号的权限说明，注：在Version 1 版本中无实质意义
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("sn",ScanRes_str);

                // 获取 服务器地址
                String BaseURL = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerAdd
                );

                // 获取服务器地址对应的 端口号
                String ServerPort = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerPort
                );

                // 参数设置
                String ExtURL = "sn=" + ScanRes_str;
                showToast(ScanRes_str);

                Api.config(BaseURL, ServerPort, ApiConfig.SEARCH_SNCODE, ExtURL, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        // 网络连接成功
                        Log.e("connection success:", res);
                        // 创建 Gson 方法
                        Gson gson = new Gson();
                        ReturninResponse returninResponse = gson.fromJson(res,ReturninResponse.class);
                        if (returninResponse.getData() != null) {
                            // 返回的数据数据不为空
                            ItemName_str = returninResponse.getData().getItemName();

                            // 开始获取父类名称
                            // 首先需要传参，这里传递用户账号的权限说明，注：在Version 1 版本中无实质意义
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("id",returninResponse.getData().getCateid());

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

                            String ExtURL = "id=" + returninResponse.getData().getCateid();
                            Api.config(BaseURL, ServerPort, ApiConfig.GET_FATHER_NAME, ExtURL, params).postRequest(new TtitCallback() {
                                @Override
                                public void onSuccess(String res) {
                                    Gson gson = new Gson();
                                    LendGetFather lendGetFather = gson.fromJson(res,LendGetFather.class);
                                    FatherName_str = lendGetFather.getData().getCategoryName();
                                    new LoadFatherNameTh().start();
                                }
                                @Override
                                public void onFailure(Exception e) {
                                }
                            });
                        }
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