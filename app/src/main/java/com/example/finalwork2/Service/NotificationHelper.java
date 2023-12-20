package com.example.finalwork2.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.finalwork2.R;

/**
 * 参考：https://www.jianshu.com/p/b529e61d220a
 */
public class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    private NotificationChannel mNotificationChannel;
    private Context mContext;

    public static final String CHANNEL_ID = "Notify Test";
    private static final String CHANNEL_NAME = "Default Channel";
    private static final String CHANNEL_DESCRIPTION = "Notify Test";

    public NotificationHelper(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationChannel.setDescription(CHANNEL_DESCRIPTION);
            getNotificationManager().createNotificationChannel(mNotificationChannel);
        }
        this.mContext = context;
    }

    /**
     * 设置通知的标题和内容
     *
     * @param title
     * @param content
     * @return
     */
    public NotificationCompat.Builder getNotification(String title, String content) {
        NotificationCompat.Builder builder = null;
        /*安卓O开始的新要求，需指定发送的channel
         * 为发送的notification分类*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
        //点击自动删除通知
        builder.setAutoCancel(true);
        return builder;
    }

    /**
     * 发送,指定channel的id和builder
     *
     * @param id
     * @param builder
     */
    public void notify(int id, NotificationCompat.Builder builder) {
        if (getNotificationManager() != null) {
            getNotificationManager().notify(id, builder.build());
        }
    }

    public void openChannelSetting(String channelId) {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
            if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
                startActivity(intent);
        } else {
            Toast.makeText(mContext, "当前版本过低", Toast.LENGTH_SHORT).show();
        }

    }

    public void openNotificationSetting() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
                startActivity(intent);
        } else {
            Toast.makeText(mContext, "当前版本过低", Toast.LENGTH_SHORT).show();
        }

    }

    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        return mNotificationManager;
    }
}


