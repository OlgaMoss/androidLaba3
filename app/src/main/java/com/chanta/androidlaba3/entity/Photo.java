package com.chanta.androidlaba3.entity;

import android.graphics.Bitmap;

/**
 * Created by chanta on 19.12.17.
 */

public class Photo {
    private int id;
    private String title;
    private byte[] image;

    public Photo() {
    }

    public Photo(int id, String title, byte[] image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
