package com.chanta.androidlaba3.viewUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.entity.Record;

import java.util.List;

/**
 * Created by chanta on 21.12.17.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {
    private List<Record> records;
    private Context context;

    public RecordAdapter(Context context, List<Record> records) {
        this.context = context;
        this.records = records;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_record, parent, false);
        RecordViewHolder viewHolder = new RecordViewHolder(itemLayoutView, context, records);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        holder.descriptionTxt.setText(records.get(position).getDescriptionRecord());
        holder.dateStartTxt.setText(records.get(position).getDateStart());
        holder.dateEndTxt.setText(records.get(position).getDateEnd());
        holder.roundedTimeTxt.setText(records.get(position).getRoundedTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
