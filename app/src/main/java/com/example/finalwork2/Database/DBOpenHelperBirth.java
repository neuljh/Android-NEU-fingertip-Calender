package com.example.finalwork2.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalwork2.JavaClass.Birth;

import java.util.ArrayList;

public class DBOpenHelperBirth extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBOpenHelperBirth(Context context) {
        super(context, "app_birth", null, 1);
        db = getReadableDatabase();
        //db.execSQL("delete from user");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Birth(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "image_id INTEGER," +
                "title TEXT," +
                "birthday TEXT)"
        );
    }
//    private String id;
//    private int image_id;
//    private String title;
//    private String birthday;


    //版本适应
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Birth");
        onCreate(db);
    }

    public void add(int image_id,String title,String birthday) {
        db.execSQL("INSERT INTO Birth (image_id,title,birthday) VALUES(?,?,?)", new Object[]{image_id,title,birthday});
    }

    public void delete(String id) {
        //db.execSQL("DELETE FROM user2 WHERE username = AND password = AND text = AND year = AND monthday = AND way = AND time ="+username+password+text+year+monthday+way+time);
        //db.delete("user","id=?",new String[id]);
        db.execSQL("DELETE FROM Birth WHERE id=?", new Object[]{id});
        //db.execSQL("delete from user");
    }

//    public void update(String id,String title, boolean finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips) {
//        db.execSQL("UPDATE Schedule SET title = ? WHERE id=?", new Object[]{title, id});
//        db.execSQL("UPDATE Schedule SET finish = ? WHERE id=?", new Object[]{finish, id});
//        db.execSQL("UPDATE Schedule SET registerdate = ? WHERE id=?", new Object[]{registerdate, id});
//        db.execSQL("UPDATE Schedule SET scheduledate = ? WHERE id=?", new Object[]{scheduledate, id});
//        db.execSQL("UPDATE Schedule SET category = ? WHERE id=?", new Object[]{category, id});
//        db.execSQL("UPDATE Schedule SET remindtime = ? WHERE id=?", new Object[]{remindtime, id});
//        db.execSQL("UPDATE Schedule SET priority = ? WHERE id=?", new Object[]{priority, id});
//        db.execSQL("UPDATE Schedule SET starttime = ? WHERE id=?", new Object[]{starttime, id});
//        db.execSQL("UPDATE Schedule SET endtime = ? WHERE id=?", new Object[]{endtime, id});
//        db.execSQL("UPDATE Schedule SET tips = ? WHERE id=?", new Object[]{tips, id});
//    }

    public ArrayList<Birth> getAllbirths() {
        ArrayList<Birth> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("Birth", null, null, null, null, null, "id ASC");
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex("id"));
            int image_id=cursor.getInt(cursor.getColumnIndex("image_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String birthday=cursor.getString(cursor.getColumnIndex("birthday"));

            list.add(new Birth(id,image_id,title,birthday));

        }
        return list;
    }
}
