package com.gtt.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.Util.NetAccessUtil;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.News;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Creat on 2018/4/26.
 */

public class DetailNews extends AppCompatActivity {
    @ViewInject(R.id.news_title)
    TextView news_title;
    @ViewInject(R.id.news_content)
    TextView news_content;
    @ViewInject(R.id.news_time)
    TextView news_time;

    News news = new News();
    String myJson = null;

    int UPDATE_NEWS = 1;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_NEWS) {
                news_title.setText(news.getNewsTitle());
                news_content.setText(news.getNewsContent());
                news_time.setText(news.getNewsDate());
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnews);

//        发现组件
        news_title = findViewById(R.id.news_title);
        news_content = findViewById(R.id.news_content);
        news_time = findViewById(R.id.news_time);

        Intent intent = this.getIntent();
        final int caseId = intent.getIntExtra("caseId", 0);
        final int noticeId = intent.getIntExtra("noticeId", 0);
        final int tipsId = intent.getIntExtra("tipsId", 0);

        int newsId = 0;
        String type = null;
        String url = null;

        if (caseId != 0) {
            newsId = caseId;
            type = "caseId";
            url=GeneralSetting.getSuccessCasesByIdUrl;
        } else if (noticeId != 0) {
            newsId = noticeId;
            type = "noticeId";
            url=GeneralSetting.getSiteNoticeByIdUrl;
        } else {
            newsId = tipsId;
            type = "tipsId";
            url=GeneralSetting.getAntiFraudTipsByIdUrl;
        }

//        套路，子线程访问网络(成功案例)
        final String finalType = type;
        final int finalNewsId = newsId;
        final String finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {

                RequestParams params = new RequestParams(finalUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter(finalType, String.valueOf(finalNewsId));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//使用GSON将字符串转为对象
                        Gson gson = new Gson();
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                        myJson = gson.toJson(jsonResult.getData());

                        news = gson.fromJson(myJson, News.class);

                        Message message = new Message();
                        message.what = UPDATE_NEWS;
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
