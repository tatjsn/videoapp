package com.example.tat.videoapplication.injection.component;

import com.example.tat.videoapplication.injection.PerActivity;
import com.example.tat.videoapplication.injection.module.ActivityModule;
import com.example.tat.videoapplication.ui.main.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
