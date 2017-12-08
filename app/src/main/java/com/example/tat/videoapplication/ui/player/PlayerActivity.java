package com.example.tat.videoapplication.ui.player;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.tat.videoapplication.R;
import com.example.tat.videoapplication.data.model.Video;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerActivity extends AppCompatActivity {
    private SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;
    private Video video;
    private boolean isSystemUiHidden;

    @BindView(R.id.video_view)
    SimpleExoPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        playWhenReady = true;
        video = getIntent().hasExtra("video") ?
                getIntent().getParcelableExtra("video") : null;
        if (video != null) {
            getSupportActionBar().setTitle(video.title());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSystemUi(getResources().getConfiguration());
        if ((Build.VERSION.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (video == null) {
            return;
        }
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(video.videoUrl()));
        player.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory("videoapplication"),
                new DefaultExtractorsFactory(), null, null);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        isSystemUiHidden = true;
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void restoreSystemUi() {
        if (isSystemUiHidden) {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            isSystemUiHidden = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateSystemUi(newConfig);
    }

    private void updateSystemUi(Configuration config) {
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi();
        } else {
            restoreSystemUi();
        }
    }
}
