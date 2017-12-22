package com.chanta.androidlaba3.viewUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbPhoto;
import com.chanta.androidlaba3.entity.Photo;

import java.util.List;

/**
 * Created by chanta on 22.12.17.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private Context context;
    private List<Photo> photos;


    TextView titleTxt;
    ImageView imagesView;

    public PhotoViewHolder(View itemView, Context context, List<Photo> photos) {
        super(itemView);
        this.context = context;
        this.photos = photos;

        titleTxt = itemView.findViewById(R.id.text_title_photo);
        imagesView = itemView.findViewById(R.id.imageView_photo);


        itemView.setOnClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        final int position = this.getLayoutPosition();
        final DbPhoto dbPhoto = new DbPhoto(context);

        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Удалить фото?")
                .setCancelable(false)
                .setPositiveButton(R.string.choice_ok, (dialog, which) -> dbPhoto.deleteItem(photos.get(position).getId()))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

    @Override
    public void onClick(View view) {

        LayoutInflater li = LayoutInflater.from(view.getContext());
        final View layoutView = li.inflate(R.layout.edit_photo, null);
        final int position = this.getLayoutPosition();
        final DbPhoto dbPhoto = new DbPhoto(context);

        final EditText editTitleText = layoutView.findViewById(R.id.edit_title_photo);
        editTitleText.setText(photos.get(position).getTitle());

        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(layoutView)
                .setTitle("Править фото")
                .setCancelable(false)
                .setPositiveButton(R.string.choice_ok, (dialog, which) -> {
                    dbPhoto.updatePhoto(
                            position,
                            editTitleText.getText().toString(),
                            photos.get(position).getImage());
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
