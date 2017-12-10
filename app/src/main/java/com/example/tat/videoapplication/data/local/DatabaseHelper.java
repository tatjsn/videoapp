package com.example.tat.videoapplication.data.local;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;

import com.example.tat.videoapplication.data.model.Video;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DatabaseHelper {
    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        this(dbOpenHelper, Schedulers.io());
    }

    @VisibleForTesting
    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, scheduler);
    }

    @VisibleForTesting
    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<Video> setVideos(Collection<Video> videos) {
        return Observable.create(emitter -> {
            if (emitter.isDisposed()) {
                return;
            }
            BriteDatabase.Transaction transaction = mDb.newTransaction();
            try {
                mDb.delete(Db.VideosTable.TABLE_NAME, null);
                for (Video video : videos) {
                    long result = mDb.insert(Db.VideosTable.TABLE_NAME,
                            Db.VideosTable.toContentValues(video),
                            SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) emitter.onNext(video);
                }
                transaction.markSuccessful();
                emitter.onComplete();
            } finally {
                transaction.end();
            }
        });
    }

    public Observable<List<Video>> getVideos() {
        return mDb.createQuery(Db.VideosTable.TABLE_NAME,
                "SELECT * FROM " + Db.VideosTable.TABLE_NAME)
                .mapToList(Db.VideosTable::parseCursor);
    }
}
