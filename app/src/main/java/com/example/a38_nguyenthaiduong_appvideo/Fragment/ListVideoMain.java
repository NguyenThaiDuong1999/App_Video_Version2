package com.example.a38_nguyenthaiduong_appvideo.Fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a38_nguyenthaiduong_appvideo.Define.Define;
import com.example.a38_nguyenthaiduong_appvideo.Interface.IonClickAvatar;
import com.example.a38_nguyenthaiduong_appvideo.R;
import com.example.a38_nguyenthaiduong_appvideo.Object.Video;
import com.example.a38_nguyenthaiduong_appvideo.Adapter.VideoAdapter;
import com.example.a38_nguyenthaiduong_appvideo.databinding.RvMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ListVideoMain extends Fragment {

    RvMainBinding binding;
    List<Video> videos;
    VideoAdapter videoAdapter;
    String urlApi = Define.STRING_HOTVIDEO_CATEGORY;
    String mp4;
    Intent intent;
    List<String> mangmp4;

    public static ListVideoMain newInstance() {

        Bundle args = new Bundle();

        ListVideoMain fragment = new ListVideoMain();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rv_main, container, false);
        videos = new ArrayList<>();
        mangmp4 = new ArrayList<>();
        new DoGetData(urlApi).execute();
        videoAdapter = new VideoAdapter(videos);
        binding.rvmain.setAdapter(videoAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);

        binding.rvmain.setLayoutManager(layoutManager);
        videoAdapter.setIonClickAvatar(new IonClickAvatar() {
            @Override
            public void onClickAvatar(String tenphim, int i) {
                intent = new Intent(getContext(), PlayVideo.class);
                intent.putExtra(Define.STRING_INPUTTENPHIM, tenphim);
                intent.putStringArrayListExtra(Define.STRING_INPUTMANGMP4, (ArrayList<String>) mangmp4);
                intent.putExtra(Define.STRING_INPUTVITRI, i);
                startActivity(intent);
            }
        });
        //Animation Drawer
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.anidrawer1), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.anidrawer2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.anidrawer3), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.anidrawer4), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.anidrawer5), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.anidrawer6), 3000);
        animationDrawable.setOneShot(false);
        binding.imganimationdrawer.setImageDrawable(animationDrawable);
        animationDrawable.start();

        return binding.getRoot();
    }

    class DoGetData extends AsyncTask<Void, Void, Void> {
        String result = "";
        String urlApi;

        public DoGetData(String urlApi) {
            this.urlApi = urlApi;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.prBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(urlApi);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    result += (char) byteCharacter;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String avatar = jsonObject.getString("avatar");
                    String title = jsonObject.getString("title");
                    videos.add(new Video(avatar, title));
                    mp4 = jsonObject.getString("file_mp4");
                    mangmp4.add(mp4);
                }

                videoAdapter.notifyDataSetChanged();
                binding.prBar.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
