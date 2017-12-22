package com.chanta.androidlaba3;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbCategory;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbPhoto;
import com.chanta.androidlaba3.entity.Category;
import com.chanta.androidlaba3.viewUtils.CategoryAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerCategory;
    private DbCategory dbCategory;
    private DbHelper dbHelper;
    private List<Category> categories;
    private DbPhoto dbPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        dbCategory = new DbCategory(this);
        dbCategory.openDB();
        categories = dbCategory.getAllCategories();

        recyclerCategory = (RecyclerView) findViewById(R.id.categoryRecycleView);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this));
        recyclerCategory.setAdapter(new CategoryAdapter(this, categories));

        AssetManager mngr = getAssets();
        try {
            InputStream inputStream = mngr.open("ball.png");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte[] byteArray = stream.toByteArray();
            dbPhoto = new DbPhoto(this);
            dbPhoto.openDB();
            dbPhoto.insertPhoto("Фото 1", byteArray);
            inputStream = mngr.open("tree.png");
            bitmap = BitmapFactory.decodeStream(inputStream);
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteArray = stream.toByteArray();
            dbPhoto.insertPhoto("Фото 2", byteArray);
            inputStream = mngr.open("bird.png");
            bitmap = BitmapFactory.decodeStream(inputStream);
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteArray = stream.toByteArray();
            dbPhoto.insertPhoto("Фото 3", byteArray);
            inputStream = mngr.open("cat.png");
            bitmap = BitmapFactory.decodeStream(inputStream);
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteArray = stream.toByteArray();
            dbPhoto.insertPhoto("Фото 4", byteArray);
            inputStream = mngr.open("smile.png");
            bitmap = BitmapFactory.decodeStream(inputStream);
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byteArray = stream.toByteArray();
            dbPhoto.insertPhoto("Фото 5", byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.standart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.log_out) {
            finish();
            return true;
        } else if (id == R.id.action_statistic) {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
