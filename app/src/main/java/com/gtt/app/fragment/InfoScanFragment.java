package com.gtt.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.Util.GlideImageLoader;
import com.gtt.app.activity.DetailpersonActivity;
import com.gtt.app.activity.MissPersonDetailActivity;
import com.gtt.app.activity.SearchActivity;
import com.gtt.app.adapter.MissInfoAdapter;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;
import com.gtt.app.model.News;
import com.gtt.app.model.User;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Creat on 2018/4/20.
 */

public class InfoScanFragment extends Fragment {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_missInfo)
    ListView ll_missInfo;
    SearchView searchView;
    private List<Missingpersons> missingpersonsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        x.Ext.init(getActivity().getApplication());
        View view = inflater.inflate(R.layout.fragment_newscenter, container, false);
        banner = view.findViewById(R.id.banner);
        ll_missInfo = view.findViewById(R.id.ll_missInfo);
        searchView = view.findViewById(R.id.sv_missPerson);


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


        RequestParams params = new RequestParams(GeneralSetting.missInfoUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("type", "0");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//使用fastjson将字符串转为对象
                JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                final List<Missingpersons> missingpersonsList = JSON.parseArray(String.valueOf(jsonResult.getData()), Missingpersons.class);
                ll_missInfo.setAdapter(new MissInfoAdapter(getContext(), missingpersonsList));

//                listView的Item的点击事件
                ll_missInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Missingpersons missingpersons = missingpersonsList.get(i);
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), MissPersonDetailActivity.class);
                        intent.putExtra("missPersonId", missingpersons.getPersonsId());
                        startActivity(intent);
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


//        搜索框功能设置
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent();
                intent.putExtra("searchContent", s);
                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }
}
