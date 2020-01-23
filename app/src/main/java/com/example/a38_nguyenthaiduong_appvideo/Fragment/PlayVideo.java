package com.example.a38_nguyenthaiduong_appvideo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.a38_nguyenthaiduong_appvideo.Define.Define;
import com.example.a38_nguyenthaiduong_appvideo.R;
import com.example.a38_nguyenthaiduong_appvideo.databinding.ActivityPlayVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class PlayVideo extends AppCompatActivity {
    ActivityPlayVideoBinding binding;
    Intent intent;
    List<String> laymangmp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_video);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        laymangmp4 = new ArrayList<>();
        intent = getIntent();

        String laytenphim = intent.getStringExtra(Define.STRING_INPUTTENPHIM);
        binding.tvplayvideo.setText(laytenphim);

        laymangmp4 = intent.getStringArrayListExtra(Define.STRING_INPUTMANGMP4);
        int layvitri = intent.getIntExtra(Define.STRING_INPUTVITRI, 0);

        for (int i = 0; i < laymangmp4.size(); i++) {
            binding.vvplayvideo.setVideoPath(laymangmp4.get(layvitri));
            binding.vvplayvideo.start();
        }
    }
}
