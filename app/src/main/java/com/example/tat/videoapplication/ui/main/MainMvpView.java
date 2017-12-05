package com.example.tat.videoapplication.ui.main;

import com.example.tat.videoapplication.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    void showVideos();
    void showVideosEmpty();
    void showError();
}
