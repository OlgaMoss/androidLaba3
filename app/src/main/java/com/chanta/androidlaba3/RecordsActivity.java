package com.chanta.androidlaba3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbRecord;
import com.chanta.androidlaba3.entity.Record;
import com.chanta.androidlaba3.viewUtils.RecordAdapter;

import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    private RecyclerView recyclerRecord;
    private DbRecord dbRecord;
    private List<Record> records;

    private int positionCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        if (getIntent().hasExtra(DbHelper.CATEGORY_ID)) {
            positionCategory = getIntent().getIntExtra(DbHelper.CATEGORY_ID, 0);
        }

        dbRecord = new DbRecord(this);
        dbRecord.openDB();
        records = dbRecord.getAllRecords(positionCategory);

        recyclerRecord = (RecyclerView) findViewById(R.id.recordRecycleView);
        recyclerRecord.setLayoutManager(new LinearLayoutManager(this));
        recyclerRecord.setAdapter(new RecordAdapter(this, records));

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), EditNewRecordActivity.class);
                intent.putExtra(DbHelper.CATEGORY_ID, positionCategory);
                startActivity(intent);
            }
        });
    }
}
