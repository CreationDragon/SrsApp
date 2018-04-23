package com.gtt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.Util.GlideImageLoader;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.youth.banner.Banner;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creat on 2018/4/20.
 */

public class DetailpersonActivity extends AppCompatActivity {
    Banner banner;
    ListView ll_missInfo;
    ImageView back;
    List<String> pics = new ArrayList<>();
    List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailperson);
//初始化Xutil
        x.Ext.init(getApplication());

        Intent intent = this.getIntent();
        final Integer personId = intent.getIntExtra("personId", 1);

        Log.i("personId:    ", String.valueOf(personId));

        banner = findViewById(R.id.banner);
        ll_missInfo = findViewById(R.id.ll_missInfo);
        back = findViewById(R.id.back);

//        返回键
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailpersonActivity.this.finish();
            }
        });

//        根据ID获取头像

        RequestParams params = new RequestParams(GeneralSetting.getPersonsPicUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("MissPersonId", String.valueOf(personId));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//使用GSON将字符串转为对象
                Gson gson = new Gson();
                JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
                pics = (List<String>) jsonResult.getData();
                for (String name : pics
                        ) {
                    images.add(GeneralSetting.baseUrl + "/missPersonsPics/" + personId + "/" + name);
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


    }
}
