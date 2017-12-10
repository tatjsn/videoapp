package com.example.tat.videoapplication.rule;

import android.content.Context;

import com.example.tat.videoapplication.VideoApplication;
import com.example.tat.videoapplication.data.DataManager;
import com.example.tat.videoapplication.injection.component.ApplicationTestComponent;
import com.example.tat.videoapplication.injection.component.DaggerApplicationTestComponent;
import com.example.tat.videoapplication.injection.module.ApplicationTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestComponentRule implements TestRule {

    private final ApplicationTestComponent mTestComponent;
    private final Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
        VideoApplication application = VideoApplication.get(context);
        mTestComponent = DaggerApplicationTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(application))
                .build();
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                VideoApplication application = VideoApplication.get(mContext);
                application.setComponent(mTestComponent);
                base.evaluate();
                application.setComponent(null);
            }
        };
    }
}
