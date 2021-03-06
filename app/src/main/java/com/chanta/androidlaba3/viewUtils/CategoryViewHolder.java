package com.chanta.androidlaba3.viewUtils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chanta.androidlaba3.EditNewRecordActivity;
import com.chanta.androidlaba3.MainActivity;
import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.RecordsActivity;
import com.chanta.androidlaba3.dbUtils.DbHelper;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbCategory;
import com.chanta.androidlaba3.entity.Category;
import com.chanta.androidlaba3.entity.Photo;

import java.util.List;

/**
 * Created by chanta on 20.12.17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    ImageView photo;
    TextView nameTxt, descriptionTxt;
    Context context;
    List<Category> categories;
    int photoId;
    public static final int REQUEST_CODE_GALLERY = 999;

    public CategoryViewHolder(View itemView, Context context, List<Category> categories) {
        super(itemView);
        this.context = context;
        this.categories = categories;

        photo = itemView.findViewById(R.id.imageView);
        nameTxt = itemView.findViewById(R.id.text_description_record);
        descriptionTxt = itemView.findViewById(R.id.text_description_category);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int position = this.getLayoutPosition();
        Intent intent = new Intent(context, RecordsActivity.class);
        intent.putExtra(DbHelper.CATEGORY_ID, position);
        context.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        LayoutInflater li = LayoutInflater.from(view.getContext());
        final View layoutView = li.inflate(R.layout.edit_category, null);
        final int position = this.getLayoutPosition();
        final DbCategory dbCategory = new DbCategory(context);

        final EditText editNameText = layoutView.findViewById(R.id.edit_name_category);
        final EditText editDescriptionText = layoutView.findViewById(R.id.edit_description_category);
        editNameText.setText(categories.get(position).getName());
        editDescriptionText.setText(categories.get(position).getDescriptionCategory());

        RadioButton redRadioButton = layoutView.findViewById(R.id.radio_red);
        redRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton greenRadioButton = layoutView.findViewById(R.id.radio_green);
        greenRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton blueRadioButton = layoutView.findViewById(R.id.radio_blue);
        blueRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton grayRadioButton = layoutView.findViewById(R.id.radio_gray);
        grayRadioButton.setOnClickListener(radioButtonClickListener);


        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(layoutView)
                .setTitle("Править категорию")
                .setCancelable(false)
                .setPositiveButton(R.string.choice_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbCategory.updateCategory(
                                position,
                                editNameText.getText().toString(),
                                editDescriptionText.getText().toString(),
                                photoId);
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

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;

            switch (rb.getId()) {
                case R.id.radio_red:
                    photoId = 1;
                    break;
                case R.id.radio_green:
                    photoId = 2;
                    break;
                case R.id.radio_blue:
                    photoId = 3;
                    break;
                case R.id.radio_gray:
                    photoId = 4;
                    break;
                case R.id.radio_pink:
                    photoId = 5;
                    break;

                default:
                    break;
            }
        }
    };
}
