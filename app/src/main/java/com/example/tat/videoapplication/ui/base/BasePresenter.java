package com.example.tat.videoapplication.ui.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    protected boolean isViewAttached() {
        return mMvpView != null;
    }

    protected T getView() {
        return mMvpView;
    }

    protected void checkViewAttached() {
        if (!isViewAttached()) throw new RuntimeException("MvpView is not attached");
    }

    public void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }
}