package com.gtt.app;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtt.app.fragment.NewsCenterFragment;
import com.gtt.app.fragment.ReleaseInfoFragment;
import com.gtt.app.fragment.UserInfoFragment;
import com.gtt.app.fragment.InfoScanFragment;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.replace_content)
    LinearLayout replace_content;

    private TextView mTextMessage;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.replace_content, new InfoScanFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_releaseInfo:
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.replace_content, new ReleaseInfoFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_siteNews:
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.replace_content, new NewsCenterFragment());
                    transaction.commit();
                    return true;

                case R.id.navigation_userInfo:
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.replace_content, new UserInfoFragment());
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        默认选中，具有点击效果
        navigation.getMenu().getItem(0).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
