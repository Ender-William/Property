package com.kkpip2022.property.activity;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kkpip2022.property.R;
import com.kkpip2022.property.api.Api;
import com.kkpip2022.property.api.ApiConfig;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.api.TtitCallback;
import com.kkpip2022.property.entity.FindUserResponse;
import com.kkpip2022.property.util.StringUtils;

import java.util.HashMap;

public class Signup extends BaseActivity {
    private Button signup_btn;
    private Button server_config_btn;

    private EditText stuID_et;
    private EditText passwd_et;
    private EditText passwd_check_et;
    private EditText name_et;
    private EditText email_et;

    private String stuID_str;
    private String passwd_str;
    private String passwd_check_str;
    private String name_str;
    private String email_str;

    @Override
    protected int initLayout() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // 强制使用竖屏模式
        focusPortrait();

        // 绑定控件
        stuID_et = (EditText) findViewById(R.id.signup_username_et);
        passwd_et = (EditText) findViewById(R.id.signup_passwd_et);
        passwd_check_et = (EditText) findViewById(R.id.signup_rePasswd_et);
        name_et = (EditText) findViewById(R.id.signup_name_et);
        email_et = (EditText) findViewById(R.id.signup_email_et);

        server_config_btn = (Button) findViewById(R.id.signup_serverConf_btn);
        signup_btn = (Button) findViewById(R.id.signup_signup_btn);

        // 跳转到服务器地址设置页面
        server_config_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 不终止 Activity 是因为从 ServerConfig 页面返回时，
                // 程序会返回到最近一个没有终止掉的 Activity，
                // 以此来达到返回上一个页面的效果。
                navigateTo(ServerConf.class,true);
            }
        });

        // 点击进行注册操作
        /*
         * 注册前先需要在本地进行 密码 与 第二密码 一致性校验，
         * 校验通过进行 API 调用，注册，
         * 注册前先查询是否用户已注册，
         * 若注册，通过 Toast 提示，
         * 若没有注册，继续向数据库插入数据，
         * 注册完成通过 Intent 将 用户名 传值给 Login Page，
         * 若注册未完成，则通过 Toast 提示对应的错误给用户，
         * 校验未通过则需要通过 Toast 提示用户，
         * 同时需要将 密码 与 第二密码 的字体颜色变为红色，
         *
         */
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置 左右晃动 动画
                Animation shake = AnimationUtils.loadAnimation(Signup.this, R.anim.editext_shake);

                // 从 EditText 获取对应的字符串
                stuID_str = stuID_et.getText().toString().trim();
                passwd_str = passwd_et.getText().toString().trim();
                passwd_check_str = passwd_check_et.getText().toString().trim();
                name_str = name_et.getText().toString().trim();
                email_str = email_et.getText().toString().trim();

                // 检查两次密码一致性，获取两次密码状态
                Boolean password_state_bool;
                password_state_bool = StringUtils.CheckPasswdSame(passwd_str,passwd_check_str);

                if (password_state_bool) {
                    // 当两次密码一致时
                    signup(stuID_str,passwd_str,name_str,email_str);
                } else {
                    // 当两次密码不一致时
                    // 通过 Toast 提示用户两次密码不一致
                    showToast(getString(R.string.Warning_passwordNotSame));
                    // 设置字体颜色为红色
                    passwd_check_et.setTextColor(getResources().getColor(R.color.light_red));
                    // EditText 左右晃动
                    passwd_check_et.startAnimation(shake);
                }

            }
        });


    }

    private void signup(String stuID_str, String passwd_str, String name_str, String email_str) {
        // 如果上述四种参数有任意一项为空，跳出提示的同时跳出流程
        if (StringUtils.isEmpty(stuID_str)) {
            showToast(getString(R.string.Warning_usernameIsEmpty));
            return;
        }
        if (StringUtils.isEmpty(passwd_str)) {
            showToast(getString(R.string.Warning_passwordIsEmpty));
            return;
        }
        if (StringUtils.isEmpty(name_str)) {
            showToast(getString(R.string.Warning_nameIsEmpty));
            return;
        }
        if (StringUtils.isEmpty(email_str)) {
            showToast(getString(R.string.Warning_email));
            return;
        }

        // 参数都不为空
        // 设置网站地址与端口号
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
        // 先检查是否存在这个账号
        String finduser = "stuID=" + stuID_str;
        HashMap<String, Object> params_check = new HashMap<String, Object>();
        params_check.put("stuID",stuID_str);

        Api.config(BaseURL, ServerPort, ApiConfig.FIND_USR, finduser, params_check).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {

                // Logcat 输出查询信息
                Log.e("onSuccess/searchUsr",res);

                Gson gson = new Gson();

                // 从返回参数中获取 data 里的查询值。
                FindUserResponse findUserResponse = gson.fromJson(res, FindUserResponse.class);
                if (findUserResponse.getData() != null) {
                    // 当这个值不为空的时候，则说明用户已经注册
                    // 通过 Toast 对用户进行提示，用户已存在
                    Looper.prepare();
                    showToast(getString(R.string.Warning_userExist));
                    // 展示之后，将 stuID 的值通过 Intent 传值给 LoginPage
                    navigateToWithVal(Login.class,true,stuID_str);
                    delayEndActivity(1000);
                    Looper.loop();
                } else {
                    // 当这个值为 空 的时候，说明没有在数据库里查询到用户信息，
                    // 因为查询的值为 主键，因此可以判断到这个账号还没有注册

                    String insertData = "stuID=" + stuID_str + "&passwd=" + passwd_str + "&stuName=" + name_str + "&email=" + email_str;
                    HashMap<String ,Object> params_insert = new HashMap<String, Object>();
                    params_insert.put("stuID",stuID_str);
                    params_insert.put("passwd",passwd_str);
                    params_insert.put("stuName",name_str);
                    params_insert.put("email",email_str);

                    Api.config(BaseURL, ServerPort, ApiConfig.REGISTER, insertData, params_insert).postRequest(new TtitCallback() {
                        @Override
                        public void onSuccess(String res) {
                            // 得到正常的返回值
                            // 先判断是否注册成功
                            // 注册成功时，Json 中嵌套的参数会返回注册时的 stuID

                            // 在 Logcat 打印状态，便于追踪
                            // 说明 API 接口调用成功
                            Log.e("onSuccess/register",res);

                            Looper.prepare();
                            FindUserResponse registerCallback = gson.fromJson(res,FindUserResponse.class);
                            if (registerCallback.getData() != null
                                    && String.valueOf(registerCallback.getData().getStuID()).equals(stuID_str)) {
                                // 如果 registerCallback 不为空，且返回的 stuID 与输入的 stuID 一致说明注册成功
                                // 此 API 接口在注册成功时，会对注册 ID 进行查询
                                // 若进行到查询阶段，说明已经成功注册

                                //通过 Toast 将成功注册的消息提示给用户
                                showToast(getString(R.string.Warning_successRegister));

                                // 完成注册之后，将 stuID 传值给 Login Page
                                navigateToWithVal(Login.class,true,stuID_str);

                                // 终止 Register Activity 的活动
                                delayEndActivity(1000);
                            } else {
                                showToast(getString(R.string.Warning_apiError));
                            }
                            Looper.loop();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // 如果出现错误，说明是 API 接口出现了问题
                            showToastSync(getString(R.string.Warning_apiError));
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {
                // 网络错误，通过异步进行展示
                showToastSync(getString(R.string.Warning_pleaseCheckNet));
                return;
            }
        });

    }
}