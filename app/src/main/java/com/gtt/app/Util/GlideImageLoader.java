package com.gtt.app.Util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.gtt.app.general.GeneralSetting;
import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoaderInterface;

/**
 * Created by Creat on 2018/4/20.
 */

public class GlideImageLoader implements ImageLoaderInterface {
    @Override
    public void displayImage(Context context, Object path, View imageView) {
        Picasso.get().load(path.toString()).into((ImageView) imageView);
    }

    @Override
    public View createImageView(Context context) {
        return null;
    }
}
