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

        // ????????????
        qrScan_btn = view.findViewById(R.id.return_qrScan_btn);
        confirm_btn = view.findViewById(R.id.return_confirm_btn);

        itemName_tv = view.findViewById(R.id.return_itemName_tv);
        returnNum_et = view.findViewById(R.id.return_return_num_et);

        fatherName_tv = view.findViewById(R.id.return_father_cate_tv);

        qrScan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ????????? intent integrator
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ReturnFragment.this);
                // ??????????????????????????? QR_CODE_TYPES ??????????????????ONE_D_CODE_TYPES ???????????????
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                // ??????????????????????????????
                intentIntegrator.setPrompt("????????????????????????????????????");
                // ?????????????????????
                intentIntegrator.setBeepEnabled(true);
                // ??????????????????
                intentIntegrator.setOrientationLocked(true);
                // ???????????????????????? Activity???????????????????????? Camera
                intentIntegrator.setCaptureActivity(Capture.class);
                //
                intentIntegrator.setBarcodeImageEnabled(true);
                // ?????????????????????
                intentIntegrator.initiateScan();
            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NumTemp;
                NumTemp = returnNum_et.getText().toString().trim();
                // ????????????????????????????????????????????????????????????????????????Version 1 ????????????????????????
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("Num",NumTemp);

                // ?????? ????????????
                String BaseURL = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerAdd
                );

                // ?????? ????????????????????? ?????????
                String ServerPort = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerPort
                );

                String stuID = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfigLoginStuID
                );

                // ??????
                String ExtURL = "num=" + NumTemp + "&sn=" + ScanRes_str + "&stuID=" + stuID;
                Api.config(BaseURL, ServerPort, ApiConfig.RETURN_IN, ExtURL, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        // ??????????????????
                        Gson gson = new Gson();

                        LendResult lendResult = gson.fromJson(res,LendResult.class);
                        if (lendResult.getData().getCode() == 1) {
                            // ????????????
                            showToastSync("????????????");
                        }
                        showToastSync("???????????????????????????????????????");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // ??????????????????

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // ????????????
    @SuppressLint("HandlerLeak")
    Handler LoadItemNamehandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here","I am Here");
                itemName_tv.setText(ItemName_str);
            }
        }
    };

    // ????????????
    @SuppressLint("HandlerLeak")
    Handler LoadFatherNamehandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here","I am Here");
                fatherName_tv.setText(FatherName_str);
            }
        }
    };

    // ????????????????????????
    private class LoadItemNameTh extends Thread {

        public void run() {
            //......???????????????????????????

            //??????????????????handler????????????
            Message msg = new Message();
            msg.what = COMPLETED;
            LoadItemNamehandler.sendMessage(msg);
        }
    }

    // ????????????????????????
    private class LoadFatherNameTh extends Thread {

        public void run() {
            //......???????????????????????????

            //??????????????????handler????????????
            Message msg = new Message();
            msg.what = COMPLETED;
            LoadFatherNamehandler.sendMessage(msg);
        }
    }

    // ?????? ZXing ??????????????????
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // ???????????????????????????
                //Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            } else {
                // ???????????????
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Log.e("Line Bar Scan Res: ", result.getContents());
                // ???????????????????????? ScanRes_str
                ScanRes_str = result.getContents();

                new LoadItemNameTh().start();

                // ????????????????????????????????????????????????????????????????????????Version 1 ????????????????????????
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("sn",ScanRes_str);

                // ?????? ???????????????
                String BaseURL = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerAdd
                );

                // ?????????????????????????????? ?????????
                String ServerPort = GetStringSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerPort
                );

                // ????????????
                String ExtURL = "sn=" + ScanRes_str;
                showToast(ScanRes_str);

                Api.config(BaseURL, ServerPort, ApiConfig.SEARCH_SNCODE, ExtURL, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        // ??????????????????
                        Log.e("connection success:", res);
                        // ?????? Gson ??????
                        Gson gson = new Gson();
                        ReturninResponse returninResponse = gson.fromJson(res,ReturninResponse.class);
                        if (returninResponse.getData() != null) {
                            // ??????????????????????????????
                            ItemName_str = returninResponse.getData().getItemName();

                            // ????????????????????????
                            // ????????????????????????????????????????????????????????????????????????Version 1 ????????????????????????
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("id",returninResponse.getData().getCateid());

                            // ?????? ????????????
                            String BaseURL = GetStringSharedPreferencesContains(
                                    SharedPreferenceDefault.SharedPreferenceSysConfName,
                                    SharedPreferenceDefault.SharedPreferenceSysConfServerAdd
                            );

                            // ?????? ????????????????????? ?????????
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