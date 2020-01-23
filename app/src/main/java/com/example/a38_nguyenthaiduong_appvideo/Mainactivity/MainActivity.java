package com.example.a38_nguyenthaiduong_appvideo.Mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.a38_nguyenthaiduong_appvideo.Fragment.ListVideoMain;
import com.example.a38_nguyenthaiduong_appvideo.Fragment.Trending;
import com.example.a38_nguyenthaiduong_appvideo.R;
import com.example.a38_nguyenthaiduong_appvideo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    ActivityMainBinding binding;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //ẩn thanh tiêu đề
        actionBar = getSupportActionBar();
        actionBar.hide();

        //gọi Fragment chứa list video ở home screen
        getFragment(new ListVideoMain());

        //
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    getFragment(new ListVideoMain());
                    break;
                case R.id.nav_trending:
                    getFragment(new Trending());
                    break;
            }
            return true;
        }
    };

    public void getFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "getFragment: " + e.getMessage());
        }
    }
}
