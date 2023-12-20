package com.example.finalwork2.JavaClass;

public class PYQ {
    private String id;
    private int image_id;
    private String date;
    private String time;
    private String name;
    private String content;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PYQ(String id, int image_id, String date, String time, String name, String content) {
        this.id = id;
        this.image_id = image_id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.content = content;
    }

    public PYQ(int image_id, String date, String time, String name, String content) {
        this.image_id = image_id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.content = content;
    }
}
