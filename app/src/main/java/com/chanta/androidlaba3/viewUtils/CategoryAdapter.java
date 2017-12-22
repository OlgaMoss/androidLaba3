package com.chanta.androidlaba3.viewUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanta.androidlaba3.R;
import com.chanta.androidlaba3.dbUtils.dbAdapter.DbPhoto;
import com.chanta.androidlaba3.entity.Category;
import com.chanta.androidlaba3.entity.Photo;

import java.util.List;

/**
 * Created by chanta on 20.12.17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
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
        Integer photoId = categories.get(position).getPhotoId();
        if(photoId != null) {
            DbPhoto dbPhoto = new DbPhoto(context);
            dbPhoto.openDB();
            Photo photo = dbPhoto.getAllPhotos().get(photoId);
            byte[] foodImage = photo.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            holder.photo.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

