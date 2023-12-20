package com.example.finalwork2.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalwork2.JavaClass.SimpleNEUClass;

import java.util.ArrayList;


public class DBOpenHelperClass extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBOpenHelperClass(Context context) {
        super(context, "NEU_class", null, 1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS class(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "weekday TEXT," +
                "name TEXT," +
                "classroom TEXT," +
                "section_number TEXT," +
                "teacher TEXT," +
                "week_number TEXT)"
        );
    }

    //版本适应
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS class");
        onCreate(db);
    }

    public void add(String weekday, String name, String classroom, String section_number, String teacher, String week_number) {
        db.execSQL("INSERT INTO class (weekday,name,classroom,section_number,teacher,week_number) VALUES(?,?,?,?,?,?)", new Object[]{weekday,name,classroom,section_number,teacher,week_number});
    }

//    public void delete(String id) {
//        //db.execSQL("DELETE FROM user2 WHERE username = AND password = AND text = AND year = AND monthday = AND way = AND time ="+username+password+text+year+monthday+way+time);
//        //db.delete("user","id=?",new String[id]);
//        db.execSQL("DELETE FROM user WHERE id=?", new Object[]{id});
//        //db.execSQL("delete from user");
//    }

//    public void update(String id, String title, String date, String male_name, String female_name, String male_pic, String female_pic, String time, String main_pic) {
////        db.execSQL("UPDATE user SET title = ? WHERE id=?", new Object[]{title, id});
////        db.execSQL("UPDATE user SET date = ? WHERE id=?", new Object[]{date, id});
////        db.execSQL("UPDATE user SET male_name = ? WHERE id=?", new Object[]{male_name, id});
////        db.execSQL("UPDATE user SET female_name = ? WHERE id=?", new Object[]{female_name, id});
////        db.execSQL("UPDATE user SET male_pic = ? WHERE id=?", new Object[]{male_pic, id});
////        db.execSQL("UPDATE user SET female_pic = ? WHERE id=?", new Object[]{female_pic, id});
////        db.execSQL("UPDATE user SET time = ? WHERE id=?", new Object[]{time, id});
////        db.execSQL("UPDATE user SET main_pic = ? WHERE id=?", new Object[]{main_pic, id});
////    }

    public ArrayList<SimpleNEUClass> getAllneuClass() {
        ArrayList<SimpleNEUClass> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("class", null, null, null, null, null, "id ASC");
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex("id"));

            String weekday = cursor.getString(cursor.getColumnIndex("weekday"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
            String section_number = cursor.getString(cursor.getColumnIndex("section_number"));
            String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
            String week_number = cursor.getString(cursor.getColumnIndex("week_number"));
            ArrayList<Integer> sections=new ArrayList<>();
            ArrayList<Integer> weeks=new ArrayList<>();
            String section_array[]=section_number.split(",");
            String week_array[]=week_number.split(",");
            for(int i=0;i<section_array.length;i++){
                sections.add(Integer.parseInt(section_array[i]));
            }
            for(int i=0;i<week_array.length;i++){
                weeks.add(Integer.parseInt(week_array[i]));
            }
            list.add(new SimpleNEUClass(Integer.parseInt(weekday),name,classroom,sections,teacher,weeks));

        }//String weekday, String name, String classroom, String section_number, String teacher, String week_number
        return list;
    }

//    public ArrayList<SimpleNEUClass> getAllneuClass(String UserName) {
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


