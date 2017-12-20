package com.chanta.androidlaba3.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chanta.androidlaba3.entity.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanta on 20.12.17.
 */

public class DbPhoto {

    private static final String TAG = "DbPhoto";
    private DbHelper dbHelper;
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private List<Photo> mPhotoList;

    public DbPhoto(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    //todo проверить???
    public void updatePhoto(int id, String newTitle, String newPath) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.TITLE, newTitle);
        cv.put(DbHelper.PATH, newPath);
        String[] args = new String[]{String.valueOf(id)};
        db.update(DbHelper.TABLE_PHOTO, cv, "_id = ?", args);
    }

    public void insertPhoto(String newTitle, String newPath) {
        String insertQuery = "insert into " + DbHelper.TABLE_PHOTO + " (" + DbHelper.TITLE + DbHelper.PATH + ")" +
                " values ('" + newTitle + "', '" + newPath + "')";
        dbHelper.getWritableDatabase().execSQL(insertQuery);
    }

    public void deleteItem(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(DbHelper.TABLE_PHOTO, DbHelper.KEY_ID + "=" + id, null);
    }

    public List<Photo> getPhoto() {
        cursor = db.query(DbHelper.TABLE_PHOTO, null, null, null, null, null, null);
        mPhotoList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            int idColInd = cursor.getColumnIndex(DbHelper.KEY_ID);
            int titleColInd = cursor.getColumnIndex(DbHelper.TITLE);
            int pathColInd = cursor.getColumnIndex(DbHelper.PATH);

            do {
                Photo photo = new Photo(cursor.getInt(idColInd),
                        cursor.getString(titleColInd), cursor.getString(pathColInd));
                mPhotoList.add(photo);
            } while (cursor.moveToNext());

        } else {
            Log.d(TAG, "В базе нет данных!");
        }

        cursor.close();

        return mPhotoList;

    }

    public void close() {
        dbHelper.close();
        db.close();
    }

}
