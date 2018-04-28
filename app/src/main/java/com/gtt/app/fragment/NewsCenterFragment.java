package com.gtt.app.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.Util.GlideImageLoader;
import com.gtt.app.Util.NetAccessUtil;
import com.gtt.app.activity.DetailNews;
import com.gtt.app.activity.MorenewsActivity;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;
import com.gtt.app.model.News;
import com.youth.banner.Banner;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creat on 2018/4/24.
 */

public class NewsCenterFragment extends Fragment implements View.OnClickListener {
    Banner banner;
    @ViewInject(R.id.ll_success_case)
    LinearLayout ll_success_case;
    LinearLayout ll_site_news;
    LinearLayout ll_anti_tips;
    News news = new News();
    Gson gson = new Gson();
    String myJson = null;
    List<News> successCaseList = new ArrayList<>();
    List<News> siteNewsList = new ArrayList<>();
    List<News> antiTipsList = new ArrayList<>();
    JsonResult jsonResult = new JsonResult();

    @ViewInject(R.id.tv_more_case)
    TextView tv_more_case;
    @ViewInject(R.id.tv_more_notice)
    TextView tv_more_notice;
    @ViewInject(R.id.tv_more_tips)
    TextView tv_more_tips;

    private final int UPDATE_SUCCESS_CASE = 1;
    private final int UPDATE_SITE_NEWS = 2;
    private final int UPDATE_ANTI_TIPS = 3;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

//        初始化XUtil
        x.Ext.init(getActivity().getApplication());
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
//                    更新成功案例
                    case UPDATE_SUCCESS_CASE:
                        for (int i = 0; i < 3; i++) {
                            TextView tv = new TextView(getContext());
//                            获取新闻id
                            final int newsId = successCaseList.get(i).getNewsId();
                            tv.setText(successCaseList.get(i).getNewsTitle());
                            tv.setGravity(Gravity.CENTER_VERTICAL);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            params.setMargins(0, 0, 0, 10);
                            tv.setLayoutParams(params);
                            ll_success_case.addView(tv);
                            ll_success_case.requestLayout();
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.putExtra("caseId", newsId);
                                    intent.setClass(getContext(), DetailNews.class);
                                    startActivity(intent);
                                }
                            });
                        }

                        break;

//                        更新站内新闻
                    case UPDATE_SITE_NEWS:
                        for (int i = 0; i < 3; i++) {
                            TextView tv = new TextView(getContext());
                            final int newsId = siteNewsList.get(i).getNewsId();
                            tv.setText(siteNewsList.get(i).getNewsTitle());
                            tv.setGravity(Gravity.CENTER_VERTICAL);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            params.setMargins(0, 0, 0, 10);
                            tv.setLayoutParams(params);
                            ll_site_news.addView(tv);
                            ll_site_news.requestLayout();
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.putExtra("noticeId", newsId);
                                    intent.setClass(getContext(), DetailNews.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        break;

//                        更新防骗措施
                    case UPDATE_ANTI_TIPS:
                        for (int i = 0; i < 3; i++) {
                            TextView tv = new TextView(getContext());
                            final int newsId = antiTipsList.get(i).getNewsId();
                            tv.setText(antiTipsList.get(i).getNewsTitle());
                            tv.setGravity(Gravity.CENTER_VERTICAL);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            params.setMargins(0, 0, 0, 10);
                            tv.setLayoutParams(params);
                            ll_anti_tips.addView(tv);
                            ll_anti_tips.requestLayout();
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.putExtra("tipsId", newsId);
                                    intent.setClass(getContext(), DetailNews.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        break;
                }
            }
        };

        View view = inflater.inflate(R.layout.fragment_newscenter, container, false);
//寻找组件id
        banner = view.findViewById(R.id.banner);
        ll_success_case = view.findViewById(R.id.ll_success_case);
        ll_site_news = view.findViewById(R.id.ll_site_news);
        ll_anti_tips = view.findViewById(R.id.ll_anti_tips);
        tv_more_case = view.findViewById(R.id.tv_more_case);
        tv_more_notice = view.findViewById(R.id.tv_more_notice);
        tv_more_tips = view.findViewById(R.id.tv_more_tips);

//        more的点击事件
        tv_more_case.setOnClickListener(this);
        tv_more_notice.setOnClickListener(this);
        tv_more_tips.setOnClickListener(this);

        List<String> images = new ArrayList<>();
        images.add(GeneralSetting.baseUrl + "/headpic/3.jpg");
        images.add(GeneralSetting.baseUrl + "/headpic/4.jpg");
        images.add(GeneralSetting.baseUrl + "/headpic/5.jpg");
        images.add(GeneralSetting.baseUrl + "/headpic/8.jpg");

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

//子线程更新“成功案例”
        new Thread(new Runnable() {
            @Override
            public void run() {

                RequestParams params = new RequestParams(GeneralSetting.getSuccessCaseUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("type", String.valueOf(0));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//使用GSON将字符串转为对象
                        Gson gson = new Gson();
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                        myJson = gson.toJson(jsonResult.getData());
                        successCaseList = JSON.parseArray(myJson, News.class);
                        Message message = new Message();
                        message.what = UPDATE_SUCCESS_CASE;
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


//        子线程更新“站内新闻”
        new Thread(new Runnable() {
            @Override
            public void run() {

                RequestParams params = new RequestParams(GeneralSetting.getSiteNewsUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("type", String.valueOf(1));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//使用GSON将字符串转为对象
                        Gson gson = new Gson();
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                        myJson = gson.toJson(jsonResult.getData());
                        siteNewsList = JSON.parseArray(myJson, News.class);
                        Message message = new Message();
                        message.what = UPDATE_SITE_NEWS;
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


        //        子线程更新“防骗技巧”
        new Thread(new Runnable() {
            @Override
            public void run() {

                RequestParams params = new RequestParams(GeneralSetting.getAntiTipsUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("type", String.valueOf(2));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//使用GSON将字符串转为对象
                        Gson gson = new Gson();
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                        myJson = gson.toJson(jsonResult.getData());
                        antiTipsList = JSON.parseArray(myJson, News.class);
                        Message message = new Message();
                        message.what = UPDATE_ANTI_TIPS;
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
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_more_case:
                intent.putExtra("type", 0);
                break;
            case R.id.tv_more_notice:
                intent.putExtra("type", 1);
                break;
            case R.id.tv_more_tips:
                intent.putExtra("type", 2);
                break;
        }
        intent.setClass(getContext(), MorenewsActivity.class);
        startActivity(intent);
    }
}
