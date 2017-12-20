package com.chanta.androidlaba3.viewUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbCategory;
import com.chanta.androidlaba3.entity.Category;

import java.util.List;

/**
 * Created by chanta on 20.12.17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    ImageView photo;
    TextView nameTxt, descriptionTxt;
    Context context;
    List<Category> categories;

    public CategoryViewHolder(View itemView, Context context, List<Category> categories) {
        super(itemView);
        this.context = context;
        this.categories = categories;

        photo = itemView.findViewById(R.id.imageView);
        nameTxt = itemView.findViewById(R.id.text_name_category);
        descriptionTxt = itemView.findViewById(R.id.text_description_category);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //todo интент на новый фрейм
    }


    @Override
    public boolean onLongClick(View view) {
        LayoutInflater li = LayoutInflater.from(view.getContext());
        final View layoutView = li.inflate(R.layout.edit_category, null);
        final int position = this.getLayoutPosition();
        final DbCategory dbCategory = new DbCategory(context);

        final EditText editNameText = layoutView.findViewById(R.id.edit_name_category);
        final EditText editDescriptionText = layoutView.findViewById(R.id.edit_description_category);
//        List<Category> categories = dbCategory.getAllCategories();
        editNameText.setText(categories.get(position).getName());
        editDescriptionText.setText(categories.get(position).getDescriptionCategory());

        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(layoutView)
                .setTitle("Edit category")
                .setCancelable(false)
                .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbCategory.updateCategory(
                                position,
                                editNameText.getText().toString(),
                                editDescriptionText.getText().toString(),
                                "null");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}
