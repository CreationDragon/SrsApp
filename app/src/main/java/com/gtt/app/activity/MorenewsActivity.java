package com.gtt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.adapter.MorenewsAdapter;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.News;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creat on 2018/4/26.
 */

public class MorenewsActivity extends AppCompatActivity {
    String myJson = null;
    News news = new News();
    List<News> newsList = new ArrayList<>();

    @ViewInject(R.id.lv_morenews)
    ListView lv_morenews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morenews);

//        发现组件
        lv_morenews = findViewById(R.id.lv_morenews);

        Intent intent = this.getIntent();
        final int type = intent.getIntExtra("type", -1);
        String url = null;
        if (type == 0) {
            url = GeneralSetting.getSuccessCaseUrl;
        } else if (type == 1) {
            url = GeneralSetting.getSiteNewsUrl;
        } else {
            url = GeneralSetting.getAntiTipsUrl;
        }

//        套路
        final String finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(finalUrl);
//                    params.setSslSocketFactory(...); // 设置ssls
                params.addQueryStringParameter("type", String.valueOf(type));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//使用fastjson将字符串转为对象
                        Gson gson = new Gson();
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
//使用gson将对象转换成json字符串
                        myJson = gson.toJson(jsonResult.getData());
                        newsList = JSON.parseArray(myJson, News.class);

                        lv_morenews.setAdapter(new MorenewsAdapter(getApplicationContext(), newsList));

                        lv_morenews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                TODO  ListView的Item点击事件
                                News news = new News();
                                news = newsList.get(i);
                                Intent intent1 = new Intent();
                                intent1.setClass(getApplicationContext(), DetailNews.class);
                                if (news.getNewsType() == 0) {
                                    intent1.putExtra("caseId", news.getNewsId());
                                } else if (news.getNewsType() == 1) {
                                    intent1.putExtra("noticeId", news.getNewsId());
                                } else {
                                    intent1.putExtra("tipsId", news.getNewsId());
                                }
                                startActivity(intent1);
                            }
                        });

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
