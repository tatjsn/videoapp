package com.example.tat.videoapplication.ui.main;

import android.util.Log;

import com.example.tat.videoapplication.data.DataManager;
import com.example.tat.videoapplication.injection.ConfigPersistent;
import com.example.tat.videoapplication.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {
    private static String TAG = MainPresenter.class.getSimpleName();

    private DataManager mDataManager;
    private Disposable mGetDisposable;
    private Disposable mSyncDisposable;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSyncDisposable != null) {
            mSyncDisposable.dispose();
        }
        if (mGetDisposable != null) {
            mGetDisposable.dispose();
        }
    }

    public void loadVideos() {
        checkViewAttached();
        if (mSyncDisposable != null && !mSyncDisposable.isDisposed()) {
            mSyncDisposable.dispose();
        }
        if (mGetDisposable != null && !mGetDisposable.isDisposed()) {
            mGetDisposable.dispose();
        }
        mSyncDisposable = mDataManager.syncVideos()
                .subscribeOn(Schedulers.io())
                .subscribe(video -> Log.d(TAG, String.format("video synced %s", video.title())),
                        exception -> Log.e(TAG, exception.getMessage()));
        mGetDisposable = mDataManager.getVideos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(videos -> {
                    if (videos.isEmpty()) {
                        getView().showVideosEmpty();
                    } else {
                        getView().showVideos(videos);
                    }
                }, exception -> getView().showError());
    }
}
