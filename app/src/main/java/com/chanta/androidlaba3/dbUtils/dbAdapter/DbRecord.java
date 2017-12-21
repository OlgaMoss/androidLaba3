package com.chanta.androidlaba3.dbUtils.dbAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.chanta.androidlaba3.entity.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanta on 20.12.17.
 */

public class DbRecord {

    private static final String TAG = "DbRecord";
    private DbHelper dbHelper;
    private Context context;
    private Cursor cursor;
    private SQLiteDatabase db;
    private List<Record> mPhotoList;


    public DbRecord(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public DbRecord openDB() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void updateRecord(int id, String newDescriptionRecord, String newDateStart, String newTimeStart,
                             String newDateEnd, String newTimeEnd, String newRoundedTime, String newPhotoIdList,
                             int newCategoryId) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.DESCRIPTION_RECORD, newDescriptionRecord);
        cv.put(DbHelper.DATE_START, newDateStart);
        cv.put(DbHelper.TIME_START, newTimeStart);
        cv.put(DbHelper.DATE_END, newDateEnd);
        cv.put(DbHelper.TIME_END, newTimeEnd);
        cv.put(DbHelper.ROUNDED_TIME, newRoundedTime);
        cv.put(DbHelper.PHOTO_ID_LIST, newPhotoIdList);
        cv.put(DbHelper.CATEGORY_ID, newCategoryId);
        String[] args = new String[]{String.valueOf(id)};
        db.update(DbHelper.TABLE_RECORD, cv, DbHelper.KEY_ID + " = ?", args);
    }

    public void insertRecord(String descriptionRecord, String dateStart, String timeStart, String dateEnd,
                             String timeEnd, String roundedTime, String photoIdList, int categoryId) {
        String insertQuery = "insert into " + DbHelper.TABLE_RECORD
                + "(" + DbHelper.DESCRIPTION_RECORD + ","
                + DbHelper.DATE_START + "," + DbHelper.TIME_START + ","
                + DbHelper.DATE_END + "," + DbHelper.TIME_END + ","
                + DbHelper.ROUNDED_TIME + "," + DbHelper.PHOTO_ID_LIST + "," + DbHelper.CATEGORY_ID + ")"
                + " values ('"
                + descriptionRecord + "', '"
                + dateStart + "', '" + timeStart + "', '"
                + dateEnd + "', '" + timeEnd + "', '"
                + roundedTime + "', '" + photoIdList + "', '" + (categoryId + 1) + "')";
        dbHelper.getWritableDatabase().execSQL(insertQuery);
    }

    public void deleteItem(int id) {
        db = dbHelper.getWritableDatabase();
        String[] args = new String[]{String.valueOf(id)};
        db.delete(DbHelper.TABLE_RECORD, DbHelper.KEY_ID + "= ?", args);
    }

    public List<Record> getAllRecords(int categoryId) {
        String[] columns = {DbHelper.KEY_ID, DbHelper.DESCRIPTION_RECORD,
                DbHelper.DATE_START, DbHelper.TIME_START,
                DbHelper.DATE_END, DbHelper.TIME_END,
                DbHelper.ROUNDED_TIME, DbHelper.PHOTO_ID_LIST, DbHelper.CATEGORY_ID};
        cursor = db.query(DbHelper.TABLE_RECORD, columns, DbHelper.CATEGORY_ID + "= ?",
                new String[]{String.valueOf(categoryId + 1)}, null, null, null);
        mPhotoList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            int idColInd = cursor.getColumnIndex(DbHelper.KEY_ID);
            int descriptionRecordColInd = cursor.getColumnIndex(DbHelper.DESCRIPTION_RECORD);
            int dateStartColInd = cursor.getColumnIndex(DbHelper.DATE_START);
            int timeStartColInd = cursor.getColumnIndex(DbHelper.TIME_START);
            int dateEndColInd = cursor.getColumnIndex(DbHelper.DATE_END);
            int timeEndColInd = cursor.getColumnIndex(DbHelper.TIME_END);
            int roundedTimeColInd = cursor.getColumnIndex(DbHelper.ROUNDED_TIME);
            int photoIdListColInd = cursor.getColumnIndex(DbHelper.PHOTO_ID_LIST);
            int categoryIdColInd = cursor.getColumnIndex(DbHelper.CATEGORY_ID);

            do {
                Record record = new Record(cursor.getInt(idColInd), cursor.getString(descriptionRecordColInd),
                        cursor.getString(dateStartColInd), cursor.getString(timeStartColInd),
                        cursor.getString(dateEndColInd), cursor.getString(timeEndColInd),
                        cursor.getString(roundedTimeColInd),
                        cursor.getString(photoIdListColInd), cursor.getInt(categoryIdColInd));
                mPhotoList.add(record);
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
