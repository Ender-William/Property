package com.kkpip2022.property.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayout());
        initView();
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();


    /**
     * 强制使用竖屏模式;
     */
    protected void focusPortrait(){
        // 强制使用竖屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    /**
     * 强制使用横屏;
     */
    protected void focusLandscape(){
        // 强制使用横屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    /**
     * Toast 消息提示;
     * @param msg: String 需要展示的字符串;
     */
    public void showToast(String msg){
        // Toast 消息展示
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast 消息异步提示;
     * @param msg: String 需要展示的字符串;
     */
    public void showToastSync(String msg){
        // Toast 消息异步展示
        // 注意，在没有异步进程的时候，不要使用 showToastSync ，否则会闪退
        Looper.prepare();
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    /**
     * Intent 跳转方法，Bool 控制是否需要切换过度动画;
     * @param cls: Class 目标 Activity 的 Class;
     * @param bool: boolean 是否需要切换过度动画;
     */
    public void navigateTo(Class cls,boolean bool){
        // 跳转页面
        Intent in = new Intent(mContext,cls);
        if(bool){
            Log.e("Activity:","StartActivity");
            startActivity(in, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
        }else{
            startActivity(in);
        }
    }

    /**
     * 带有传参的 Intent 跳转方法，Bool 控制是否需要切换过度动画;
     * 传参的"键"名为 KEY ;
     * @param cls: Class 目标 Activity 的 Class;
     * @param bool: boolean 是否需要切换过度动画;
     * @param str: String 需要传递的参数;
     */
    public void navigateToWithVal(Class cls,boolean bool, String str) {
        Intent intent = new Intent(mContext,cls);
        intent.putExtra("KEY",str);
        if(bool){
            Log.e("Activity:","StartActivity");
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
        }else{
            startActivity(intent);
        }
    }

    public void navigateToWithIntVal(Class cls,boolean bool, int str) {
        Intent intent = new Intent(mContext,cls);
        intent.putExtra("intKEY",str);
        if(bool){
            Log.e("Activity:","StartActivity");
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
        }else{
            startActivity(intent);
        }
    }

    /**
     * 接收 Intent 的传参;
     * @return : String Intent 接收到的字符串;
     */
    public String navigateGetVal() {
        Intent intent = getIntent();
        String val_str = intent.getStringExtra("KEY");
        return val_str;
    }

    public int navigateGetIntVal() {
        Intent intent = getIntent();
        int val_str = intent.getIntExtra("intKEY",0);
        return val_str;
    }

    /**
     * 用于传递跳转到的 Fragment 位置
     * @param cls: Class 目标 Activity 的 Class;
     * @param position: int 目标 Fragment 的位置;
     */
    public void navigateToFragment(Class cls,int position) {
        Intent intent = new Intent(mContext,cls);
        intent.putExtra("Frag",position);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext).toBundle());
    }

    /**
     * 用于接收需要跳转的 Fragment Position 地址
     * @return int
     */
    public int getFragmentPosition() {
        Intent intent = getIntent();
        int val_int = intent.getIntExtra("Frag",1);
        return val_int;
    }

    /**
     * 用于刷新 Activity;
     */
    protected void reloadActivity() {
        // 刷新当前 Activity
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 倒计时延时终止 Activity 活动;
     * @param sed: int 使用毫秒单位 ms，1000ms 为 1s;
     */
    protected void delayEndActivity(int sed) {
        // 延时终止 Activity 活动
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        },sed);
    }

    /**
     * 储存 String 到 SharedPreference 当中;
     * @param spName: String SharedPreference 数据库的名称;
     * @param key: String 键值对中的"键"的名字;
     * @param val: String 键值对中的"值"的参数;
     */
    protected void SaveStringToSharedPreferences(String spName, String key, String val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,val);
        editor.commit();
    }

    /**
     * 储存 int 到 SharedPreference 当中;
     * @param spName: String SharedPreference 数据库的名称;
     * @param key: String 键值对中的"键"的名字;
     * @param val: int 键值对中的"值"的参数;
     */
    protected void SaveIntToSharedPreferences(String spName, String key, int val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,val);
        editor.commit();
    }

    /**
     * 储存 float 到 SharedPreference 当中;
     * @param spName: String SharedPreference 数据库的名称;
     * @param key: String 键值对中的"键"的名字;
     * @param val: float 键值对中的"值"的参数;
     */
    protected void SaveFloatToSharedPreferences(String spName, String key, float val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key,val);
        editor.commit();
    }

    /**
     * 储存 boolean 到 SharedPreference 当中;
     * @param spName: String SharedPreference 数据库的名称;
     * @param key: String 键值对中的"键"的名字;
     * @param val: boolean 键值对中的"值"的参数;
     */
    protected void SaveBooleanToSharedPreferences(String spName, String key, boolean val) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,val);
        editor.commit();
    }

    /**
     * 检查 SharedPreference 数据库中 键(KEY) 是否已存在;
     * @param spName: String SharedPreference 数据库的名称;
     * @param key: String 键值对中的"键"的名字;
     * @return : boolean 存在返回 true, 不存在返回 false;
     */
    protected boolean CheckisSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        boolean isContains = sharedPreferences.contains(key);
        return isContains;
    }

    /**
     * 从 SharedPreference 中获取 String;
     * @param spName: String SharedPreference 数据库的名称;
     * @param key: String 键值对中的"键"的名字;
     * @return : String 对应"键"的取值;
     */
    protected String GetStringSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        String val = sharedPreferences.getString(key,null);
        return val;
    }

    protected int GetIntSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        int val = sharedPreferences.getInt(key,0);
        return val;
    }

    protected boolean GetBooleanSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(spName,MODE_PRIVATE);
        boolean val = sharedPreferences.getBoolean(key, false);
        return val;
    }

}
