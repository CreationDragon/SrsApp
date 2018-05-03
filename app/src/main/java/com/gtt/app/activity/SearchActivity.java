package com.gtt.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.gtt.app.R;
import com.gtt.app.adapter.SearchResultAdapter;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;
import com.gtt.app.model.News;
import com.gtt.app.model.User;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Creat on 2018/4/22.
 */

public class SearchActivity extends AppCompatActivity {
    SearchView sv_missPerson;
    ListView lv_search_result;
    ImageView iv_NoResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        x.Ext.init(getApplication());

//        获取前一个界面传递过来的参数
        Intent intent = this.getIntent();
        final String keyWord = intent.getStringExtra("searchContent");

        sv_missPerson = findViewById(R.id.sv_missPerson);
        lv_search_result = findViewById(R.id.lv_search_result);
        iv_NoResult = findViewById(R.id.iv_NoResult);


        sv_missPerson.setQueryHint(keyWord);
        sv_missPerson.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getResult(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

//        网络访问获取搜索结果
        RequestParams params = new RequestParams(GeneralSetting.infoSearchUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("keyWord", keyWord);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//使用GSON将字符串转为对象
                Gson gson = new Gson();
                JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                final List<Missingpersons> missingpersonsList = JSON.parseArray(String.valueOf(jsonResult.getData()), Missingpersons.class);

                if (missingpersonsList.size() != 0) {
                    iv_NoResult.setVisibility(View.GONE);
                    lv_search_result.setVisibility(View.VISIBLE);
                    lv_search_result.setAdapter(new SearchResultAdapter(getApplicationContext(), missingpersonsList));

                    //        ListView中item的点击事件
                    lv_search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Missingpersons missingpersons = new Missingpersons();
                            missingpersons = missingpersonsList.get(i);
                            Intent intent = new Intent();
                            intent.setClass(getApplication(), MissPersonDetailActivity.class);
                            intent.putExtra("missPersonId", missingpersons.getPersonsId());
                            startActivity(intent);
                        }
                    });


                } else {
                    iv_NoResult.setVisibility(View.VISIBLE);
                    lv_search_result.setVisibility(View.GONE);
                }
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

    private void getResult(final String keyWord) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //        网络访问获取搜索结果
                RequestParams params = new RequestParams(GeneralSetting.infoSearchUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("keyWord", keyWord);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//使用GSON将字符串转为对象
                        Gson gson = new Gson();
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                        final List<Missingpersons> missingpersonsList = JSON.parseArray(String.valueOf(jsonResult.getData()), Missingpersons.class);

                        if (missingpersonsList.size() != 0) {
                            iv_NoResult.setVisibility(View.GONE);
                            lv_search_result.setVisibility(View.VISIBLE);
                            lv_search_result.setAdapter(new SearchResultAdapter(getApplicationContext(), missingpersonsList));

                            //        ListView中item的点击事件
                            lv_search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Missingpersons missingpersons = new Missingpersons();
                                    missingpersons = missingpersonsList.get(i);
                                    Intent intent = new Intent();
                                    intent.setClass(getApplication(), MissPersonDetailActivity.class);
                                    intent.putExtra("missPersonId", missingpersons.getPersonsId());
                                    startActivity(intent);
                                }
                            });


                        } else {
                            iv_NoResult.setVisibility(View.VISIBLE);
                            lv_search_result.setVisibility(View.GONE);
                        }
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
