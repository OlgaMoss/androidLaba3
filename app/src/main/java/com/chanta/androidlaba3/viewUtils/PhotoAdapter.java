package com.chanta.androidlaba3.viewUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.entity.Photo;

import java.util.List;

/**
 * Created by chanta on 22.12.17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private List<Photo> photos;
    private Context context;

    public PhotoAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_photo, parent, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(itemLayoutView, context, photos);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.titleTxt.setText(photos.get(position).getTitle());
        byte[] foodImage = photos.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imagesView.setImageBitmap(bitmap);

    }


    @Override
    public int getItemCount() {
        return photos.size();
    }
}
