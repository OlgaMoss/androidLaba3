package com.chanta.androidlaba3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbRecord;
import com.chanta.androidlaba3.entity.Record;

import java.util.Calendar;

public class EditNewRecordActivity extends AppCompatActivity {

    public static final String RECORD = "Record";
    public static final int DAY_TO_HOUR = 24;
    public static final int MOUTH_TO_HOUR = 730;
    public static final int YEAR_TO_HOUR = 8760;

    private int startYear, startMonth, startDay, startHours, startMinutes;
    private int endYear, endMonth, endDay, endHours, endMinutes;
    private TextView mStartDateTextView, mStartTimeTextView, mEndDateTextView, mEndTimeTextView;
    private EditText mDescriptionEditText;
    private int positionCategory;
    private Record record;
    private boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_record);

        if (getIntent().hasExtra(DbHelper.CATEGORY_ID)) {
            positionCategory = getIntent().getIntExtra(DbHelper.CATEGORY_ID, 0);
        }

        mDescriptionEditText = (EditText) findViewById(R.id.edit_description_record);
        mStartDateTextView = (TextView) findViewById(R.id.startDate);
        mStartTimeTextView = (TextView) findViewById(R.id.startTime);
        mEndDateTextView = (TextView) findViewById(R.id.endDate);
        mEndTimeTextView = (TextView) findViewById(R.id.endTime);

        Record editRecord = (Record) getIntent().getSerializableExtra(RECORD);
        if (editRecord != null) {
            isNew = false;
            record = editRecord;

            mDescriptionEditText.setText(editRecord.getDescriptionRecord());
            mStartDateTextView.setText(editRecord.getDateStart());
            mStartTimeTextView.setText(editRecord.getTimeStart());
            mEndDateTextView.setText(editRecord.getDateEnd());
            mEndTimeTextView.setText(editRecord.getTimeEnd());
            // todo images
        } else {
            final Calendar c = Calendar.getInstance();

            startYear = c.get(Calendar.YEAR);
            startMonth = c.get(Calendar.MONTH) + 1;
            startDay = c.get(Calendar.DAY_OF_MONTH);
            startHours = c.get(Calendar.HOUR_OF_DAY);
            startMinutes = c.get(Calendar.MINUTE);

            endYear = c.get(Calendar.YEAR);
            endMonth = c.get(Calendar.MONTH) + 1;
            endDay = c.get(Calendar.DAY_OF_MONTH);
            endHours = c.get(Calendar.HOUR_OF_DAY);
            endMinutes = c.get(Calendar.MINUTE);

            setDateToTextView(mStartDateTextView, String.valueOf(startDay), String.valueOf(startMonth), String.valueOf(startYear));
            setTimeToTextView(mStartTimeTextView, String.valueOf(startHours), String.valueOf(startMinutes));
            setDateToTextView(mEndDateTextView, String.valueOf(endDay), String.valueOf(endMonth), String.valueOf(endYear));
            setTimeToTextView(mEndTimeTextView, String.valueOf(endHours), String.valueOf(endMinutes));
        }

        findViewById(R.id.accepted_floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo пересчитать roundedTime
                DbRecord dbRecord = new DbRecord(getApplicationContext());
                String roundedTime = getRoundedTime(
                        mStartDateTextView.getText().toString(),
                        mStartTimeTextView.getText().toString(),
                        mEndDateTextView.getText().toString(),
                        mEndTimeTextView.getText().toString());

                if (isNew) {
                    dbRecord.insertRecord(mDescriptionEditText.getText().toString(),
                            mStartDateTextView.getText().toString(),
                            mStartTimeTextView.getText().toString(),
                            mEndDateTextView.getText().toString(),
                            mEndTimeTextView.getText().toString(),
                            roundedTime,
                            null, //todo встатвить для изображений
                            positionCategory);
                    final Intent intent = new Intent(v.getContext(), RecordsActivity.class);
                    intent.putExtra(DbHelper.CATEGORY_ID, positionCategory);
                    startActivity(intent);

                } else {
                    dbRecord.updateRecord(record.getId(),
                            mDescriptionEditText.getText().toString(),
                            mStartDateTextView.getText().toString(),
                            mStartTimeTextView.getText().toString(),
                            mEndDateTextView.getText().toString(),
                            mEndTimeTextView.getText().toString(),
                            roundedTime,
                            null, //todo встатвить для изображений
                            positionCategory);
                    final Intent intent = new Intent(v.getContext(), RecordsActivity.class);
                    intent.putExtra(DbHelper.CATEGORY_ID, positionCategory - 1);
                    startActivity(intent);
                }


            }
        });
    }

    private String getRoundedTime(String startDate, String startTime, String endDate, String endTime) {
        int startYear = Integer.parseInt(startDate.split("/")[0]);
        int startMonth = Integer.parseInt(startDate.split("/")[1]);
        int startDay = Integer.parseInt(startDate.split("/")[2]);
        int startHours = Integer.parseInt(startTime.split(":")[0]);
        int startMinutes = Integer.parseInt(startTime.split(":")[1]);

        int endYear = Integer.parseInt(endDate.split("/")[0]);
        int endMonth = Integer.parseInt(endDate.split("/")[1]);
        int endDay = Integer.parseInt(endDate.split("/")[2]);
        int endHours = Integer.parseInt(endTime.split(":")[0]);
        int endMinutes = Integer.parseInt(endTime.split(":")[1]);

        String minute = String.valueOf(endMinutes - startMinutes);

        String hour = String.valueOf((endHours - startHours) +
                DAY_TO_HOUR * (endDay - startDay) +
                MOUTH_TO_HOUR * (endMonth - startMonth) +
                YEAR_TO_HOUR * (endYear - startYear));

        return hour + ":" + minute;
    }

    private void setDateToTextView(final TextView textView, final String day, final String month, final String year) {
        textView.setText(String.format(
                "%s%s%s%s%s",
                day,
                "/",
                month,
                "/",
                year));
    }

    private void setTimeToTextView(final TextView textView, final String hours, final String minutes) {
        textView.setText(String.format(
                "%s%s%s",
                hours,
                ":",
                minutes));
    }

    public void onChooseStartDate(View view) {
        String startDate = ((TextView) view).getText().toString();
        String[] parts = startDate.split("/");
        startYear = Integer.parseInt(parts[0]);
        startMonth = Integer.parseInt(parts[1]);
        startDay = Integer.parseInt(parts[2]);
        openDateDialog(startDay, startMonth, startYear, mStartDateTextView);
    }

    public void onChooseEndDate(View view) {
        String endDate = ((TextView) view).getText().toString();
        String[] parts = endDate.split("/");
        endYear = Integer.parseInt(parts[0]);
        endMonth = Integer.parseInt(parts[1]);
        endDay = Integer.parseInt(parts[2]);
        openDateDialog(endDay, endMonth, endYear, mEndDateTextView);
    }

    public void openDateDialog(final int d, final int m, final int y,
                               final TextView dateTextView) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    dateTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void onChooseStartTime(View view) {
        String startTime = ((TextView) view).getText().toString();
        String[] parts = startTime.split(":");
        startHours = Integer.parseInt(parts[0]);
        startMinutes = Integer.parseInt(parts[1]);
        openTimeDialog(startHours, startMinutes, mStartTimeTextView);
    }

    public void onChooseEndTime(View view) {
        String endTime = ((TextView) view).getText().toString();
        String[] parts = endTime.split(":");
        endHours = Integer.parseInt(parts[0]);
        endMinutes = Integer.parseInt(parts[1]);
        openTimeDialog(endHours, endMinutes, mEndTimeTextView);
    }

    public void openTimeDialog(final int h, final int m, final TextView timeTextView) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> timeTextView.setText(hourOfDay + ":" + minute), h, m, false);
        timePickerDialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
