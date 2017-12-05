package com.example.tat.videoapplication;

import android.app.Application;
import android.content.Context;

import com.example.tat.videoapplication.injection.component.ApplicationComponent;
import com.example.tat.videoapplication.injection.component.DaggerApplicationComponent;
import com.example.tat.videoapplication.injection.module.ApplicationModule;

public class VideoApplication extends Application {
    ApplicationComponent mApplicationComponent;

    public static VideoApplication get(Context context) {
        return (VideoApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}
