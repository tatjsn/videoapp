package com.example.tat.videoapplication;

import com.example.tat.videoapplication.data.DataManager;
import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.ui.main.MainMvpView;
import com.example.tat.videoapplication.ui.main.MainPresenter;
import com.example.tat.videoapplication.util.RxSchedulersOverrideRule;
import com.example.tat.videoapplication.util.TestDataFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    @Mock
    MainMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
        when(mMockDataManager.syncVideos())
                .thenReturn(Observable.empty());
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadVideosReturnsVideos() {
        List<Video> videos = Arrays.asList(
                TestDataFactory.makeVideo("Title")
        );

        when(mMockDataManager.getVideos())
                .thenReturn(Observable.just(videos));

        mMainPresenter.loadVideos();
        verify(mMockMainMvpView).showVideos(videos);
        verify(mMockMainMvpView, never()).showVideosEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadVideosReturnsEmptyList() {
        when(mMockDataManager.getVideos())
                .thenReturn(Observable.just(Collections.emptyList()));

        mMainPresenter.loadVideos();
        verify(mMockMainMvpView).showVideosEmpty();
        verify(mMockMainMvpView, never()).showVideos(ArgumentMatchers.anyList());
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadVideosFails() {
        when(mMockDataManager.getVideos())
                .thenReturn(Observable.error(new RuntimeException()));

        mMainPresenter.loadVideos();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showVideosEmpty();
        verify(mMockMainMvpView, never()).showVideos(ArgumentMatchers.anyList());
    }
}