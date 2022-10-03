package com.kkpip2022.property.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.kkpip2022.property.R;
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

    HorizontalListView HorizonListView_lv;

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

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
        CateAdapter = new SimpleAdapter(getActivity(),getData(),
                R.layout.category_name,new String[]{"title","sonNum","totalItem"},
                new int[]{R.id.Category_ItemTitle_tv,R.id.Category_TotalCate_tv,R.id.Category_TotalSonItem_tv});

        // 设置适配器
        HorizonListView_lv.setAdapter(CateAdapter);

        // 设置点击操作，在旧版本的 Android 开发中，setOnItemClickListener
        // 后面的参数只需要设置 this 即可，这个是新版本的，需要使用 this::onItemClick
        HorizonListView_lv.setOnItemClickListener(this::onItemClick);
        // Inflate the layout for this fragment
        return view;
    }

    private List<Map<String, Object>> getData() {
        String[] CateTitles = {"这是一个总类","这是一个总类2"};
        String[] CateItem = {"10000","125"};
        String[] CateTotal = {"123333345","166658"};
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i=0;i < CateTitles.length;i++) {
            Map map = new HashMap();
            map.put("title",CateTitles[i]);
            map.put("sonNum",CateItem[i]);
            map.put("totalItem",CateTotal[i]);
            list.add(map);
        }
        return list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position:",String.valueOf(position));
        String text = HorizonListView_lv.getAdapter().getItem(position).toString();
    }
}