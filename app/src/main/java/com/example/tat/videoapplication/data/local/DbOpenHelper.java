package com.example.tat.videoapplication.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tat.videoapplication.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "videos.db";
    public static final int DATABASE_VERSION = 1;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(Db.VideosTable.CREATE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
