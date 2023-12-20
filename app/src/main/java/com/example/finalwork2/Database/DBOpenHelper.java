package com.example.finalwork2.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.finalwork2.JavaClass.Schedule;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBOpenHelper(Context context) {
        super(context, "app_schedule", null, 1);
        db = getReadableDatabase();
        //db.execSQL("delete from user");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Schedule(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "finish TEXT," +
                "registerdate TEXT," +
                "scheduledate TEXT," +
                "category TEXT," +
                "remindtime TEXT," +
                "priority TEXT," +
                "starttime TEXT," +
                "endtime TEXT," +
                "tips TEXT)"
        );
    }


    //版本适应
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Schedule");
        onCreate(db);
    }

    public void add(String title, String finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips) {
        db.execSQL("INSERT INTO Schedule (title,finish,registerdate,scheduledate,category,remindtime,priority,starttime,endtime,tips) VALUES(?,?,?,?,?,?,?,?,?,?)", new Object[]{title,finish,registerdate,scheduledate,category,remindtime,priority,starttime,endtime,tips});
    }

    public void delete(String id) {
        //db.execSQL("DELETE FROM user2 WHERE username = AND password = AND text = AND year = AND monthday = AND way = AND time ="+username+password+text+year+monthday+way+time);
        //db.delete("user","id=?",new String[id]);
        db.execSQL("DELETE FROM Schedule WHERE id=?", new Object[]{id});
        //db.execSQL("delete from user");
    }

    public void update(String id,String title, String finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips) {
        db.execSQL("UPDATE Schedule SET title = ? WHERE id=?", new Object[]{title, id});
        db.execSQL("UPDATE Schedule SET finish = ? WHERE id=?", new Object[]{finish, id});
        db.execSQL("UPDATE Schedule SET registerdate = ? WHERE id=?", new Object[]{registerdate, id});
        db.execSQL("UPDATE Schedule SET scheduledate = ? WHERE id=?", new Object[]{scheduledate, id});
        db.execSQL("UPDATE Schedule SET category = ? WHERE id=?", new Object[]{category, id});
        db.execSQL("UPDATE Schedule SET remindtime = ? WHERE id=?", new Object[]{remindtime, id});
        db.execSQL("UPDATE Schedule SET priority = ? WHERE id=?", new Object[]{priority, id});
        db.execSQL("UPDATE Schedule SET starttime = ? WHERE id=?", new Object[]{starttime, id});
        db.execSQL("UPDATE Schedule SET endtime = ? WHERE id=?", new Object[]{endtime, id});
        db.execSQL("UPDATE Schedule SET tips = ? WHERE id=?", new Object[]{tips, id});
    }
    //    private String title;
//    private boolean finish;
//    private String registerdate;
//    private String scheduledate;
//    private String category;
//    private String remindtime;
//    private String priority;
//    private String starttime;
//    private String endtime;
//    private String tips;
    public ArrayList<Schedule> getAllschedules() {
        ArrayList<Schedule> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("Schedule", null, null, null, null, null, "id ASC");
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex("id"));

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String finish = cursor.getString(cursor.getColumnIndex("finish"));
            String registerdate = cursor.getString(cursor.getColumnIndex("registerdate"));
            String scheduledate = cursor.getString(cursor.getColumnIndex("scheduledate"));

            String category = cursor.getString(cursor.getColumnIndex("category"));
            String remindtime = cursor.getString(cursor.getColumnIndex("remindtime"));
            String priority = cursor.getString(cursor.getColumnIndex("priority"));

            String starttime = cursor.getString(cursor.getColumnIndex("starttime"));
            String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
            String tips = cursor.getString(cursor.getColumnIndex("tips"));

            list.add(new Schedule(id,title,Boolean.valueOf(finish),registerdate,scheduledate,category,remindtime,priority,starttime,endtime,tips));

        }
        return list;
    }

//    public ArrayList<Home> getUserHome(String UserName) {
//        ArrayList<Home> list = new ArrayList<>();
//        @SuppressLint("Recycle") Cursor cursor = db.query("user", null, null, null, null, null, "id ASC");
//        while (cursor.moveToNext()) {
//
//            String id = cursor.getString(cursor.getColumnIndex("id"));
//
//            String username = cursor.getString(cursor.getColumnIndex("username"));
//            String password = cursor.getString(cursor.getColumnIndex("password"));
//            String title = cursor.getString(cursor.getColumnIndex("title"));
//            String date = cursor.getString(cursor.getColumnIndex("date"));
//
//            String male_name = cursor.getString(cursor.getColumnIndex("male_name"));
//            String female_name = cursor.getString(cursor.getColumnIndex("female_name"));
//            String male_pic = cursor.getString(cursor.getColumnIndex("male_pic"));
//
//            String female_pic = cursor.getString(cursor.getColumnIndex("female_pic"));
//            String time = cursor.getString(cursor.getColumnIndex("time"));
//            String main_pic = cursor.getString(cursor.getColumnIndex("main_pic"));
//
//            if (username!=null){
//                if(username.equals(UserName)){
//                    list.add(new Home(id, Integer.parseInt(male_pic), Integer.parseInt(female_pic), male_name, female_name, date, time, "days", Integer.parseInt(main_pic), title));
//                }
//            }
//        }
//        return list;
//    }
}

