package com.kkpip2022.property.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.kkpip2022.property.R;
import com.kkpip2022.property.api.Api;
import com.kkpip2022.property.api.ApiConfig;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.api.TtitCallback;
import com.kkpip2022.property.entity.CateListDetail;
import com.kkpip2022.property.entity.CateListResponse;
import com.kkpip2022.property.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDetail extends BaseActivity {

    static final int COMPLETED = 0;

    SimpleAdapter DetailList_adapter;

    private Button Back_btn;

    ListView VerticalListView_lv;

    private List<Map<String, Object>> CateItem = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_category_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        Back_btn = (Button) findViewById(R.id.cateItemDetail_back_btn);

        // 返回至上一页
        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 绑定 ListView
        VerticalListView_lv = findViewById(R.id.cateItemDetail_listview);

        // 设置适配器 Adapter
        DetailList_adapter = new SimpleAdapter(CategoryDetail.this,getData(),
                R.layout.son_cate_detail,new String[]{"title","totalNum"},
                new int[]{R.id.son_cate_title_tv,R.id.son_cate_total_tv});

        // 设置适配器
        VerticalListView_lv.setAdapter(DetailList_adapter);

    }

    private List<Map<String, Object>> getData() {
        // 需要考虑传参的问题，在 Version 1 中没有实质意义
        HashMap<String,Object> params = new HashMap<>();
        params.put("none","none");

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
        String ExtURL = "cateid=" + ( navigateGetIntVal() + 1 );

        Log.e("Request Number:",ExtURL);

        Api.config(BaseURL, ServerPort, ApiConfig.GET_CATE_SON_ITEM, ExtURL, params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                // 如果网络没有错误
                Log.e("connection success:", res);
                // 创建 Gson 方法
                Gson gson = new Gson();
                // 从返回值中获取 Data 数据
                CateListResponse cateListResponse = gson.fromJson(res,CateListResponse.class);
                // 返回的 getData 是一个列表
                if (cateListResponse.getData() != null) {
                    // 如果 data 有数据
                    // 将 data 数据赋值给 ListData
                    List<String> ListData = cateListResponse.getData();
                    Log.e("This is List",String.valueOf(ListData));
                    for (int i = 0; i < ListData.size(); i++) {
                        String TempData = (String) String.valueOf(ListData.get(i));
                        Log.e("Item",TempData);
                        CateListDetail cateListDetail = gson.fromJson(TempData,CateListDetail.class);
                        Map map = new HashMap();
                        map.put("title",cateListDetail.getItemName());
                        map.put("totalNum",cateListDetail.getQuantity());
                        CateItem.add(map);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        return CateItem;
    }

}