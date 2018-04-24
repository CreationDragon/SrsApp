package com.gtt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gtt.app.R;
import com.gtt.app.Util.GlideImageLoader;
import com.gtt.app.Util.NetAccessUtil;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;
import com.youth.banner.Banner;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Creat on 2018/4/22.
 */

public class MissPersonDetailActivity extends AppCompatActivity {

    Banner banner;
    @ViewInject(R.id.ll_missInfo)
    ListView ll_missInfo;
    ImageView back;
    List<String> pics = new ArrayList<>();
    List<String> images = new ArrayList<>();
    int missPersonId;
    JsonResult jsonResult = new JsonResult();
    Missingpersons missingpersons = new Missingpersons();

    TextView tv_missperson_name;
    TextView tv_missperson_age;
    TextView tv_missperson_bodyheight;
    TextView tv_missperson_feature;
    TextView tv_missperson_address;
    TextView tv_missperson_dress;
    TextView tv_missperson_contact;
    TextView tv_missperson_unit;
    TextView tv_missperson_release_date;


    public static final int UPDATE_TEXT = 1;
    //    定义handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    tv_missperson_name.setText(missingpersons.getPersonsName());
                    tv_missperson_age.setText(String.valueOf(missingpersons.getPersonsAge()) + "岁");
                    tv_missperson_bodyheight.setText(String.valueOf(missingpersons.getPersonsBodyheight()) + "厘米");
                    tv_missperson_feature.setText(missingpersons.getPersonsFeature());
                    tv_missperson_address.setText(missingpersons.getPersonsAddress());
                    tv_missperson_contact.setText(missingpersons.getPersonsContact());
                    tv_missperson_unit.setText(missingpersons.getPersonsRescueunit());
                    tv_missperson_release_date.setText(missingpersons.getPersonsReleasedate());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missperson_detail);

        //初始化Xutil
        x.Ext.init(getApplication());
        Intent intent = this.getIntent();
        final Integer missPersonId = intent.getIntExtra("missPersonId", 1);

        banner = findViewById(R.id.banner);
        ll_missInfo = findViewById(R.id.ll_missInfo);
        back = findViewById(R.id.back);

        tv_missperson_name = findViewById(R.id.tv_missperson_name);
        tv_missperson_age = findViewById(R.id.tv_missperson_age);
        tv_missperson_bodyheight = findViewById(R.id.tv_missperson_bodyhight);
        tv_missperson_feature = findViewById(R.id.tv_missperson_feature);
        tv_missperson_address = findViewById(R.id.tv_missperson_address);
        tv_missperson_contact = findViewById(R.id.tv_missperson_contact);
        tv_missperson_unit = findViewById(R.id.tv_missperson_unit);
        tv_missperson_release_date = findViewById(R.id.tv_missperson_release_date);

//        返回键
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MissPersonDetailActivity.this.finish();
            }
        });

//        根据ID获取头像

        RequestParams params = new RequestParams(GeneralSetting.getPersonsPicUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("MissPersonId", String.valueOf(missPersonId));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//使用GSON将字符串转为对象
                Gson gson = new Gson();
                JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
                pics = (List<String>) jsonResult.getData();
                for (String name : pics
                        ) {
                    images.add(GeneralSetting.baseUrl + "/missPersonsPics/" + missPersonId + "/" + name);
                }
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
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


        new Thread(new Runnable() {
            @Override
            public void run() {
                //        根据ID获取详细失踪信息

                RequestParams secondparams = new RequestParams(GeneralSetting.missPeronsInfoDeatailUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                secondparams.addQueryStringParameter("MissPersonId", String.valueOf(missPersonId));
                x.http().post(secondparams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//使用GSON将字符串转为对象
                        Gson gson = new Gson();
                        JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
//                        Type lt=new TypeToken<Missingpersons>(){}.getType();
                        String myJson = gson.toJson(jsonResult.getData());
                        missingpersons = gson.fromJson(myJson, Missingpersons.class);
                        System.out.println(jsonResult.getData());

                        Message message = new Message();
                        message.what = UPDATE_TEXT;
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
