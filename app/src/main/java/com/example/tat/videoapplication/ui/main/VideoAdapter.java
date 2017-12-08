package com.example.tat.videoapplication.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tat.videoapplication.R;
import com.example.tat.videoapplication.data.model.Video;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Video> mVideos;

    @Inject
    public VideoAdapter() {
        mVideos = Collections.emptyList();
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = mVideos.get(position);
        holder.title.setText(video.title());
        holder.presenterName.setText(video.presenterName());
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.presenterName)
        TextView presenterName;

        VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}