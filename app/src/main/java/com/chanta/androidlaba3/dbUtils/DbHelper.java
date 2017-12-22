package com.chanta.androidlaba3.dbUtils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by chanta on 19.12.17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    public static final String KEY_ID = "_id";

    public static final String TABLE_CATEGORY = "category";
    public static final String NAME = "name";
    public static final String DESCRIPTION_CATEGORY = "description_category";
    public static final String PHOTO_ID = "photo_id";

    public static final String TABLE_RECORD = "record";

    public static final String DESCRIPTION_RECORD = "description_record";
    public static final String DATE_START = "date_start";
    public static final String TIME_START = "time_start";
    public static final String DATE_END = "date_end";
    public static final String TIME_END = "time_end";
    public static final String ROUNDED_TIME = "rounded_time";
    public static final String PHOTO_ID_LIST = "photo_id_list";
    public static final String CATEGORY_ID = "category_id";

    public static final String TABLE_PHOTO = "photo";

    public static final String TITLE = "title";
    public static final String IMAGE = "image";

    private static final String DATABASE_NAME = "DB";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL("create table " + TABLE_CATEGORY + " (" +
                    KEY_ID + " integer primary key autoincrement," +
                    NAME + " text not null," +
                    DESCRIPTION_CATEGORY + " text," +
                    PHOTO_ID + " integer," +
                    "FOREIGN KEY (" + PHOTO_ID + ") REFERENCES " + TABLE_PHOTO + " (" + KEY_ID + "));");

            sqLiteDatabase.execSQL("create table " + TABLE_RECORD + " (" +
                    KEY_ID + " integer primary key autoincrement," +
                    DESCRIPTION_RECORD + " text not null," +
                    DATE_START + " text not null," +
                    TIME_START + " text not null," +
                    DATE_END + " text not null," +
                    TIME_END + " text not null," +
                    ROUNDED_TIME + " text not null," +
                    PHOTO_ID_LIST + " integer," +
                    CATEGORY_ID + " integer not null," +
                    "FOREIGN KEY (" + PHOTO_ID_LIST + ") REFERENCES " + TABLE_PHOTO + " (" + KEY_ID + ")," +
                    "FOREIGN KEY (" + CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORY + " (" + KEY_ID + "));");

            sqLiteDatabase.execSQL("create table " + TABLE_PHOTO + " (" +
                    KEY_ID + " integer primary key autoincrement," +
                    TITLE + " text not null," +
                    IMAGE + " blob " +
                    ");");

            insertDefaultTableCategory(sqLiteDatabase);
            insertDefaultTableRecord(sqLiteDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertDefaultTableCategory(SQLiteDatabase sqLiteDatabase) {

        String insertQuery = "insert into " + TABLE_CATEGORY + " (" + NAME + ") values ('Работа')";
        sqLiteDatabase.execSQL(insertQuery);
        insertQuery = "insert into " + TABLE_CATEGORY + " (" + NAME + ") values ('Обед')";
        sqLiteDatabase.execSQL(insertQuery);
        insertQuery = "insert into " + TABLE_CATEGORY + " (" + NAME + ") values ('Отдых')";
        sqLiteDatabase.execSQL(insertQuery);
        insertQuery = "insert into " + TABLE_CATEGORY + " (" + NAME + ") values ('Уборка')";
        sqLiteDatabase.execSQL(insertQuery);
        insertQuery = "insert into " + TABLE_CATEGORY + " (" + NAME + ") values ('Сон')";
        sqLiteDatabase.execSQL(insertQuery);

    }

    private void insertDefaultTableRecord(SQLiteDatabase sqLiteDatabase) {
        String insertQuery = "insert into " + TABLE_RECORD
                + "(" + DESCRIPTION_RECORD + ","
                + DATE_START + "," + TIME_START + ","
                + DATE_END + "," + TIME_END + ","
                + ROUNDED_TIME + "," + PHOTO_ID_LIST + "," + CATEGORY_ID + ")"
                + " values ('Посмотреть телевизор', " +
                "'12/12/13', '15:50', " +
                "'12/12/13', '15:10', " +
                "'00:20', ' ', '3')";
        sqLiteDatabase.execSQL(insertQuery);

        insertQuery = "insert into " + TABLE_RECORD
                + "(" + DESCRIPTION_RECORD + ","
                + DATE_START + "," + TIME_START + ","
                + DATE_END + "," + TIME_END + ","
                + ROUNDED_TIME + "," + PHOTO_ID_LIST + "," + CATEGORY_ID + ")"
                + " values ('Поирать', " +
                "'12/12/13', '21:00', " +
                "'12/12/13', '21:30'," +
                "'00:30', ' ', '3')";
        sqLiteDatabase.execSQL(insertQuery);

        insertQuery = "insert into " + TABLE_RECORD
                + "(" + DESCRIPTION_RECORD + ","
                + DATE_START + "," + TIME_START + ","
                + DATE_END + "," + TIME_END + ","
                + ROUNDED_TIME + "," + PHOTO_ID_LIST + "," + CATEGORY_ID + ")"
                + " values ('Посмотреть на стену'," +
                "'12/12/13', '18:00'," +
                "'12/12/13', '18:10', " +
                "'00:10', ' ', '3')";
        sqLiteDatabase.execSQL(insertQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        this.onCreate(sqLiteDatabase);
    }
}
