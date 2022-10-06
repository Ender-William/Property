package com.kkpip2022.property.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.kkpip2022.property.R;
import com.kkpip2022.property.api.Api;
import com.kkpip2022.property.api.ApiConfig;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.api.TtitCallback;
import com.kkpip2022.property.entity.CategoryItemDetail;
import com.kkpip2022.property.entity.CategoryResponse;
import com.kkpip2022.property.util.HorizontalListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {

    SimpleAdapter CateAdapter;
    SimpleAdapter CrewAdapter;

    HorizontalListView HorizonListView_lv;

    ListView VerticalListView_lv;

    private List<Map<String,Object>> CateItem = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        // 绑定 Vertical ListView 控件
        VerticalListView_lv = view.findViewById(R.id.home_verticalListView);

        // 绑定 Horizontal ListView 控件
        // 这个类是由网上的开发者贡献的，我只是拿来是用
        // 这个类目前有些问题，就是加载的 Layout 文件的 Width 是处于失效的状态
        // 不知道是因为我嵌套在 HorizonScrollView 中的问题还是本身的问题
        // 不过无伤大雅，可以使用
        HorizonListView_lv = view.findViewById(R.id.home_horizontalListView);

        // 设置 「目录、分类、总库存」 Horizontal ListView 动态加载数据
        // 需要先绑定控件、适配器，数据获取器以及Item点击后的响应方法

        // 设置适配器 Adapter，因为在 Fragment 中，需要使用 getActivity 方法
        // getData 部分是用来获取数据的
        // 然后设置目标 Item 布局，并将需要动态加载的控件加入一个 ArrayList中
        // 将 ArrayList 中的控件与 String[] 中的命名一一对应
        // 后面再往 List>>Map 中添加数据时，只需要对应名字绑定数据即可
        CateAdapter = new SimpleAdapter(getActivity(),HLVgetData(),
                R.layout.category_name,new String[]{"title","sonNum","totalItem"},
                new int[]{R.id.Category_ItemTitle_tv,R.id.Category_TotalCate_tv,R.id.Category_TotalSonItem_tv});

        // 设置适配器
        HorizonListView_lv.setAdapter(CateAdapter);

        // 设置点击操作，在旧版本的 Android 开发中，setOnItemClickListener
        // 后面的参数只需要设置 this 即可，这个是新版本的，需要使用 this::onItemClick
        HorizonListView_lv.setOnItemClickListener(this::HLVonItemClick);


        // 设置 Vertical ListView 的 Adapter
        CrewAdapter = new SimpleAdapter(getActivity(),VLVgetData(),
                R.layout.other_userinfo,new String[]{"crewName","crewEmail"},
                new int[]{R.id.userinfo_username_tv,R.id.userinfo_email_tv});

        // 设置适配器
        VerticalListView_lv.setAdapter(CrewAdapter);

        // 设置点击后的操作
        VerticalListView_lv.setOnItemClickListener(this::VLVonItemClick);

        // Inflate the layout for this fragment
        return view;
    }

    // 加载数据并以 List 形式向 Horizontal ListView 提供数据
    private List<Map<String, Object>> HLVgetData() {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("authority","admin");

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
        String ExtURL = "authority=" + "admin";

        Api.config(BaseURL, ServerPort, ApiConfig.HOME_CATEDETAIL, ExtURL, params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {

                Log.e("connection success:", res);

                Gson gson = new Gson();
                CategoryResponse categoryResponse = gson.fromJson(res, CategoryResponse.class);
                if (categoryResponse.getData() != null) {
                    // 如果 data 项有数据
                    List<String> CategoryResponse = categoryResponse.getData();
                    for (int i = 0; i < CategoryResponse.size(); i++) {
                        String TempData = (String) String.valueOf(CategoryResponse.get(i));
                        CategoryItemDetail categoryItemDetail = gson.fromJson(TempData,CategoryItemDetail.class);
                        Map map = new HashMap();
                        map.put("title",categoryItemDetail.getCategoryName());
                        map.put("sonNum",categoryItemDetail.getSonItem());
                        map.put("totalItem",categoryItemDetail.getSonTotality());
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

    // 加载数据以 List 形式向 Vertical ListView 提供数据
    private List<Map<String,Object>> VLVgetData() {

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


        String[] CrewName = {"张三","李四","李斯特"};
        String[] CrewEmail = {"ThisIsATestEmail@xxx.com1","ThisIsATestEmail@xxx.com2","ThisIsATestEmail@xxx.com3"};
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i=0;i < CrewName.length;i++) {
            Map map = new HashMap();
            map.put("crewName",CrewName[i]);
            map.put("crewEmail",CrewEmail[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // 用来处理 Horizontal ListView 中 Item 的点击事件
    public void HLVonItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position:",String.valueOf(position));
        String text = HorizonListView_lv.getAdapter().getItem(position).toString();
    }

    // 用来处理 Vertical ListView 中 Item 的点击事件
    public void VLVonItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position:",String.valueOf(position));
    }
}