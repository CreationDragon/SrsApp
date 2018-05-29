package com.gtt.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtt.app.MainActivity;
import com.gtt.app.R;
import com.gtt.app.entity.User;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Volunteer;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import cn.finalteam.toolsfinal.StringUtils;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    private File tempFile;
    @BindView(R.id.back)
    ImageView iv_back;
    @ViewInject(R.id.tv_submit)
    TextView tv_submit;
    @ViewInject(R.id.iv_upload_image)
    ImageView iv_upload_image;
    @ViewInject(R.id.iv_preview)
    ImageView iv_preview;
    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_user_age)
    EditText et_user_age;
    @BindView(R.id.et_user_bodyHeight)
    EditText et_user_bodyHeight;
    @BindView(R.id.et_user_address)
    EditText et_user_address;
    @BindView(R.id.et_user_contact)
    EditText et_user_contact;
    @BindView(R.id.et_user_dress)
    EditText et_user_dress;
    @BindView(R.id.et_user_email)
    EditText et_user_email;
    @BindView(R.id.et_user_feature)
    EditText et_user_featuree;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rg_IsDna)
    RadioGroup rg_IsDna;
    @BindView(R.id.rb_man)
    RadioButton rb_man;
    @BindView(R.id.rb_woman)
    RadioButton rb_woman;
    @BindView(R.id.rb_yes)
    RadioButton rb_yes;
    @BindView(R.id.rb_no)
    RadioButton rb_no;
    RadioButton gender;
    RadioButton IsDna;
    //    图片文件路径
    String imagePath = null;
    //    Bitmap图片
    Bitmap bmp = null;
    //    图片文件
    File file = null;
    private Integer userId = null;
    EditText et_user_password;
    EditText et_user_surePsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);


        //        发现组件
        iv_back = findViewById(R.id.back);
        tv_submit = findViewById(R.id.tv_submit);
        et_user_address = findViewById(R.id.et_user_address);
        et_user_name = findViewById(R.id.et_user_name);
        et_user_contact = findViewById(R.id.et_user_contact);
        et_user_dress = findViewById(R.id.et_user_dress);
        rb_man = findViewById(R.id.rb_man);
        rb_woman = findViewById(R.id.rb_woman);
        rg_gender = findViewById(R.id.rg_gender);
        et_user_email = findViewById(R.id.et_user_email);
        et_user_password = findViewById(R.id.et_user_password);
        et_user_surePsw = findViewById(R.id.et_user_surePsw);


//        点击事件
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectGenderRadioBtn();
            }
        });

    }

    private void selectGenderRadioBtn() {
        gender = findViewById(rg_gender.getCheckedRadioButtonId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tv_submit:
                String psw = et_user_password.getText().toString();
                String surePsw = et_user_surePsw.getText().toString();
                if (psw.equals(surePsw) && !StringUtils.isEmpty(psw) && !StringUtils.isEmpty(surePsw)) {
                    User user = new User();
                    user.setUserName(et_user_name.getText().toString());
                    user.setUserPsw(et_user_password.getText().toString());
                    user.setUserGener(gender.getText().toString());
                    user.setUserAddressDetail(et_user_address.getText().toString());
                    user.setUserPhone(et_user_contact.getText().toString());
                    user.setUserEmail(et_user_email.getText().toString());

                    Gson gson = new Gson();
                    String str = gson.toJson(user);
                    volunteerRecruit(str);
                } else {
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    //    发布信息
    private void volunteerRecruit(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(GeneralSetting.registerUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("register_data", str);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("successResult:       ", result);
                        Gson gson = new Gson();
                        JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
                        if (jsonResult.result.equals("success")) {
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "系统维护，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
