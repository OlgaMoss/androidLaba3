package com.chanta.androidlaba3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanta.androidlaba3.dbUtils.dbAdapter.DbCategory;
import com.chanta.androidlaba3.entity.Category;
import com.chanta.androidlaba3.viewUtils.CategoryAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerCategory;
    private DbCategory dbCategory;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbCategory = new DbCategory(this);
        dbCategory.openDB();
        categories = dbCategory.getAllCategories();

        recyclerCategory = (RecyclerView) findViewById(R.id.categoryRecycleView);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this));
        recyclerCategory.setAdapter(new CategoryAdapter(this, categories));


    }
}
