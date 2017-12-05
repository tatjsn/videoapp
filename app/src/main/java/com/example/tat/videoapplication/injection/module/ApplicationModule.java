package com.example.tat.videoapplication.injection.module;

import android.app.Application;
import android.content.Context;

import com.example.tat.videoapplication.injection.ApplicationContext;

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
}
