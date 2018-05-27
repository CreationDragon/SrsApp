package com.gtt.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gtt.app.R;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.Missingpersons;
import com.gtt.app.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Creat on 2018/4/20.
 */

public class MissInfoAdapter extends BaseAdapter {
    private Context newcontext;
    private List<Missingpersons> missingpersonsList = new ArrayList<>();
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_feature)
    TextView tv_feature;
    @BindView(R.id.tv_time)
    TextView tv_time;
    private ImageView iv_head;
    ImageView iv_gender_man;
    ImageView iv_gender_woman;

    public MissInfoAdapter(Context context, List<Missingpersons> list) {
        newcontext = context;
        missingpersonsList = list;
    }

    @Override
    public int getCount() {
        return missingpersonsList.size();
    }

    @Override
    public Missingpersons getItem(int i) {
        return missingpersonsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Missingpersons missingpersons = new Missingpersons();
        missingpersons = getItem(i);
        View missInfo;
        if (view == null) {
            missInfo = LayoutInflater.from(newcontext).inflate(R.layout.missinfo, viewGroup, false);
        } else {
            missInfo = view;
        }

        iv_head = missInfo.findViewById(R.id.iv_head);
        tv_time = missInfo.findViewById(R.id.tv_time);
        tv_feature = missInfo.findViewById(R.id.tv_feature);
        tv_name = missInfo.findViewById(R.id.tv_name);
        iv_gender_man = missInfo.findViewById(R.id.iv_gender_man);
        iv_gender_woman = missInfo.findViewById(R.id.iv_gender_woman);

        if (missingpersons.getPsersonsPic() != null) {

            Picasso.get().load(Uri.parse(GeneralSetting.baseUrl + "missImage/" + missingpersons.getPsersonsPic()).toString()).into(iv_head);
        } else {
            Picasso.get().load(Uri.parse(GeneralSetting.baseUrl + "headpic/qyc.jpg").toString()).into(iv_head);
        }

        tv_name.setText(missingpersons.getPersonsName());
        tv_feature.setText(missingpersons.getPersonsFeature());
        tv_time.setText(missingpersons.getPersonsReleasedate());

        if (missingpersons.getPersonsGender().equals("男")) {
            iv_gender_man.setVisibility(View.VISIBLE);
            iv_gender_woman.setVisibility(View.GONE);
        } else {
            iv_gender_woman.setVisibility(View.VISIBLE);
            iv_gender_man.setVisibility(View.GONE);
        }

        return missInfo;

    }
}
