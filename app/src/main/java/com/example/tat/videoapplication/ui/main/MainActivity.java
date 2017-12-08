package com.example.tat.videoapplication.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.tat.videoapplication.R;
import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.injection.component.ActivityComponent;
import com.example.tat.videoapplication.ui.base.BaseActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {
    @Inject
    MainPresenter mMainPresenter;

    @Inject
    VideoAdapter mVideoAdapter;

    @BindView(R.id.video_list)
    RecyclerView mVideoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mVideoListView.setAdapter(mVideoAdapter);
        mVideoListView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
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
        mVideoAdapter.setVideos(videos);
        mVideoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showVideosEmpty() {
        mVideoAdapter.setVideos(Collections.emptyList());
        mVideoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Log.d("tatdbg", "videos error");
    }
}
