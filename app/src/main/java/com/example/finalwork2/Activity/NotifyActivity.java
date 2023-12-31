package com.example.finalwork2.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.example.finalwork2.R;

public class NotifyActivity extends Activity {
    final int NOTIFYID = 0x123;            //通知的ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        notification();
    }
    public void notification(){
        //获取通知管理器，用于发送通知
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder notification = new Notification.Builder(this); // 创建一个Notification对象
        // 设置打开该通知，该通知自动消失
        notification.setAutoCancel(true);
        // 设置通知的图标
        notification.setSmallIcon(R.drawable.ic_add);
        // 设置通知内容的标题
        notification.setContentTitle("奖励百万红包！！！");
        // 设置通知内容
        notification.setContentText("点击查看详情！");
        //设置使用系统默认的声音、默认震动
        notification.setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE);
        //设置发送时间
        notification.setWhen(System.currentTimeMillis());
        // 创建一个启动其他Activity的Intent
        Intent intent = new Intent(this
                , SettingsActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0,intent,0);
        //设置通知栏点击跳转
        notification.setContentIntent(pi);
        //发送通知
        notificationManager.notify(NOTIFYID, notification.build());
    }
}
