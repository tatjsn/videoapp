package com.example.tat.videoapplication;

import android.database.Cursor;

import com.example.tat.videoapplication.data.local.DatabaseHelper;
import com.example.tat.videoapplication.data.local.Db;
import com.example.tat.videoapplication.data.local.DbOpenHelper;
import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.util.RxSchedulersOverrideRule;
import com.example.tat.videoapplication.util.TestDataFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class DatabaseHelperTest {
    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setup() {
        if (mDatabaseHelper == null)
            mDatabaseHelper = new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application),
                    mOverrideSchedulersRule.getScheduler());
    }

    @Test
    public void setVideos_shouldSetVideosCorrectly() {
        List<Video> videos = Arrays.asList(
                TestDataFactory.makeVideo("Title1"),
                TestDataFactory.makeVideo("Title2")
        );
        TestObserver<Video> result = new TestObserver<>();
        mDatabaseHelper.setVideos(videos).subscribe(result);
        result.assertNoErrors();
        result.assertValueSequence(videos);

        Cursor cursor = mDatabaseHelper.getBriteDb()
                .query("SELECT * FROM " + Db.VideosTable.TABLE_NAME);
        assertEquals(2, cursor.getCount());
        for (Video video : videos) {
            cursor.moveToNext();
            assertEquals(video, Db.VideosTable.parseCursor(cursor));
        }
    }

    @Test
    public void getVideos_shouldGetVideosCorrectly() {
        List<Video> videos = Arrays.asList(
                TestDataFactory.makeVideo("Title1"),
                TestDataFactory.makeVideo("Title2")
        );
        mDatabaseHelper.setVideos(videos).subscribe();

        TestObserver<List<Video>> result = new TestObserver<>();
        mDatabaseHelper.getVideos().subscribe(result);
        result.assertNoErrors();
        result.assertValue(videos);
    }
}