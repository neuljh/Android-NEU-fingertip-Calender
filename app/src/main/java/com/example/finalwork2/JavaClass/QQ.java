package com.example.finalwork2.JavaClass;

public class QQ {
    private String id;
    private String username;
    private String qq;
    private String app_id;
    private String image_url;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public QQ(String id, String username, String qq, String app_id, String image_url, String nickname) {
        this.id = id;
        this.username = username;
        this.qq = qq;
        this.app_id = app_id;
        this.image_url = image_url;
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
//    public void add(String username,String qq,String app_id,String image_url) {
//        db.execSQL("INSERT INTO QQ(username,qq,app_id,image_url) VALUES(?,?,?,?)", new Object[]{username,qq,app_id,image_url});
//    }
