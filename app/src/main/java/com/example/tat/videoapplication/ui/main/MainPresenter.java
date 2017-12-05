package com.example.tat.videoapplication.ui.main;

import com.example.tat.videoapplication.ui.base.BasePresenter;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainMvpView> {
    @Inject
    public MainPresenter() {
        // For Dagger
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
