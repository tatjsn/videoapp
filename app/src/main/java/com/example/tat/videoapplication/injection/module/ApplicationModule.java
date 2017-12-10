package com.example.tat.videoapplication.injection.module;

import android.app.Application;
import android.content.Context;

import com.example.tat.videoapplication.BuildConfig;
import com.example.tat.videoapplication.data.remote.VideoService;
import com.example.tat.videoapplication.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    VideoService provideVideoService() {
        return VideoService.Creator.create(BuildConfig.VIDEO_API_BASE_URL);
    }
}
