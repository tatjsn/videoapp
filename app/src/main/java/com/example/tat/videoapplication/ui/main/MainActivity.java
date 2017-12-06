package com.example.tat.videoapplication.ui.main;

import android.os.Bundle;
import android.util.Log;

import com.example.tat.videoapplication.R;
import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.injection.component.ActivityComponent;
import com.example.tat.videoapplication.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {
    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter.loadVideos();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mMainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mMainPresenter.detachView();
    }

    @Override
    public void showVideos(List<Video> videos) {
        Log.d("tatdbg", String.format("videos %d", videos.size()));
    }

    @Override
    public void showVideosEmpty() {
        Log.d("tatdbg", "videos empty");
    }

    @Override
    public void showError() {
        Log.d("tatdbg", "videos error");
    }
}
