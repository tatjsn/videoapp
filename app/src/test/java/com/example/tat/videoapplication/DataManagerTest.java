package com.example.tat.videoapplication;

import com.example.tat.videoapplication.data.DataManager;
import com.example.tat.videoapplication.data.local.DatabaseHelper;
import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.data.remote.VideoService;
import com.example.tat.videoapplication.util.TestDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {
    @Mock
    DatabaseHelper mMockDatabaseHelper;
    @Mock
    VideoService mMockVideoService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockVideoService, mMockDatabaseHelper);
    }

    @Test
    public void syncVideos_shouldEmitsValue() {
        List<Video> videos = Arrays.asList(
                TestDataFactory.makeVideo("Title1"),
                TestDataFactory.makeVideo("Title2")
        );
        when(mMockVideoService.getVideos())
                .thenReturn(Observable.just(videos));
        when(mMockDatabaseHelper.setVideos(videos))
                .thenReturn(Observable.fromIterable(videos));

        TestObserver<Video> result = new TestObserver<>();
        mDataManager.syncVideos().subscribe(result);
        result.assertNoErrors();
        result.assertValueSequence(videos);
    }

    @Test
    public void syncVideos_shouldCallsService() {
        List<Video> videos = Arrays.asList(
                TestDataFactory.makeVideo("Title1"),
                TestDataFactory.makeVideo("Title2")
        );
        when(mMockVideoService.getVideos())
                .thenReturn(Observable.just(videos));
        when(mMockDatabaseHelper.setVideos(videos))
                .thenReturn(Observable.fromIterable(videos));

        mDataManager.syncVideos().subscribe();
        verify(mMockVideoService).getVideos();
        verify(mMockDatabaseHelper).setVideos(videos);
    }

    @Test
    public void syncVideos_shouldNotCallsDatabase_whenServiceFails() {
        when(mMockVideoService.getVideos())
                .thenReturn(Observable.error(new RuntimeException()));

        mDataManager.syncVideos().subscribe(new TestObserver<>());
        verify(mMockVideoService).getVideos();
        verify(mMockDatabaseHelper, never()).setVideos(ArgumentMatchers.anyList());
    }

    @Test
    public void getVideos_shouldEmitsVideos() {
        List<Video> videos = Arrays.asList(
                TestDataFactory.makeVideo("Title1"),
                TestDataFactory.makeVideo("Title2")
        );
        when(mMockDatabaseHelper.getVideos())
                .thenReturn(Observable.just(videos));

        TestObserver<List<Video>> result = new TestObserver<>();
        mDataManager.getVideos().subscribe(result);
        result.assertNoErrors();
        result.assertValue(videos);
    }
}