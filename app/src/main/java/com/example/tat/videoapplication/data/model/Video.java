package com.example.tat.videoapplication.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Video {
    public abstract String title();
    @SerializedName("presenter_name")
    public abstract String presenterName();
    public abstract String description();
    @SerializedName("thumbnail_url")
    public abstract String thumbnailUrl();
    @SerializedName("video_url")
    public abstract String videoUrl();
    @SerializedName("video_duration")
    public abstract int videoDuration();

    public static Builder builder() {
        return new AutoValue_Video.Builder();
    }

    public static TypeAdapter<Video> typeAdapter(Gson gson) {
        return new AutoValue_Video.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTitle(String title);
        public abstract Builder setPresenterName(String presenterName);
        public abstract Builder setDescription(String description);
        public abstract Builder setThumbnailUrl(String thumbnailUrl);
        public abstract Builder setVideoUrl(String videoUrl);
        public abstract Builder setVideoDuration(int videoDuration);
        public abstract Video build();
    }
}
