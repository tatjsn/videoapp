package com.example.tat.videoapplication.injection.component;

import com.example.tat.videoapplication.injection.ConfigPersistent;
import com.example.tat.videoapplication.injection.module.ActivityModule;

import dagger.Component;

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}
