package com.example.tat.videoapplication.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.tat.videoapplication.data.model.Video;

public class Db {
    public static final class VideosTable {
        public static final String TABLE_NAME = "videos";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PRESENTER_NAME = "presenter_name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_VIDEO_URL = "video_url";
        public static final String COLUMN_VIDEO_DURATION = "video_duration";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_TITLE + " TEXT PRIMARY KEY, " +
                        COLUMN_PRESENTER_NAME + " TEXT NOT NULL, " +
                        COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_THUMBNAIL_URL + " TEXT NOT NULL, " +
                        COLUMN_VIDEO_URL + " TEXT NOT NULL, " +
                        COLUMN_VIDEO_DURATION + " INTEGER" +
                        " ); ";

        public static ContentValues toContentValues(Video video) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, video.title());
            values.put(COLUMN_PRESENTER_NAME, video.presenterName());
            values.put(COLUMN_DESCRIPTION, video.description());
            values.put(COLUMN_THUMBNAIL_URL, video.thumbnailUrl());
            values.put(COLUMN_VIDEO_URL, video.videoUrl());
            values.put(COLUMN_VIDEO_DURATION, video.videoDuration());
            return values;
        }

        public static Video parseCursor(Cursor cursor) {
            return Video.builder()
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)))
                    .setPresenterName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRESENTER_NAME)))
                    .setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)))
                    .setThumbnailUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMBNAIL_URL)))
                    .setVideoUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIDEO_URL)))
                    .setVideoDuration(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIDEO_DURATION)))
                    .build();
        }
    }
}
