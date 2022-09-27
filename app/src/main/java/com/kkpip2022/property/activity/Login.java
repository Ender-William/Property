package com.kkpip2022.property.activity;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kkpip2022.property.R;
import com.kkpip2022.property.api.Api;
import com.kkpip2022.property.api.ApiConfig;
import com.kkpip2022.property.api.SharedPreferenceDefault;
import com.kkpip2022.property.api.TtitCallback;
import com.kkpip2022.property.entity.LoginResponse;
import com.kkpip2022.property.util.StringUtils;

import java.util.HashMap;
import java.util.Objects;

public class Login extends BaseActivity {
    private Button login_btn;
    private Button server_config_btn;

    private EditText username_et;
    private EditText password_et;

    private String stuID;
    private String passwd;
    private String intent_stuID;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // 强制使用竖屏模式
        focusPortrait();

        // 绑定控件
        username_et = (EditText) findViewById(R.id.login_username_et);
        password_et = (EditText) findViewById(R.id.login_passwd_et);

        login_btn = (Button) findViewById(R.id.login_login_btn);
        server_config_btn = (Button) findViewById(R.id.login_serverConf_btn);

        // 检测从 Register 页面的传值，注册成功时会向 Login 页面跳转并传递 stuID 的值
        intent_stuID = navigateGetVal();
        if (intent_stuID != null) {
            // 如果不为空，说明有值，通过 setText 方法在 username_et 上显示
            username_et.setText(intent_stuID);
        }

        // 跳转到服务器地址设置页面
        server_config_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(ServerConf.class,true);
            }
        });

        // 点击登录按钮，尝试登录到主页面
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stuID = username_et.getText().toString().trim();
                passwd = password_et.getText().toString().trim();
                login(stuID,passwd);
            }
        });
    }

    private void login(String stuID, String password) {
        if (StringUtils.isEmpty(stuID)){
            showToastSync(getString(R.string.Warning_usernameIsEmpty));
            return;
        }
        if (StringUtils.isEmpty(password)) {
            showToastSync(getString(R.string.Warning_passwordIsEmpty));
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("stuID",stuID);
        params.put("passwd",password);

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
        String ExtURL = "stuID=" + stuID + "&passwd=" + password;

        // showToast(BaseURL+":"+ServerPort+ApiConfig.LOGIN+ExtURL);

        // 开始调用 Api
        Api.config(BaseURL, ServerPort, ApiConfig.LOGIN, ExtURL, params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(final String res) {

                Log.e("onSuccess",res);
                Gson gson = new Gson();

                // 因为返回的 res json 中嵌套了一个实体类，因此需要将要用的 data 的实体类提取出来
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);

                if (loginResponse.getData() != null) {

                    // 将 fullBackResponse 中的 stuID passwd 储存为字符串形式
                    // state 账号状态以 int 形式储存，0代表账号未注销，1代表账号已注销
                    // 已注销的账号不能登录，需要返回一个提示
                    String LoginResponse_stuID = String.valueOf(loginResponse.getData().getStuID());
                    String LoginResponse_passwd = loginResponse.getData().getPasswd();
                    int LoginResponse_state = loginResponse.getData().getState();
                    // showToastSync(res);
                    if (Objects.equals(LoginResponse_stuID,stuID)
                            && Objects.equals(LoginResponse_passwd,password)
                            && LoginResponse_state == 0){
                        // 如果返回的 账号、密码与输入的相同以及状态码为0 时，可以进行登录操作
                        // 这里使用 Looper ，开启异步
                        Looper.prepare();
                        // 将 stuID 以字符串形式保存到 SharedPreference 的 sysLoginStuID 当中
                        SaveStringToSharedPreferences(
                                SharedPreferenceDefault.SharedPreferenceSysConfName,
                                SharedPreferenceDefault.SharedPreferenceSysConfigLoginStuID,
                                LoginResponse_stuID
                        );
                        // 将登录状态保存到 SharedPreference 的 sysLoginState 当中
                        SaveIntToSharedPreferences(
                                SharedPreferenceDefault.SharedPreferenceSysConfName,
                                SharedPreferenceDefault.SharedPreferenceSysConfLoginState,
                                LoginResponse_state
                        );
                        // showToastSync(getString(R.string.Warning_loginSuccess));
                        // 跳转到主页面
                        navigateTo(Home.class,true);
                        delayEndActivity(1000);
                        Looper.loop();
                        } else if (LoginResponse_state == 1 ) {
                        // 如果用户的状态码 state = 1 说明用户已经注销
                        showToastSync(getString(R.string.Warning_userLogout));
                    } else {
                        // 如果不属于上
                        // 述任意一种情况则为 网络配置或者网络环境 有错误
                        showToastSync(getString(R.string.Warning_loginFail));
                    }
                } else {
                    // 如果 onSuccess 的返回值为空，则说明服务器端的 API 接口有错误
                    // 需要通过异步的 Toast 提示该错误
                    showToastSync(getString(R.string.Warning_apiError));
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