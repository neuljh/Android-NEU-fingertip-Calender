package com.example.finalwork2.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager manager;


    //当BroadcastReceiver接收到Intent广播时调用。
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//
//        Toast.makeText(context, "闹铃响了, 可以做点事情了~~", Toast.LENGTH_LONG).show();
//
//        manager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
//        //例如这个id就是你传过来的
//        String id = intent.getStringExtra("id");
//        id= "0";
//        //MainActivity是你点击通知时想要跳转的Activity
//        Intent playIntent = new Intent(context, MainActivity.class);
//        playIntent.putExtra("id", id);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setContentTitle("title").setContentText("提醒内容").setSmallIcon(R.drawable.ic_add).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true).setSubText("二级text");
//        manager.notify(1, builder.build());
//    }
    @Override
    public void onReceive(Context context, Intent intent) {
        WebView webView = new WebView(context);
        webView.loadUrl("http://www.baidu.com");
        Toast.makeText(context, "定时,任务", Toast.LENGTH_SHORT).show();
    }
}

