package com.chanta.androidlaba3.entity;

/**
 * Created by chanta on 19.12.17.
 */

public class Photo {
    private int id;
    private String title;
    private String path;

    public Photo() {
    }

    public Photo(int id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
