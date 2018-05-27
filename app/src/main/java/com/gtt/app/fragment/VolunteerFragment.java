package com.gtt.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtt.app.MainActivity;
import com.gtt.app.R;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;
import com.gtt.app.model.Volunteer;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import cn.finalteam.toolsfinal.StringUtils;

/**
 * Created by Creat on 2018/5/27.
 */

public class VolunteerFragment extends Fragment implements View.OnClickListener {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        //        发现组件
        iv_back = view.findViewById(R.id.back);
        tv_submit = view.findViewById(R.id.tv_submit);
        et_user_address = view.findViewById(R.id.et_user_address);
        et_user_name = view.findViewById(R.id.et_user_name);
        et_user_age = view.findViewById(R.id.et_user_age);
        et_user_contact = view.findViewById(R.id.et_user_contact);
        et_user_dress = view.findViewById(R.id.et_user_dress);
        rb_man = view.findViewById(R.id.rb_man);
        rb_woman = view.findViewById(R.id.rb_woman);
        rg_gender = view.findViewById(R.id.rg_gender);
        et_user_email = view.findViewById(R.id.et_user_email);


//        点击事件
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectGenderRadioBtn();
            }
        });

        return view;
    }

    private void selectGenderRadioBtn() {
        gender = getActivity().findViewById(rg_gender.getCheckedRadioButtonId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_submit:
                Volunteer volunteer = new Volunteer();
                volunteer.setVolunteerName(et_user_name.getText().toString());
                volunteer.setVolunteerAge(Integer.parseInt(et_user_age.getText().toString()));
                volunteer.setVolunteerGender(gender.getText().toString());
                volunteer.setVolunteerAddress(et_user_address.getText().toString());
                volunteer.setVolunteerPhone(et_user_contact.getText().toString());
                volunteer.setVolunteerEmail(et_user_email.getText().toString());

                Gson gson = new Gson();
                String str = gson.toJson(volunteer);
                volunteerRecruit(str);


                break;

        }
    }

    //    发布信息
    private void volunteerRecruit(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(GeneralSetting.volunteerRegisterUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("volunteerInfo", str);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("successResult:       ", result);
                        Gson gson = new Gson();
                        JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
                        if (jsonResult.result.equals("success")) {
                            Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "系统维护，请稍后再试", Toast.LENGTH_SHORT).show();
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

}
