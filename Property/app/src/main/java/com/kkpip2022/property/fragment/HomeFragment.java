package com.kkpip2022.property.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.kkpip2022.property.R;
import com.kkpip2022.property.activity.CategoryDetail;
import com.kkpip2022.property.api.Api;
import com.kkpip2022.property.api.ApiConfig;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.api.TtitCallback;
import com.kkpip2022.property.entity.CategoryItemDetail;
import com.kkpip2022.property.entity.CategoryResponse;
import com.kkpip2022.property.entity.UserEmail;
import com.kkpip2022.property.entity.UserEmailItem;
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

    static final int COMPLETED = 0;

    SimpleAdapter CateAdapter;
    SimpleAdapter CrewAdapter;

    HorizontalListView HorizonListView_lv;

    ListView VerticalListView_lv;

    // 创建作用域在 HomeFragment 中的用于向 Horizon ListView 加载 List 数据的变量
    private List<Map<String,Object>> CateItem = new ArrayList<>();

    // 创建作用域在 HomeFragment 中用于向 Vertical ListView 中用户及电子邮箱 List 数据的变量
    private List<Map<String,Object>> UserItem = new ArrayList<>();

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
        new HorizonThread().start();

        new VerticalThread().start();

        // Inflate the layout for this fragment
        return view;
    }

    @SuppressLint("HandlerLeak")
    Handler Horizonhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here", "I am Here");
                CateAdapter = new SimpleAdapter(getActivity(),CateItem,
                        R.layout.category_name,new String[]{"title","sonNum","totalItem"},
                        new int[]{R.id.Category_ItemTitle_tv,R.id.Category_TotalCate_tv,R.id.Category_TotalSonItem_tv});

                // 设置适配器
                HorizonListView_lv.setAdapter(CateAdapter);

                // 设置点击操作，在旧版本的 Android 开发中，setOnItemClickListener
                // 后面的参数只需要设置 this 即可，这个是新版本的，需要使用 this::onItemClick
                HorizonListView_lv.setOnItemClickListener(HomeFragment.this::HLVonItemClick);
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler Vertialhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Log.e("Here", "I am Here");
                // 设置 Vertical ListView 的 Adapter
                CrewAdapter = new SimpleAdapter(getActivity(),UserItem,
                        R.layout.other_userinfo,new String[]{"crewName","crewEmail"},
                        new int[]{R.id.userinfo_username_tv,R.id.userinfo_email_tv});

                // 设置适配器
                VerticalListView_lv.setAdapter(CrewAdapter);

                // 设置点击后的操作
                VerticalListView_lv.setOnItemClickListener(HomeFragment.this::VLVonItemClick);
            }
        }
    };

    private class HorizonThread extends Thread {
        public void run() {
            // 首先需要传参，这里传递用户账号的权限说明，注：在Version 1 版本中无实质意义
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

            // 调用 API 访问后端接口 HOME_CATEDETAIL
            Api.config(BaseURL, ServerPort, ApiConfig.HOME_CATEDETAIL, ExtURL, params).postRequest(new TtitCallback() {
                @Override
                public void onSuccess(String res) {
                    // 如果网络没有错误
                    Log.e("connection success:", res);
                    // 创建 Gson 方法
                    Gson gson = new Gson();
                    // 获取返回值中 data 数据，data 数据格式为一个 List，List 中每一项为一个简单的 Json 数据
                    CategoryResponse categoryResponse = gson.fromJson(res, CategoryResponse.class);
                    if (categoryResponse.getData() != null) {
                        // 如果 data 项有数据
                        // 将 data 数据赋值给 CategoryResponse
                        List<String> CategoryResponse = categoryResponse.getData();
                        // 通过遍历的方法逐一读取 List 中每一项，将每一项 Json 格式数据通过实体类中 setter 与 getter
                        // 方法，读取出目标数据，并将目标数据添加到一个空的 Map 中
                        // 每一次添加完成都将 Map 添加到 CateItem 变量中
                        for (int i = 0; i < CategoryResponse.size(); i++) {
                            String TempData = (String) String.valueOf(CategoryResponse.get(i));
                            CategoryItemDetail categoryItemDetail = gson.fromJson(TempData,CategoryItemDetail.class);
                            Map map = new HashMap();
                            map.put("title",categoryItemDetail.getCategoryName());
                            map.put("sonNum",categoryItemDetail.getSonItem());
                            map.put("totalItem",categoryItemDetail.getSonTotality());
                            CateItem.add(map);
                        }
                        //处理完成后给handler发送消息
                        Message msg = new Message();
                        msg.what = COMPLETED;
                        Horizonhandler.sendMessage(msg);
                    }
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }

    private class VerticalThread extends Thread {
        public void run() {

            // 首先需要传参，这里传递用户账号的权限说明，注：在Version 1 版本中无实质意义
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

            Api.config(BaseURL,ServerPort,ApiConfig.GET_USER_EMAIL,ExtURL,params).postRequest(new TtitCallback() {
                @Override
                public void onSuccess(String res) {
                    // 如果网络没有错误
                    Log.e("connection success:", res);
                    // 创建 Gson 方法
                    Gson gson = new Gson();
                    // 获取返回值中 data 数据，data 数据格式为一个 List，List 中每一项为一个简单的 Json 数据
                    UserEmail userEmail = gson.fromJson(res, UserEmail.class);
                    if (userEmail.getData() != null) {
                        // 如果 data 项有数据
                        // 将 data 数据赋值给 CategoryResponse
                        List<String> CategoryResponse = userEmail.getData();
                        // 通过遍历的方法逐一读取 List 中每一项，将每一项 Json 格式数据通过实体类中 setter 与 getter
                        // 方法，读取出目标数据，并将目标数据添加到一个空的 Map 中
                        // 每一次添加完成都将 Map 添加到 CateItem 变量中
                        for (int i = 0; i < CategoryResponse.size(); i++) {
                            String TempData = (String) String.valueOf(CategoryResponse.get(i));
                            UserEmailItem userEmailItem = gson.fromJson(TempData,UserEmailItem.class);
                            Map map = new HashMap();
                            map.put("crewName",userEmailItem.getStuname());
                            map.put("crewEmail",userEmailItem.getEmail());
                            UserItem.add(map);
                        }
                        //处理完成后给handler发送消息
                        Message msg = new Message();
                        msg.what = COMPLETED;
                        Vertialhandler.sendMessage(msg);
                    }
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }

    // 加载数据以 List 形式向 Vertical ListView 提供数据
    private List<Map<String,Object>> VLVgetData() {

        return UserItem;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // 用来处理 Horizontal ListView 中 Item 的点击事件
    public void HLVonItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position:",String.valueOf(position));
        String text = HorizonListView_lv.getAdapter().getItem(position).toString();
        navigateToWithIntVal(CategoryDetail.class,true,position);
    }

    // 用来处理 Vertical ListView 中 Item 的点击事件
    public void VLVonItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position:",String.valueOf(position));
    }
}