package com.gtt.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gtt.app.R;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.Missingpersons;
import com.gtt.app.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Creat on 2018/4/22.
 */

public class SearchResultAdapter extends BaseAdapter {
    Context context;
    List<Missingpersons> missingpersonsList = new ArrayList<>();

    TextView tv_name;
    TextView tv_feature;
    TextView tv_time;
    private ImageView iv_head;

    public SearchResultAdapter(Context applicationContext, List<Missingpersons> missingpersons) {
        context = applicationContext;
        missingpersonsList = missingpersons;
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
        missingpersons = (Missingpersons) getItem(i);
        View serachResult = null;

        if (view == null) {
            serachResult = LayoutInflater.from(context).inflate(R.layout.missinfo, viewGroup, false);
        } else {
            serachResult = view;
        }

        iv_head = serachResult.findViewById(R.id.iv_head);
        tv_time = serachResult.findViewById(R.id.tv_time);
        tv_feature = serachResult.findViewById(R.id.tv_feature);
        tv_name = serachResult.findViewById(R.id.tv_name);

        if (missingpersons.getPsersonsPic() != null) {

            Picasso.get().load(Uri.parse(GeneralSetting.baseUrl + "missImage/" + missingpersons.getPsersonsPic()).toString()).into(iv_head);
        } else {
            Picasso.get().load(Uri.parse(GeneralSetting.baseUrl + "headpic/qyc.jpg").toString()).into(iv_head);
        }

        tv_name.setText(missingpersons.getPersonsName());
        tv_feature.setText(missingpersons.getPersonsFeature());
        tv_time.setText(missingpersons.getPersonsReleasedate());


        return serachResult;

    }
}
