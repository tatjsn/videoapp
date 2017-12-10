package com.example.tat.videoapplication.util;

import com.example.tat.videoapplication.data.model.Video;

public class TestDataFactory {
    public static Video makeVideo(String title) {
        return Video.builder()
                .setTitle(title)
                .setDescription("Description")
                .setPresenterName("PresenterName")
                .setThumbnailUrl("https://example.com/thumbnail.jpg")
                .setVideoUrl("https://example.com/video.jpg")
                .setVideoDuration(60 * 1000)
                .build();
    }
}
