package com.example.tat.videoapplication.ui.main;

import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {
    void showVideos(List<Video> videos);
    void showVideosEmpty();
    void showError();
    void playVideo(String videoUrl);
}
