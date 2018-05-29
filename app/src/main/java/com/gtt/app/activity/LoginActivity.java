package com.gtt.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.finalteam.toolsfinal.StringUtils;

/**
 * Created by Creat on 2018/5/12.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    //    定义组件
    EditText et_user_name;
    EditText et_user_pwd;
    ImageView iv_back;
    Button btn_login;
    String userName = null;
    String userPwd = null;
    TextView tv_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//      初始化Xutil
//        x.Ext.init(getApplication());

//        发现组件
        et_user_name = findViewById(R.id.et_user_name);
        et_user_pwd = findViewById(R.id.et_user_pwd);
        btn_login = findViewById(R.id.btn_login);
        iv_back = findViewById(R.id.iv_back);
        tv_register = findViewById(R.id.tv_register);
        tv_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线


//   焦点变化
        et_user_pwd.setOnEditorActionListener(this);

//        设置点击事件
        btn_login.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_register.setOnClickListener(this);

    }

//    点击事件的实现

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //查找账户
                String result = findUser();
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_register:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), RegistActivity.class);
                startActivity(intent);
                break;
        }
//

    }

    private String findUser() {
        Log.i("确认调用了函数: ", "yes");
        String result = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(GeneralSetting.loginUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("userName", userName);
                params.addQueryStringParameter("userPsw", userPwd);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("登录返回的结果: ", result);
                        Gson gson = new Gson();
                        JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
                        User u = gson.fromJson(String.valueOf(jsonResult.getData()), User.class);


                        if ("success".equals(jsonResult.getResult())) {
                            SharedPreferences.Editor editor = getSharedPreferences("userInfo", 0).edit();
                            editor.putInt("user_id", u.getUserId());
                            editor.putString("user_name", u.getUserName());
                            editor.putString("user_pwd", u.getUserPsw());
                            editor.putString("user_gender", u.getUserGener());
                            editor.apply();
                            editor.commit();

                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "账号密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
        return result;
    }

    //输入法的确认按钮
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            userName = et_user_name.getText().toString();
            userPwd = et_user_pwd.getText().toString();
//            获得焦点
            if (StringUtils.isEmpty(userName) | StringUtils.isEmpty(userPwd)) {
                btn_login.setClickable(false);
            } else {
                btn_login.setClickable(true);
                btn_login.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            return true;
        }
        return false;
    }
}
