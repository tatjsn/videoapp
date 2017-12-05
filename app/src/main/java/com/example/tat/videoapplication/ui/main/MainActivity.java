package com.example.tat.videoapplication.ui.main;

import android.os.Bundle;

import com.example.tat.videoapplication.R;
import com.example.tat.videoapplication.injection.component.ActivityComponent;
import com.example.tat.videoapplication.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {
    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    @Override
    protected void detachPresenter() {

    }

    @Override
    public void showVideos() {

    }

    @Override
    public void showVideosEmpty() {

    }

    @Override
    public void showError() {

    }
}
