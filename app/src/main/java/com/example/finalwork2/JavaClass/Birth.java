package com.example.finalwork2.JavaClass;

public class Birth {
    private String id;
    private int image_id;
    private String title;
    private String birthday;

    public Birth(String id, int image_id, String title, String birthday) {
        this.id = id;
        this.image_id = image_id;
        this.title = title;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
