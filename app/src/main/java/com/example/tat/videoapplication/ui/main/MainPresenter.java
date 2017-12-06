package com.example.tat.videoapplication.ui.main;

import android.util.Log;

import com.example.tat.videoapplication.data.DataManager;
import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.injection.ConfigPersistent;
import com.example.tat.videoapplication.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {
    private DataManager mDataManager;
    private Disposable mDisposable;

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
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public void loadVideos() {
        checkViewAttached();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDataManager.syncVideos()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Video>() { // TODO Empty observer
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("tatdbg", "onsubscribe");
                    }

                    @Override
                    public void onNext(Video video) {
                        Log.d("tatdbg", "onnext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tatdbg", "onerror", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("tatdbg", "oncomplete");
                    }
                });
        mDataManager.getVideos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Video>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<Video> videos) {
                        if (videos.isEmpty()) {
                            getView().showVideosEmpty();
                        } else {
                            getView().showVideos(videos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onComplete() {
                        // Do nothing
                    }
                });
    }
}
