package com.gtt.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.fragment.UserInfoFragment;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.News;
import com.gtt.app.entity.User;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Creat on 2018/5/13.
 */

public class UserInfoActivity extends AppCompatActivity {
    Intent intent;
    LinearLayout show_info;
    Button btn_login_register;
    User user = new User();
    ImageView iv_head;
    TextView tv_user_name;
    ImageView iv_user_man;
    ImageView iv_user_woman;
    Integer userId = null;
    TextView tv_user_age;
    TextView tv_user_address;
    TextView tv_user_detail_address;
    TextView tv_user_contact;
    TextView tv_user_email;
    Button btn_login_out;
    View mainView;

    public static final int MSG_STATE = 1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_STATE:
                    tv_user_name.setText(user.getUserName());
                    if (user.getUserGener().equals("男")) {
                        iv_user_man.setVisibility(View.VISIBLE);
                        iv_user_woman.setVisibility(View.GONE);
                    } else {
                        iv_user_woman.setVisibility(View.VISIBLE);
                        iv_user_man.setVisibility(View.GONE);
                    }
                    tv_user_detail_address.setText(user.getUserAddressDetail());
                    tv_user_contact.setText(user.getUserPhone());
                    tv_user_email.setText(user.getUserEmail());
                    Picasso.get().load(Uri.parse(GeneralSetting.baseUrl + "headpic/" + user.getUserId() + ".jpg").toString()).into(iv_head);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

//        初始化Xutil
        x.Ext.init(getApplication());

//        获取参数
        intent = this.getIntent();
        userId = intent.getIntExtra("userId", 0);


        //        发现组件
        show_info = findViewById(R.id.show_info);
        btn_login_register = findViewById(R.id.btn_login_regist);
        tv_user_name = findViewById(R.id.tv_user_name);
        iv_head = findViewById(R.id.iv_head);
        iv_user_man = findViewById(R.id.iv_user_man);
        iv_user_woman = findViewById(R.id.iv_user_woman);
        tv_user_address = findViewById(R.id.tv_user_address);
        tv_user_detail_address = findViewById(R.id.tv_user_detail_address);
        tv_user_contact = findViewById(R.id.tv_user_contact);
        tv_user_email = findViewById(R.id.tv_user_email);
        btn_login_out = findViewById(R.id.btn_login_out);


//        退出登录点击事件
        btn_login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });


//        网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(GeneralSetting.getUserByIdUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("userId", String.valueOf(userId));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("登录返回的结果: ", result);
                        Gson gson = new Gson();
                        JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
                        user = gson.fromJson(String.valueOf(jsonResult.getData()), User.class);

                        Message message = new Message();
                        message.what = MSG_STATE;
                        handler.sendMessage(message);

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

    }

}
