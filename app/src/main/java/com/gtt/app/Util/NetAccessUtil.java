package com.gtt.app.Util;

import android.widget.Toast;

import com.google.gson.Gson;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Creat on 2018/4/22.
 */

public class NetAccessUtil {
    JsonResult jsonResult = new JsonResult();

    public Object getNetAccessData(String url, String paramName, Object paramValue) {
        //        根据ID获取详细失踪信息

        RequestParams params = new RequestParams(url);
//                    params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter(paramName, String.valueOf(paramValue));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//使用GSON将字符串转为对象
                Gson gson = new Gson();
                jsonResult = gson.fromJson(result, JsonResult.class);
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
        return jsonResult.getData();
    }

}
