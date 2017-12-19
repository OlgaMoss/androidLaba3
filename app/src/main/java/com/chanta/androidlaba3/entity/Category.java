package com.chanta.androidlaba3.entity;

/**
 * Created by chanta on 19.12.17.
 */

public class Category {
    private int id;
    private String name;
    private String descriptionCategory;
    private int photoId;

    public Category() {
    }

    public Category(int id, String name, String descriptionCategory, int photoId) {
        this.id = id;
        this.name = name;
        this.descriptionCategory = descriptionCategory;
        this.photoId = photoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionCategory() {
        return descriptionCategory;
    }

    public void setDescriptionCategory(String descriptionCategory) {
        this.descriptionCategory = descriptionCategory;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
