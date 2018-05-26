package com.gtt.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gtt.app.R;
import com.gtt.app.activity.LoginActivity;
import com.gtt.app.activity.MissPersonDetailActivity;
import com.gtt.app.activity.UserInfoActivity;
import com.gtt.app.adapter.MissInfoAdapter;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;
import com.gtt.app.model.News;
import com.gtt.app.model.User;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Creat on 2018/4/20.
 */

public class UserInfoFragment extends Fragment implements View.OnClickListener {
    LinearLayout show_info;
    Button btn_login_register;
    User user = new User();
    ImageView iv_head;
    TextView tv_user_name;
    ImageView iv_user_man;
    ImageView iv_user_woman;
    Button btn_releaseInfo;
    Button btn_scanRecord;
    ListView lv_history;
    Integer userId = null;
    private List<Missingpersons> missingpersonsList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, container, false);

//        发现组件
        show_info = view.findViewById(R.id.show_info);
        btn_login_register = view.findViewById(R.id.btn_login_regist);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        iv_head = view.findViewById(R.id.iv_head);
        iv_user_man = view.findViewById(R.id.iv_user_man);
        iv_user_woman = view.findViewById(R.id.iv_user_woman);
        btn_releaseInfo = view.findViewById(R.id.btn_releaseInfo);
        btn_scanRecord = view.findViewById(R.id.btn_scanRecord);
        lv_history = view.findViewById(R.id.lv_history);


//        找SharedPreference
        SharedPreferences preferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = preferences.getString("user_name", "");
//        非空判断
        if (!name.equals("")) {
            userId = preferences.getInt("user_id", 0);
            show_info.setVisibility(View.VISIBLE);
            btn_login_register.setVisibility(View.GONE);
            tv_user_name.setText(name);
            Picasso.get().load(Uri.parse(GeneralSetting.baseUrl + "headpic/" + preferences.getInt("user_id", 0) + ".jpg").toString()).into(iv_head);

            if (preferences.getString("user_gender", "").equals("男")) {
                iv_user_man.setVisibility(View.VISIBLE);
                iv_user_woman.setVisibility(View.GONE);
            } else {
                iv_user_woman.setVisibility(View.VISIBLE);
                iv_user_man.setVisibility(View.GONE);
            }

        } else {
            show_info.setVisibility(View.GONE);
            btn_login_register.setVisibility(View.VISIBLE);
        }


//        默认点击事件（还必须得在线程里面）
        view.post(new Runnable() {
            @Override
            public void run() {
                btn_releaseInfo.performClick();
            }
        });


//        点击事件
        iv_head.setOnClickListener(this);
        btn_login_register.setOnClickListener(this);
        btn_releaseInfo.setOnClickListener(this);
        btn_scanRecord.setOnClickListener(this);
        return view;
    }

    //    点击事件的处理
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_head:
                intent = new Intent();
                intent.putExtra("userId", userId);
                intent.setClass(getActivity(), UserInfoActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_login_regist:
                intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_releaseInfo:
                btnInitialize();
                btn_releaseInfo.setBackgroundColor(Color.parseColor("#D1D1D1"));
                getReleaseInfo(userId);
                break;

            case R.id.btn_scanRecord:
                btnInitialize();
                btn_scanRecord.setBackgroundColor(Color.parseColor("#D1D1D1"));
                getRecordHistory(userId);
                break;

        }

    }

    //   获取查看历史
    private void getRecordHistory(final Integer userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(GeneralSetting.getRecordHistoryByUserIdUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("userId", String.valueOf(userId));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                        missingpersonsList = JSON.parseArray(String.valueOf(jsonResult.getData()), Missingpersons.class);
                        lv_history.setAdapter(new MissInfoAdapter(getContext(), missingpersonsList));

//                listView的Item的点击事件
                        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
//                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
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

    //    获取发布信息
    private void getReleaseInfo(final Integer userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(GeneralSetting.getMissPersonByUserIdUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("userId", String.valueOf(userId));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        JsonResult jsonResult = JSON.parseObject(result, JsonResult.class);
                        missingpersonsList = JSON.parseArray(String.valueOf(jsonResult.getData()), Missingpersons.class);
                        lv_history.setAdapter(new MissInfoAdapter(getContext(), missingpersonsList));

//                listView的Item的点击事件
                        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            }
        }).start();

    }


    private void btnInitialize() {
        btn_scanRecord.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btn_releaseInfo.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }
}
