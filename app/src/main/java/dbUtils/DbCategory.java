package dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    // private String description;
    //private int photoId;

    public DbCategory(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    //апгрейд по имени категории //todo подумать  "name = ?" мб поменять на id
    public void updateCategory(String name, String newDescription, String newPhotoId) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.DESCRIPTION_CATEGORY, newDescription);
        cv.put(DbHelper.PHOTO_ID, newPhotoId);
        String[] args = new String[]{name};
        db.update(DbHelper.TABLE_CATEGORY, cv, "name = ?", args);
    }

    // метод для удаления строки по id
    public void deleteItem(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(DbHelper.TABLE_CATEGORY, DbHelper.KEY_ID + "=" + id, null);
    }

    // метод возвращающий коллекцию всех данных
    public List<Category> getCategory() {
        cursor = db.query(DbHelper.TABLE_CATEGORY, null, null, null, null, null, null);
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

    // здесь закрываем все соединения с базой и класс-помощник
    public void close() {
        dbHelper.close();
        db.close();
    }


}
