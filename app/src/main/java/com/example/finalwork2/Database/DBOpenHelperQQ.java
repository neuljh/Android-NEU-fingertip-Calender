package com.example.finalwork2.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalwork2.JavaClass.QQ;

import java.util.ArrayList;

public class DBOpenHelperQQ extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBOpenHelperQQ(Context context) {
        super(context, "app_qq", null, 1);
        db = getReadableDatabase();
        //db.execSQL("delete from user");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS QQ(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "qq TEXT," +
                "app_id TEXT,"+
                "image_url TEXT,"+
                "nickname TEXT)"
        );
    }

    //版本适应
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Birth");
        onCreate(db);
    }

    public void add(String username,String qq,String app_id,String image_url,String nickname) {
        db.execSQL("INSERT INTO QQ(username,qq,app_id,image_url,nickname) VALUES(?,?,?,?,?)", new Object[]{username,qq,app_id,image_url,nickname});
    }

//    public void delete(String id) {
//        //db.execSQL("DELETE FROM user2 WHERE username = AND password = AND text = AND year = AND monthday = AND way = AND time ="+username+password+text+year+monthday+way+time);
//        //db.delete("user","id=?",new String[id]);
//        db.execSQL("DELETE FROM Birth WHERE id=?", new Object[]{id});
//        //db.execSQL("delete from user");
//    }

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

    public ArrayList<QQ> getAllQQs() {
        ArrayList<QQ> list = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("QQ", null, null, null, null, null, "id ASC");
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex("id"));
            String username=cursor.getString(cursor.getColumnIndex("username"));
            String qq=cursor.getString(cursor.getColumnIndex("qq"));
            String app_id=cursor.getString(cursor.getColumnIndex("app_id"));
            String image_url=cursor.getString(cursor.getColumnIndex("image_url"));
            String nickname=cursor.getString(cursor.getColumnIndex("nickname"));

            list.add(new QQ(id,username,qq,app_id,image_url,nickname));
        }
        return list;
    }
}
