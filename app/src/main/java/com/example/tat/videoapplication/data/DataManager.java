package com.example.tat.videoapplication.data;

import com.example.tat.videoapplication.data.local.DatabaseHelper;
import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.data.remote.VideoService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DataManager {
    private VideoService mVideoService;
    private DatabaseHelper mDatabaseHelper;

    @Inject
    public DataManager(VideoService videoService, DatabaseHelper databaseHelper) {
        mVideoService = videoService;
        mDatabaseHelper = databaseHelper;
    }

    public Observable<Video> syncVideos() {
        return mVideoService.getVideos()
                .concatMap(videos -> {
                    Observable<Video> result = mDatabaseHelper.setVideos(videos);
                    return result;
                });
    }

    public Observable<List<Video>> getVideos() {
        return mDatabaseHelper.getVideos().distinct();
    }
}
