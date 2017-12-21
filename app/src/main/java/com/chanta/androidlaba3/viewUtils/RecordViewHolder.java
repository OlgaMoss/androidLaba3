package com.chanta.androidlaba3.viewUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanta.androidlaba3.DetailRecordActivity;
import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbRecord;
import com.chanta.androidlaba3.entity.Record;

import java.util.List;

/**
 * Created by chanta on 21.12.17.
 */

public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public static final String RECORD = "Record";

    private Context context;
    private List<Record> records;


    TextView descriptionTxt, dateStartTxt, dateEndTxt, roundedTimeTxt;

    public RecordViewHolder(View itemView, Context context, List<Record> records) {
        super(itemView);
        this.context = context;
        this.records = records;

        descriptionTxt = itemView.findViewById(R.id.text_description_record);
        dateStartTxt = itemView.findViewById(R.id.text_dateStart);
        dateEndTxt = itemView.findViewById(R.id.text_dateEnd);
        roundedTimeTxt = itemView.findViewById(R.id.text_rounded_time);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int position = this.getLayoutPosition();

        Intent intent = new Intent(context, DetailRecordActivity.class);
        intent.putExtra(RECORD, records.get(position));
        context.startActivity(intent);

    }

    @Override
    public boolean onLongClick(View view) {

        final int position = this.getLayoutPosition();
        final DbRecord dbRecord = new DbRecord(context);

        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Удалить запись?")
                .setCancelable(false)
                .setPositiveButton(R.string.choice_ok, (dialog, which) -> dbRecord.deleteItem(records.get(position - 1).getId()))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }
}
