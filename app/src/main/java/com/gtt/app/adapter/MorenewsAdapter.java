package com.gtt.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gtt.app.R;
import com.gtt.app.model.News;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creat on 2018/4/26.
 */

public class MorenewsAdapter extends BaseAdapter {

    @ViewInject(R.id.tv_news_title)
    TextView tv_news_title;
    @ViewInject(R.id.tv_news_time)
    TextView tv_news_time;

    List<News> news = new ArrayList<>();
    Context context = null;

    public MorenewsAdapter(Context applicationContext, List<News> newsList) {
        context = applicationContext;
        news = newsList;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public News getItem(int i) {
        return news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        News news = new News();
        news = getItem(i);

        View morenews;
        if (view == null) {
            morenews = LayoutInflater.from(context).inflate(R.layout.morenews_detail, viewGroup, false);
        } else {
            morenews = view;
        }
//        发现组件
        tv_news_title = morenews.findViewById(R.id.tv_news_title);
        tv_news_time = morenews.findViewById(R.id.tv_news_time);

//        赋值
        tv_news_title.setText(news.getNewsTitle());
        tv_news_time.setText(news.getNewsDate());
        return morenews;
    }
}
