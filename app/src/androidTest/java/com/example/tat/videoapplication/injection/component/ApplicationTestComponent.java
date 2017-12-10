package com.example.tat.videoapplication.injection.component;

import com.example.tat.videoapplication.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface ApplicationTestComponent extends ApplicationComponent {

}
