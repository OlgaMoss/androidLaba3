package com.chanta.androidlaba3;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {
    private static final String TAG = "StatisticsActivity";
    private TextView mList1TextView, mList2TextView, mList3TextView;
    private PieChart pieChart;
    private ArrayList<Integer> yData;
    private ArrayList<String> xData;

    private int startYear, startMonth, startDay;
    private int endYear, endMonth, endDay;
    private TextView mStartDateTextView, mEndDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        mList1TextView = (TextView) findViewById(R.id.list_1_textView);
        mList2TextView = (TextView) findViewById(R.id.list_2_textView);
        mList3TextView = (TextView) findViewById(R.id.list_3_textView);
        pieChart = (PieChart) findViewById(R.id.idPieChart);
        yData = new ArrayList<>();
        xData = new ArrayList<>();
        mStartDateTextView = (TextView) findViewById(R.id.startDateAnyTime);
        mEndDateTextView = (TextView) findViewById(R.id.endDateAnyTime);

        final Calendar c = Calendar.getInstance();

        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH) + 1;
        startDay = c.get(Calendar.DAY_OF_MONTH);

        endYear = c.get(Calendar.YEAR);
        endMonth = c.get(Calendar.MONTH) + 1;
        endDay = c.get(Calendar.DAY_OF_MONTH);

        setDateToTextView(mStartDateTextView, String.valueOf(startDay), String.valueOf(startMonth), String.valueOf(startYear));
        setDateToTextView(mEndDateTextView, String.valueOf(endDay), String.valueOf(endMonth), String.valueOf(endYear));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mostFrequentCase();
        theLargestTotalTimeByCategory();
        timeChartInAllCategories();
    }

    //самые частые занятия
    public void mostFrequentCase() {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + DbHelper.DESCRIPTION_RECORD + ", COUNT(" + DbHelper.DESCRIPTION_RECORD + ")" +
                " FROM " + DbHelper.TABLE_RECORD +
                " group by " + DbHelper.DESCRIPTION_RECORD +
                " ORDER BY COUNT(" + DbHelper.DESCRIPTION_RECORD + ") DESC;";
        Cursor cursor = db.rawQuery(query, new String[]{});
        String listTop = "";
        int k = 1;
        if (cursor.moveToFirst()) {
            int descriptionRecordColInd = cursor.getColumnIndex(DbHelper.DESCRIPTION_RECORD);
            int cntDescriptionRecordColInd = cursor.getColumnIndex("COUNT(" + DbHelper.DESCRIPTION_RECORD + ")");
            do {
                listTop += k + ". " + cursor.getString(descriptionRecordColInd) + "  " + cursor.getString(cntDescriptionRecordColInd) + "\n";
                k++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        mList1TextView.setText(listTop);
    }

    //самое большое суммарное время по категориям
    public void theLargestTotalTimeByCategory() {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listTop = "";
        String query = "SELECT " + DbHelper.NAME + ", " +
                " strftime('%H:%M', sum(strftime('%s'," + DbHelper.ROUNDED_TIME + ")), 'unixepoch') time" +
                " FROM " + DbHelper.TABLE_RECORD +
                " INNER JOIN " + DbHelper.TABLE_CATEGORY + " ON " +
                DbHelper.TABLE_RECORD + "." + DbHelper.CATEGORY_ID + " = " + DbHelper.TABLE_CATEGORY + "." + DbHelper.KEY_ID +
                " group by " + DbHelper.NAME +
                " ORDER BY time DESC;";
        Cursor cursor = db.rawQuery(query, null);

        int k = 1;
        if (cursor.moveToFirst()) {
            int descriptionRecordColInd = cursor.getColumnIndex(DbHelper.NAME);
            int cntDescriptionRecordColInd = cursor.getColumnIndex("time");
            do {
                listTop += k + ". " + cursor.getString(descriptionRecordColInd) + "  " + cursor.getString(cntDescriptionRecordColInd) + "\n";
                k++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        mList2TextView.setText(listTop);

    }

    //суммарное время по выбранным категориям
    public void theTotalTimeForTheSelectedCategories(View view) {
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.radioButton1);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.radioButton2);
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.radioButton3);
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.radioButton4);
        CheckBox checkBox5 = (CheckBox) findViewById(R.id.radioButton5);
        String rb1 = (checkBox1.isChecked()) ? checkBox1.getText().toString() : "test";
        String rb2 = (checkBox2.isChecked()) ? checkBox2.getText().toString() : "test";
        String rb3 = (checkBox3.isChecked()) ? checkBox3.getText().toString() : "test";
        String rb4 = (checkBox4.isChecked()) ? checkBox4.getText().toString() : "test";
        String rb5 = (checkBox5.isChecked()) ? checkBox5.getText().toString() : "test";

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listTop = "";
        String query = "SELECT " + DbHelper.NAME + ", " +
                " strftime('%H:%M', sum(strftime('%s'," + DbHelper.ROUNDED_TIME + ")), 'unixepoch') time" +
                " FROM " + DbHelper.TABLE_RECORD +
                " INNER JOIN " + DbHelper.TABLE_CATEGORY + " ON " +
                DbHelper.TABLE_RECORD + "." + DbHelper.CATEGORY_ID + " = " + DbHelper.TABLE_CATEGORY + "." + DbHelper.KEY_ID +
                " where " + DbHelper.NAME + " in('" + rb1 + "','" + rb2 + "','" + rb3 + "','" + rb4 + "','" + rb5 + "');";
        //  " group by " + DbHelper.NAME +
        //  " ORDER BY time DESC;";
        Log.d(TAG, "theTotalTimeForTheSelectedCategories: " + query);
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            int descriptionRecordColInd = cursor.getColumnIndex(DbHelper.NAME);
            int cntDescriptionRecordColInd = cursor.getColumnIndex("time");
            do {
                listTop = cursor.getString(descriptionRecordColInd) + "  " + cursor.getString(cntDescriptionRecordColInd) + "\n";

            } while (cursor.moveToNext());
        }
        cursor.close();
        mList3TextView.setText(listTop);

    }

    // круговая диаграмма времени по всем категориям

    public void timeChartInAllCategories() {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listTop = "";
        String query = "SELECT " + DbHelper.NAME + ", " +
                " strftime('%H:%M', sum(strftime('%s'," + DbHelper.ROUNDED_TIME + ")), 'unixepoch') time" +
                " FROM " + DbHelper.TABLE_RECORD +
                " INNER JOIN " + DbHelper.TABLE_CATEGORY + " ON " +
                DbHelper.TABLE_RECORD + "." + DbHelper.CATEGORY_ID + " = " + DbHelper.TABLE_CATEGORY + "." + DbHelper.KEY_ID +
                " group by " + DbHelper.NAME +
                " ORDER BY time DESC;";
        Cursor cursor = db.rawQuery(query, null);

        int k = 1;
        if (cursor.moveToFirst()) {
            int descriptionRecordColInd = cursor.getColumnIndex(DbHelper.NAME);
            int cntDescriptionRecordColInd = cursor.getColumnIndex("time");
            do {
                yData.add(cursor.getInt(cntDescriptionRecordColInd));
                xData.add(cursor.getString(descriptionRecordColInd));
                k++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        Description description = new Description();
        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        description.setText("Chart Data");
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Super Cool Chart");
        pieChart.setCenterTextSize(10);
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.size(); i++) {
            yEntrys.add(new PieEntry(yData.get(i), i));
        }

        for (int i = 1; i < xData.size(); i++) {
            xEntrys.add(xData.get(i));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Время по категориям");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void onChooseStartDateAnyTime(View view) {
        String startDate = ((TextView) view).getText().toString();
        String[] parts = startDate.split("/");
        startYear = Integer.parseInt(parts[0]);
        startMonth = Integer.parseInt(parts[1]);
        startDay = Integer.parseInt(parts[2]);
        openDateDialog(startDay, startMonth, startYear, mStartDateTextView);
    }

    public void onChooseEndDateAnyTime(View view) {
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

    public void setDateToTextView(final TextView textView, final String day, final String month, final String year) {
        textView.setText(String.format(
                "%s%s%s%s%s",
                day,
                "/",
                month,
                "/",
                year));
    }
}
