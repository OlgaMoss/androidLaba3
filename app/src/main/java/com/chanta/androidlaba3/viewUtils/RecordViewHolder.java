package com.chanta.androidlaba3.viewUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.entity.Record;

import java.util.List;

/**
 * Created by chanta on 21.12.17.
 */

public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private List<Record> records;


    TextView  descriptionTxt, dateStartTxt, dateEndTxt, roundedTimeTxt;

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


    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
