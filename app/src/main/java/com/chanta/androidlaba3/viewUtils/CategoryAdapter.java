package com.chanta.androidlaba3.viewUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.entity.Category;

import java.util.List;

/**
 * Created by chanta on 20.12.17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context, List<Category> players) {
        this.context = context;
        this.categories = players;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_category, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(itemLayoutView, context, categories);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.nameTxt.setText(categories.get(position).getName());
        holder.descriptionTxt.setText(categories.get(position).getDescriptionCategory());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

