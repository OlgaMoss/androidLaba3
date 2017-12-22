package com.chanta.androidlaba3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.chanta.androidlaba3.entity.Record;

public class DetailRecordActivity extends AppCompatActivity {
    public static final String RECORD = "Record";
    private Record record;
    private TextView mStartDateTextView, mStartTimeTextView, mEndDateTextView, mEndTimeTextView, mRoundTimeTextView;
    private TextView mDescriptionTextText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_record);

        record = (Record) getIntent().getSerializableExtra(RECORD);
        mDescriptionTextText = (TextView) findViewById(R.id.detail_description_record);
        mStartDateTextView = (TextView) findViewById(R.id.detail_startDate);
        mStartTimeTextView = (TextView) findViewById(R.id.detail_startTime);
        mEndDateTextView = (TextView) findViewById(R.id.detail_endDate);
        mEndTimeTextView = (TextView) findViewById(R.id.detail_endTime);
        mRoundTimeTextView = (TextView) findViewById(R.id.rounded_time);

        mDescriptionTextText.setText(record.getDescriptionRecord());
        mStartDateTextView.setText(record.getDateStart());
        mStartTimeTextView.setText(record.getTimeStart());
        mEndDateTextView.setText(record.getDateEnd());
        mEndTimeTextView.setText(record.getTimeEnd());
        mRoundTimeTextView.setText(record.getRoundedTime());

        //todo фото RecycleView

        findViewById(R.id.edited_floatingActionButton).setOnClickListener(v -> {
            final Intent intent = new Intent(v.getContext(), EditNewRecordActivity.class);
            intent.putExtra(DbHelper.CATEGORY_ID, record.getCategoryId());
            intent.putExtra(RECORD, record);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
