package com.chanta.androidlaba3.entity;

/**
 * Created by chanta on 19.12.17.
 */

public class Record {
    private int id;
    private String descriptionRecord;
    private String dateStart;
    private String dateEnd;
    private String roundedTime;
    private String photoIdList;
    private int categoryId;

    public Record() {
    }

    public Record(int id, String descriptionRecord, String dateStart, String dateEnd, String roundedTime, String photoIdList, int categoryId) {
        this.id = id;
        this.descriptionRecord = descriptionRecord;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.roundedTime = roundedTime;
        this.photoIdList = photoIdList;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriptionRecord() {
        return descriptionRecord;
    }

    public void setDescriptionRecord(String descriptionRecord) {
        this.descriptionRecord = descriptionRecord;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getRoundedTime() {
        return roundedTime;
    }

    public void setRoundedTime(String roundedTime) {
        this.roundedTime = roundedTime;
    }

    public String getPhotoIdList() {
        return photoIdList;
    }

    public void setPhotoIdList(String photoIdList) {
        this.photoIdList = photoIdList;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
