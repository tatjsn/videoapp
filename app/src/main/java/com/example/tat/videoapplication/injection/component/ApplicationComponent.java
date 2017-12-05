package com.example.tat.videoapplication.injection.component;

import android.app.Application;
import android.content.Context;

import com.example.tat.videoapplication.injection.ApplicationContext;
import com.example.tat.videoapplication.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ApplicationContext
    Context context();
    Application application();
}
