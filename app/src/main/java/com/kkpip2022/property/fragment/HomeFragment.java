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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
        HorizonListView_lv = view.findViewById(R.id.home_horizontalListView);
        CateAdapter = new SimpleAdapter(getActivity(),getData(),
                R.layout.category_name,new String[]{"title","sonNum","totalItem"},
                new int[]{R.id.Category_ItemTitle_tv,R.id.Category_TotalCate_tv,R.id.Category_TotalSonItem_tv});
        HorizonListView_lv.setAdapter(CateAdapter);
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