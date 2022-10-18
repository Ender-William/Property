package com.kkpip2022.property.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }

    /**
     * Toast 消息异步提示;
     * @param msg: String 需要展示的字符串;
     */
    public void showToastSync(String msg){
        // Toast 消息异步展示
        // 注意，在没有异步进程的时候，不要使用 showToastSync ，否则会闪退
        Looper.prepare();
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    /**
     * Intent 跳转方法，Bool 控制是否需要切换过度动画;
     * @param cls: Class 目标 Activity 的 Class;
     */
    public void navigateTo(Class cls){
        // 跳转页面
        Intent in = new Intent(getActivity(),cls);
        startActivity(in);
    }

    /**
     * 带有传参的 Intent 跳转方法，Bool 控制是否需要切换过度动画;
     * 传参的"键"名为 KEY ;
     * @param cls: Class 目标 Activity 的 Class;
     * @param str: String 需要传递的参数;
     */
    public void navigateToWithVal(Class cls, String str) {
        Intent intent = new Intent(getActivity(),cls);
        intent.putExtra("KEY",str);
        startActivity(intent);
    }

    public void navigateToWithIntVal(Class cls,boolean bool, int str) {
        Intent intent = new Intent(getActivity(),cls);
        intent.putExtra("intKEY",str);
        if(bool){
            Log.e("Activity:","StartActivity");
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) getActivity()).toBundle());
        }else{
            startActivity(intent);
        }
    }

    /**
     * 储存 String 到 SharedPreference 当中;
     * @param spName: String SharedPreference 数据库的名称;
     * @param key: String 键值对中的"键"的名字;
     * @param val: String 键值对中的"值"的参数;
     */
    protected void SaveStringToSharedPreferences(String spName, String key, String val) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
        String val = sharedPreferences.getString(key,null);
        return val;
    }

    protected int GetIntSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
        int val = sharedPreferences.getInt(key,0);
        return val;
    }

    protected boolean GetBooleanSharedPreferencesContains(String spName, String key) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(spName,Context.MODE_PRIVATE);
        boolean val = sharedPreferences.getBoolean(key, false);
        return val;
    }

}
