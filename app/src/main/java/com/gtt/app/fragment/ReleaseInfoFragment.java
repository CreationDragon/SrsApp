package com.gtt.app.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.gtt.app.MainActivity;
import com.gtt.app.R;
import com.gtt.app.activity.MissPersonDetailActivity;
import com.gtt.app.adapter.MissInfoAdapter;
import com.gtt.app.general.GeneralSetting;
import com.gtt.app.model.JsonResult;
import com.gtt.app.model.Missingpersons;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.toolsfinal.StringUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Creat on 2018/4/23.
 */

public class ReleaseInfoFragment extends Fragment implements View.OnClickListener {
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Context context;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    @BindView(R.id.back)
    ImageView iv_back;
    @ViewInject(R.id.tv_submit)
    TextView tv_submit;
    @ViewInject(R.id.iv_upload_image)
    ImageView iv_upload_image;
    @ViewInject(R.id.iv_preview)
    ImageView iv_preview;
    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_user_age)
    EditText et_user_age;
    @BindView(R.id.et_user_bodyHeight)
    EditText et_user_bodyHeight;
    @BindView(R.id.et_user_address)
    EditText et_user_address;
    @BindView(R.id.et_user_contact)
    EditText et_user_contact;
    @BindView(R.id.et_user_dress)
    EditText et_user_dress;
    @BindView(R.id.et_user_feature)
    EditText et_user_featuree;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rg_IsDna)
    RadioGroup rg_IsDna;
    @BindView(R.id.rb_man)
    RadioButton rb_man;
    @BindView(R.id.rb_woman)
    RadioButton rb_woman;
    @BindView(R.id.rb_yes)
    RadioButton rb_yes;
    @BindView(R.id.rb_no)
    RadioButton rb_no;
    RadioButton gender;
    RadioButton IsDna;
    //    图片文件路径
    String imagePath = null;
    //    Bitmap图片
    Bitmap bmp = null;
    //    图片文件
    File file = null;
    private Integer userId = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_releaseinfo, container, false);
        context = getContext();

//        发现组件
        iv_back = view.findViewById(R.id.back);
        iv_upload_image = view.findViewById(R.id.iv_upload_image);
        tv_submit = view.findViewById(R.id.tv_submit);
        iv_preview = view.findViewById(R.id.iv_preview);
        et_user_address = view.findViewById(R.id.et_user_address);
        et_user_name = view.findViewById(R.id.et_user_name);
        et_user_age = view.findViewById(R.id.et_user_age);
        et_user_bodyHeight = view.findViewById(R.id.et_user_bodyHeight);
        et_user_contact = view.findViewById(R.id.et_user_contact);
        et_user_dress = view.findViewById(R.id.et_user_dress);
        et_user_featuree = view.findViewById(R.id.et_user_feature);
        rb_man = view.findViewById(R.id.rb_man);
        rb_woman = view.findViewById(R.id.rb_woman);
        rb_yes = view.findViewById(R.id.rb_yes);
        rb_no = view.findViewById(R.id.rb_no);
        rg_gender = view.findViewById(R.id.rg_gender);
        rg_IsDna = view.findViewById(R.id.rg_IsDna);


//        点击事件
        iv_upload_image.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectGenderRadioBtn();
            }
        });

        rg_IsDna.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectIsDnaRadioBtn();
            }
        });


        return view;
    }

    private void selectIsDnaRadioBtn() {
        IsDna = getActivity().findViewById(rg_IsDna.getCheckedRadioButtonId());
    }

    private void selectGenderRadioBtn() {
        gender = getActivity().findViewById(rg_gender.getCheckedRadioButtonId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_upload_image:
// 以带结果的方式启动Intent，这样就可以拿到图片地址
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.setType("image/*");
                intent1.putExtra("crop", true);
                intent1.putExtra("return-data", true);
                startActivityForResult(intent1, 0);
                break;

            case R.id.tv_submit:
                Missingpersons missingpersons = new Missingpersons();
                missingpersons.setPersonsName(et_user_name.getText().toString());
                missingpersons.setPersonsAge(Integer.parseInt(et_user_age.getText().toString()));
                missingpersons.setPersonsGender(gender.getText().toString());
                missingpersons.setPersonsBodyheight(Integer.parseInt(et_user_bodyHeight.getText().toString()));
                missingpersons.setPersonsFeature(et_user_featuree.getText().toString());
                missingpersons.setPersonsAddress(et_user_address.getText().toString());
                missingpersons.setPersonsDna(judgeDna(IsDna.getText().toString()));
                missingpersons.setPersonsDress(et_user_dress.getText().toString());
                missingpersons.setPersonsContact(et_user_contact.getText().toString());

                Gson gson = new Gson();
                String str = gson.toJson(missingpersons);

                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String name = preferences.getString("user_name", "");
                userId = preferences.getInt("user_id", 0);
                if (!StringUtils.isEmpty(name)) {
                    realeaseInfo(str);
                } else {
                    Toast.makeText(getActivity(), "您尚未登录，请登录。", Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }

    //    判断是否检测过DNA
    private Integer judgeDna(String s) {
        if ("是".equals("s")) {
            return 0;
        } else {
            return 1;
        }
    }

    //    发布信息
    private void realeaseInfo(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(GeneralSetting.releaseMissInfoUrl);
//                    params.setSslSocketFactory(...); // 设置ssl
                params.addQueryStringParameter("missPersonsInfo", str);
                params.addQueryStringParameter("userID", "1");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("successResult:       ", result);
                        Gson gson = new Gson();
                        JsonResult jsonResult = gson.fromJson(result, JsonResult.class);
                        if (jsonResult.result.equals("success")) {
//                            进行图片上传
                            uploadImage(file);

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

    private void uploadImage(final File bmp) {
        final ProgressDialog dia = new ProgressDialog(getActivity());
        dia.setMessage("加载中....");
        dia.show();


        RequestParams params = new RequestParams(GeneralSetting.uploadImageUrl);
        List<KeyValue> list = new ArrayList<KeyValue>();
        list.add(new KeyValue("file", bmp));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        params.setMultipart(true);
//        params.addBodyParameter("file", bmp, "multipart/form-data");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //加载成功回调，返回获取到的数据
                Toast.makeText(getActivity(), "消息发布成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                dia.dismiss();//加载完成
                Toast.makeText(getActivity(), "消息发布成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //uri代表的就是图片在内容解析者所在的地址
        if (requestCode == 0) {
            Uri uri = data.getData();
            ContentResolver cr = context.getContentResolver();
            try {
                bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                file = saveBitmapFile(bmp);
                imagePath = getDataColumn(getActivity(), uri);
                iv_preview.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private File saveBitmapFile(Bitmap bmp) {
        File SDfile = Environment.getExternalStorageDirectory();

        //将要保存图片的路径
        file = new File(SDfile.toString() + "/Pictures");

        boolean bFile = file.exists();

        if (bFile == true) {
            System.out.println("The folder exists.");
        } else {
            System.out.println("The folder do not exist,now trying to create a one...");
            file.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file = new File(file + "/1.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private String getDataColumn(Context context, final Uri uri) {
        File myFile = new File(uri.getPath());
        imagePath = myFile.getAbsolutePath();

        return imagePath;
    }
}
