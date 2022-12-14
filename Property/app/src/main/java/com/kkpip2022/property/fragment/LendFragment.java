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

    @SuppressLint("HandlerLeak")
    Handler ReFreshhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here","I am Here");
                itemName_tv.setText(ItemName);
                Quantity_tv.setText(TotalHave);
            }
        }
    };

    @SuppressLint("HandlerLeak")
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
                // ????????? intent integrator
                IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(LendFragment.this);
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

        Confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NumTemp;
                NumTemp = want_tv.getText().toString().trim();
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

                // ??????????????????
                String ExtURL = "num=" + NumTemp + "&sn=" + ScanRes_str + "&stuID=" + stuID;
                Api.config(BaseURL, ServerPort, ApiConfig.TAKE_OUT, ExtURL, params).postRequest(new TtitCallback() {
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

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // ????????????
    private class ReFreshNQ extends Thread {

        public void run() {
            //......???????????????????????????

            //??????????????????handler????????????
            Message msg = new Message();
            msg.what = COMPLETED;
            ReFreshhandler.sendMessage(msg);
        }
    }

    private class ReFreshFN extends Thread {

        public void run() {
            //......???????????????????????????

            //??????????????????handler????????????
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
                Log.e("??????", result.getContents());
                // ???????????? SN ???
                ScanRes_str = result.getContents();

                // ????????????????????????????????????????????????????????????????????????Version 1 ????????????????????????
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("sn",ScanRes_str);

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

                // ??????????????????
                String ExtURL = "sn=" + ScanRes_str;
                showToast(ScanRes_str);

                Api.config(BaseURL, ServerPort, ApiConfig.SEARCH_SNCODE, ExtURL, params).postRequest(new TtitCallback() {
                    @Override
                    public void onSuccess(String res) {
                        // ??????????????????
                        Log.e("connection success:", res);
                        // ?????? Gson ??????
                        Gson gson = new Gson();
                        LendResponse lendResponse = gson.fromJson(res,LendResponse.class);
                        if (lendResponse.getData() != null) {
                            // data ?????????????????????
                            ItemName = lendResponse.getData().getItemName();
                            TotalHave = lendResponse.getData().getQuantity();

                            // ?????? View ?????????????????? View ???????????????????????????????????????????????? WorkThread ??????
                            // ??? View ?????????????????????
                            new ReFreshNQ().start();

                            // ????????????????????????????????????????????????????????????????????????Version 1 ????????????????????????
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("id",lendResponse.getData().getCateid());

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
                        // data ??????????????????
                        // ?????????????????????????????????
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