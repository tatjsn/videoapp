package com.example.tat.videoapplication.injection.module;

import android.app.Application;
import android.content.Context;

import com.example.tat.videoapplication.data.DataManager;
import com.example.tat.videoapplication.data.remote.VideoService;
import com.example.tat.videoapplication.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
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
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    VideoService provideVideoService() {
        return mock(VideoService.class);
    }

}