package com.example.finalwork2.Utils;

import android.app.Application;

import com.example.finalwork2.JavaClass.PYQ;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LCLogger;
import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyLeanCloudApp extends Application {
    private static LCObject pyq = new LCObject("PYQ");
    @Override
    public void onCreate() {
        super.onCreate();

        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
        // 提供 this、App ID、App Key、Server Host 作为参数
        // 注意这里千万不要调用 cn.leancloud.core.LeanCloud 的 initialize 方法，否则会出现 NetworkOnMainThread 等错误。
        LeanCloud.initialize(this, ContentUtils.lc_app_id, ContentUtils.lc_app_key, ContentUtils.lc_web);
    }

    final public static void add(int image_id, String date, String time, String name, String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                pyq.put("image_id",image_id);
                pyq.put("date",date);
                pyq.put("time",time);
                pyq.put("name",name);
                pyq.put("content",content);
                pyq.saveInBackground().blockingSubscribe();
            }
        }).start();
    }

    final public static ArrayList<PYQ> get_data(){
        ArrayList<PYQ> pyqs=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LCQuery<LCObject> query=new LCQuery<>("PYQ");
                query.limit(10);
                query.findInBackground().subscribe(new Observer<List<LCObject>>(){
                    public void onSubscribe(Disposable disposable) {}
                    public void onNext(List<LCObject> lists) {
                        for(LCObject lcObject:lists){
                            PYQ pyq=new PYQ(lcObject.getInt("image_id"),lcObject.getString("date"),lcObject.getString("time"),lcObject.getString("name"),lcObject.getString("content"));
                            pyqs.add(pyq);
                        }
                    }
                    public void onError(Throwable throwable) {}
                    public void onComplete() {}
                });
            }
        }).start();
        return pyqs;
    }



//    LCQuery<LCObject> query = new LCQuery<>("Student");
//    query.whereEqualTo("lastName", "Smith");
//    query.findInBackground().subscribe(new Observer<List<LCObject>>() {
//        public void onSubscribe(Disposable disposable) {}
//        public void onNext(List<LCObject> students) {
//            // students 是包含满足条件的 Student 对象的数组
//        }
//        public void onError(Throwable throwable) {}
//        public void onComplete() {}
//    });
}
//    public PYQ(int image_id, String date, String time, String name, String content) {
//        this.image_id = image_id;
//        this.date = date;
//        this.time = time;
//        this.name = name;
//        this.content = content;
//    }
