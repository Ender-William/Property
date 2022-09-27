package com.kkpip2022.property.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kkpip2022.property.R;
import com.kkpip2022.property.adapter.MyPagerAdapter;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.entity.TabEntity;
import com.kkpip2022.property.fragment.HomeFragment;
import com.kkpip2022.property.fragment.LendFragment;
import com.kkpip2022.property.fragment.MeFragment;
import com.kkpip2022.property.fragment.ReturnFragment;

import java.util.ArrayList;

public class Home extends BaseActivity {

    private String[] mTitles = {"主页", "出借", "归还", "个人"};

    // 底部 Navigate Bar Icon 未被选中的图标
    private int[] mIconUnselectIds = {
            R.mipmap.home_black, R.mipmap.lend_black,
            R.mipmap.return_black, R.mipmap.me_black};

    // 底部 Navigate Bar Icon 被选中的图标
    private int[] mIconSelectIds = {
            R.mipmap.home_blue, R.mipmap.lend_blue,
            R.mipmap.return_blue, R.mipmap.me_blue};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.HomeViewpager);
        commonTabLayout = findViewById(R.id.HomeCommonTabLayout);
    }

    @Override
    protected void initData() {

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

        // 添加与 mTitles 对应顺序的 Fragment 页面
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(LendFragment.newInstance());
        mFragments.add(ReturnFragment.newInstance());
        mFragments.add(MeFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments));
    }
}