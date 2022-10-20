package com.kkpip2022.property.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.kkpip2022.property.R;
import com.kkpip2022.property.api.Api;
import com.kkpip2022.property.api.ApiConfig;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.api.TtitCallback;
import com.kkpip2022.property.entity.CateListDetail;
import com.kkpip2022.property.entity.GetHistoryDetail;
import com.kkpip2022.property.entity.GetHistoryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class history_operate extends BaseActivity {

    static final int COMPLETED = 0;

    SimpleAdapter adapter;

    ListView VerticalListView_lv;

    private List<Map<String, Object>> CateItem = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_history_operate;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        VerticalListView_lv = findViewById(R.id.history_history_lv);

        new WorkThread().start();

    }

    private class WorkThread extends Thread {
        public void run() {
            // 需要考虑传参的问题，在 Version 1 中没有实质意义
            HashMap<String, Object> params = new HashMap<>();
            params.put("none", "none");

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

            // 获取 用户ID
            String stuID = GetStringSharedPreferencesContains(
                    SharedPreferenceDefault.SharedPreferenceSysConfName,
                    SharedPreferenceDefault.SharedPreferenceSysConfigLoginStuID
            );

            // 传参部分代码
            String ExtURL = "stuID=" + stuID;

            Api.config(BaseURL, ServerPort, ApiConfig.GET_HISTORY, ExtURL, params).postRequest(new TtitCallback() {
                @Override
                public void onSuccess(String res) {
                    // 网络没有问题

                    Gson gson = new Gson();
                    Log.e("Get RES:",res);
                    GetHistoryResponse getHistoryResponse = gson.fromJson(res, GetHistoryResponse.class);
                    if (getHistoryResponse.getData() != null) {
                        // 如果有数据
                        List<String> ListData = getHistoryResponse.getData();
                        for (int i = 0; i < ListData.size(); i++) {
                            String TempData = (String) String.valueOf(ListData.get(i));
                            GetHistoryDetail getHistoryDetail = gson.fromJson(TempData, GetHistoryDetail.class);
                            Map map = new HashMap();
                            map.put("num", getHistoryDetail.getLoanQuantity());
                            map.put("title", getHistoryDetail.getItemName());
                            CateItem.add(map);
                        }
                    }
                    //处理完成后给handler发送消息
                    Message msg = new Message();
                    msg.what = COMPLETED;
                    ReFreshhandler.sendMessage(msg);
                }

                @Override
                public void onFailure(Exception e) {

                }
            });

        }
    }

    @SuppressLint("HandlerLeak")
    Handler ReFreshhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here", "I am Here");

                // 设置适配器 Adapter
                adapter = new SimpleAdapter(history_operate.this,CateItem,
                        R.layout.history_operate_detail,new String[]{"title","num"},
                        new int[]{R.id.historydetail_itemName_tv,R.id.historydetail_num_tv});

                VerticalListView_lv.setAdapter(adapter);
            }
        }
    };

}