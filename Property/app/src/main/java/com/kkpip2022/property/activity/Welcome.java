package com.kkpip2022.property.activity;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;

import com.kkpip2022.property.R;

public class Welcome extends BaseActivity {

    private Button login_btn;
    private Button signup_btn;
    private Button serverconfig_btn;

    @Override
    protected int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //强制使用竖屏模式
        focusPortrait();

        login_btn = (Button) findViewById(R.id.welcome_login_btn);
        signup_btn = (Button) findViewById(R.id.welcome_signup_btn);
        serverconfig_btn = (Button) findViewById(R.id.welcome_serverConf_btn);

        // 跳转到登录页面
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(Login.class,true);
                delayEndActivity(1000);
            }
        });

        // 跳转到注册页面
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(Signup.class,true);
                delayEndActivity(1000);
            }
        });

        // 跳转到服务器地址设置页面
        serverconfig_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(ServerConf.class,true);
            }
        });

    }
}