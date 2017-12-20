package com.chanta.androidlaba3.dbUtils.dbAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.chanta.androidlaba3.entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanta on 19.12.17.
 */

public class DbCategory {

    private static final String TAG = "DbCategory";
    private DbHelper dbHelper;
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private List<Category> mCategoryList;

    public DbCategory(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public DbCategory openDB() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void updateCategory(int id, String name, String newDescription, String newPhotoId) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.NAME, name);
        cv.put(DbHelper.DESCRIPTION_CATEGORY, newDescription);
        cv.put(DbHelper.PHOTO_ID, newPhotoId);
        db.update(DbHelper.TABLE_CATEGORY, cv, DbHelper.KEY_ID + "= ?", new String[]{String.valueOf(id)});
    }

    // метод для удаления строки по id
    public void deleteItem(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(DbHelper.TABLE_CATEGORY, DbHelper.KEY_ID + "=" + id, null);
    }

    // метод возвращающий коллекцию всех данных
    public List<Category> getAllCategories() {
        String[] columns = {DbHelper.KEY_ID, DbHelper.NAME, DbHelper.DESCRIPTION_CATEGORY, DbHelper.PHOTO_ID};
        cursor = db.query(DbHelper.TABLE_CATEGORY, columns, null, null, null, null, null);
        mCategoryList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            int idColInd = cursor.getColumnIndex(DbHelper.KEY_ID);
            int nameColInd = cursor.getColumnIndex(DbHelper.NAME);
            int descriptionColInd = cursor.getColumnIndex(DbHelper.DESCRIPTION_CATEGORY);
            int photoIdColInd = cursor.getColumnIndex(DbHelper.PHOTO_ID);

            do {
                Category category = new Category(cursor.getInt(idColInd),
                        cursor.getString(nameColInd), cursor.getString(descriptionColInd), cursor.getInt(photoIdColInd));
                mCategoryList.add(category);
            } while (cursor.moveToNext());

        } else {
            Log.d(TAG, "В базе нет данных!");
        }

        cursor.close();

        return mCategoryList;

    }

    public void close() {
        dbHelper.close();
        db.close();
    }


}
