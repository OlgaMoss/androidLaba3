package com.chanta.androidlaba3.dbUtils.dbAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

//    private String descriptionRecord;
//    private String dateStart;
//    private String dateEnd;
//    private String roundedTime;
//    private String photoIdList;
//    private int categoryId;

    public DbRecord(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    //todo проверить???
    //todo может id хранить в String
    public void updateRecord(int id, String newDescriptionRecord, String newDateStart, String newDateEnd,
                             String newRoundedTime, String newPhotoIdList, int newCategoryId) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.DESCRIPTION_CATEGORY, newDescriptionRecord);
        cv.put(DbHelper.DATE_START, newDateStart);
        cv.put(DbHelper.DATE_END, newDateEnd);
        cv.put(DbHelper.ROUNDED_TIME, newRoundedTime);
        cv.put(DbHelper.PHOTO_ID_LIST, newPhotoIdList);
        cv.put(DbHelper.CATEGORY_ID, newCategoryId);
        String[] args = new String[]{String.valueOf(id)};
        db.update(DbHelper.TABLE_RECORD, cv, "_id = ?", args);
    }

    //todo проверку на категорииID ??
    public void insertPhoto(String descriptionRecord, String dateStart, String dateEnd, String roundedTime,
                            String photoIdList, int categoryId) {
        String insertQuery = "insert into " + DbHelper.TABLE_RECORD
                + "(" + DbHelper.DESCRIPTION_RECORD + "," + DbHelper.DATE_START + "," + DbHelper.DATE_END
                + "," + DbHelper.ROUNDED_TIME + "," + DbHelper.PHOTO_ID_LIST + "," + DbHelper.CATEGORY_ID + ")"
                + " values ('" + descriptionRecord + "', '" + dateStart + "', '" + dateEnd + "', '"
                + roundedTime + "', '" + photoIdList + "', '" + categoryId + "')";
        dbHelper.getWritableDatabase().execSQL(insertQuery);
    }

    public void deleteItem(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(DbHelper.TABLE_RECORD, DbHelper.KEY_ID + "=" + id, null);
    }

    public List<Record> getCategory() {
        cursor = db.query(DbHelper.TABLE_RECORD, null, null, null, null, null, null);
        mPhotoList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            int idColInd = cursor.getColumnIndex(DbHelper.KEY_ID);
            int descriptionRecordColInd = cursor.getColumnIndex(DbHelper.DESCRIPTION_RECORD);
            int dateStartColInd = cursor.getColumnIndex(DbHelper.DATE_START);
            int dateEndColInd = cursor.getColumnIndex(DbHelper.DATE_END);
            int roundedTimeColInd = cursor.getColumnIndex(DbHelper.ROUNDED_TIME);
            int photoIdListColInd = cursor.getColumnIndex(DbHelper.PHOTO_ID);
            int categoryIdColInd = cursor.getColumnIndex(DbHelper.CATEGORY_ID);

            do {
                Record record = new Record(cursor.getInt(idColInd), cursor.getString(descriptionRecordColInd),
                        cursor.getString(dateStartColInd), cursor.getString(dateEndColInd), cursor.getString(roundedTimeColInd),
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
