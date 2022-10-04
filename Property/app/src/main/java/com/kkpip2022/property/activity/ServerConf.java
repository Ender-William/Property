package com.kkpip2022.property.activity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kkpip2022.property.R;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.util.StringUtils;

public class ServerConf extends BaseActivity {

    private EditText serveraddr_et;
    private EditText portnumber_et;

    private Button save_conf_btn;
    private Button back_btn;

    private String serveradd_str;
    private String serverport_str;
    private String temp_serveradd_str;
    private String temp_serverport_str;

    @Override
    protected int initLayout() {
        return R.layout.activity_server_conf;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        // 强制使用竖屏
        focusPortrait();

        serveraddr_et = (EditText) findViewById(R.id.serverconf_serveraddr_et);
        portnumber_et = (EditText) findViewById(R.id.serverconf_port_et);

        save_conf_btn = (Button) findViewById(R.id.ServerConf_saveConf_btn);
        back_btn = (Button) findViewById(R.id.ServerConf_back_btn);

        // 先检查有没有有预先的设置
        if (CheckisSharedPreferencesContains(
                SharedPreferenceDefault.SharedPreferenceSysConfName
                ,SharedPreferenceDefault.SharedPreferenceSysConfServerAdd) &&
                CheckisSharedPreferencesContains(
                        SharedPreferenceDefault.SharedPreferenceSysConfName,
                        SharedPreferenceDefault.SharedPreferenceSysConfServerPort)) {
            //如果存在预先设置，从SharedPreference中加载预设
            serveradd_str = GetStringSharedPreferencesContains(SharedPreferenceDefault.SharedPreferenceSysConfName,
                    SharedPreferenceDefault.SharedPreferenceSysConfServerAdd);
            serverport_str = GetStringSharedPreferencesContains(SharedPreferenceDefault.SharedPreferenceSysConfName,
                    SharedPreferenceDefault.SharedPreferenceSysConfServerPort);
            showToast(getString(R.string.Warning_serverConfLoadFinish));
            // 将加载出来的配置通过 EditText 显示出来
            serveraddr_et.setText(serveradd_str);
            portnumber_et.setText(serverport_str);
        } else {
            // 没有存在预先设置，通过 Toast 提示
            showToast(getString(R.string.Warning_pleaseSetUpServerAddr));
        }

        // 返回至上一页面
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerConf.this.finish();
            }
        });

        // 保存设置
        save_conf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp_serveradd_str = serveraddr_et.getText().toString();
                temp_serverport_str = portnumber_et.getText().toString();
                if (StringUtils.isEmpty(temp_serveradd_str) ||
                    StringUtils.isEmpty(temp_serverport_str)) {
                    // 当地址或端口号为空的时候
                    showToast(getString(R.string.Warning_pleaseCheckisEmpty));
                } else {
                    // 当地址或端口号不为空的时候
                    SaveStringToSharedPreferences(
                            SharedPreferenceDefault.SharedPreferenceSysConfName,
                            SharedPreferenceDefault.SharedPreferenceSysConfServerAdd,
                            temp_serveradd_str);
                    SaveStringToSharedPreferences(
                            SharedPreferenceDefault.SharedPreferenceSysConfName,
                            SharedPreferenceDefault.SharedPreferenceSysConfServerPort,
                            temp_serverport_str);
                    // 提示保存完成的信息
                    showToast(getString(R.string.Warning_saveFinish));
                }
            }
        });
    }
}