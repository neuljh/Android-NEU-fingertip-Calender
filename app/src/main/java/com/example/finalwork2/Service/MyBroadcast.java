package com.example.finalwork2.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyBroadcast extends BroadcastReceiver {
    private String TAG = "MyBroadcast";
    /**
     * use to send a notification
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast action", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onReceive: 1");
        String action = intent.getAction();
        if(action.equals(intent.getAction())) {
            Log.e("----", action);
            String extras = intent.getStringExtra("msg");
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder builder = notificationHelper.getNotification("日程提醒", extras);
            builder.build();
            notificationHelper.notify(1, builder);
            Toast.makeText(context, "Broadcast action", Toast.LENGTH_SHORT).show();
        }

    }
}
