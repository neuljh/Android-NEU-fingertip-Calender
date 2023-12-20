package com.example.finalwork2.JavaClass;

public class User {
    private String name;
    private String password;
    private String email;
    private String phonenumber;
    private  String username;
    private String school;
    private String sex;

    public User(String name, String password, String email, String phonenumber, String username, String school, String sex) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.username = username;
        this.school = school;
        this.sex = sex;
    }

    public User() {
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
