package com.example.tat.videoapplication.ui.base;

import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;

import com.example.tat.videoapplication.VideoApplication;
import com.example.tat.videoapplication.injection.component.ActivityComponent;
import com.example.tat.videoapplication.injection.component.ConfigPersistentComponent;
import com.example.tat.videoapplication.injection.component.DaggerConfigPersistentComponent;
import com.example.tat.videoapplication.injection.module.ActivityModule;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> sComponentsArray =
            new LongSparseArray<>();

    private long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null
                ? savedInstanceState.getLong(KEY_ACTIVITY_ID)
                : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (sComponentsArray.get(mActivityId) == null) {
            configPersistentComponent =
                    DaggerConfigPersistentComponent.builder()
                            .applicationComponent(VideoApplication.get(this).getComponent())
                            .build();
            sComponentsArray.put(mActivityId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsArray.get(mActivityId);
        }
        ActivityComponent activityComponent =
                configPersistentComponent.activityComponent(new ActivityModule(this));
        inject(activityComponent);
        attachView();
    }

    protected abstract int getLayout();

    protected abstract void inject(ActivityComponent activityComponent);

    protected abstract void attachView();

    protected abstract void detachPresenter();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            sComponentsArray.remove(mActivityId);
        }
        detachPresenter();
        super.onDestroy();
    }
}
