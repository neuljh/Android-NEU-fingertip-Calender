package com.example.finalwork2.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.example.finalwork2.R;
import com.example.finalwork2.Service.NotificationHelper;

import java.lang.reflect.Method;
import java.util.Calendar;

public class NotifySettingActivity extends AppCompatActivity implements View.OnClickListener{

    protected Button notifyCurrentlyBtn, notifySettingBtn, alarmNotifyBtn, channelSettingBtn;
    protected NotificationHelper notificationHelper;
    public static final String INTENT_ALARM_LOG = "com.example.finalwork2.Service.RECEIVE";
    private String TAG;
    private Intent intent;
    private Toolbar toolbar;
    private int nav_num=3;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_setting);
        username=getIntent().getStringExtra("username");

        notifyCurrentlyBtn = findViewById(R.id.notify_currently);
        notifySettingBtn = findViewById(R.id.notify_setting);
        alarmNotifyBtn = findViewById(R.id.alarm_notify);
        channelSettingBtn = findViewById(R.id.channel_setting);
        notifySettingBtn.setOnClickListener(this);
        notifyCurrentlyBtn.setOnClickListener(this);
        alarmNotifyBtn.setOnClickListener(this);
        channelSettingBtn.setOnClickListener(this);
        notificationHelper = new NotificationHelper(this);
        TAG = this.getPackageName();


        init_toolbar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notify_currently:
                sendNotify();
                break;
            case R.id.notify_setting:
                notificationHelper.openNotificationSetting();
                break;
            case R.id.channel_setting:
                notificationHelper.openChannelSetting(NotificationHelper.CHANNEL_ID);
            case R.id.alarm_notify:
                clockSet();
                break;
            default:
                break;
        }
    }

    /**
     * 立刻发送一条notification
     */
    private void sendNotify(){
        NotificationCompat.Builder builder = notificationHelper.getNotification("日程提醒",
                "即时通知");
        builder.build();
        notificationHelper.notify(1,builder);
    }

    /**
     * 用一个Dialog来设置要发送的内容
     */
    private void clockSet(){
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("定时提醒");
        builder.setMessage("请输入内容");
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString();
                Log.v( TAG, input);
                clockSetting(input);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(NotifySettingActivity.this, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    /**
     * 调用AlarmManager服务来定时发送一条广播
     * 参考https://blog.csdn.net/kongqwesd12/article/details/78998151
     */
    private void clockSetting(final String string){
        final Calendar currentTime = Calendar.getInstance();
        new TimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /*set alarm to send a Broadcast*/
                Intent intent = new Intent();
                intent.setAction(INTENT_ALARM_LOG);
                /*安卓O开始对于静态注册的广播需要设置ComponentName*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.setComponent(new ComponentName("com.example.finalwork2",
                            "com.example.finalwork2.Service.MyBroadcast"));
                }
                intent.putExtra("msg", string);
                PendingIntent pi = PendingIntent.getBroadcast(NotifySettingActivity.this, 0, intent,
                        0);
                Calendar c = Calendar.getInstance();
                //set alarm time
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                //get the system's AlarmManager
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                /*非必要不使用RTC_WAKEUP*/
                if(alarmManager != null) {
                    //alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                    AlarmManager.AlarmClockInfo test=new AlarmManager.AlarmClockInfo(c.getTimeInMillis(),pi);
                    alarmManager.setAlarmClock(test,pi);
                    Log.e(TAG, c.getTime().toString());
                }else {
                    Log.e(TAG, "not found");
                }
                Log.i(TAG, String.valueOf(c.getTimeInMillis()));
            }
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();

    }

    public void init_toolbar(){
        toolbar=findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);

        //使用actionbar自带的返回键图片
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使用actionbar，自定义返回键图片
        actionBar.setHomeAsUpIndicator(R.drawable.ic_settings_white);

        //使用toolbar自定义返回键图片
        //toolbar.setNavigationIcon(R.drawable.ic_addbox);

        //actioBar设置标题
        actionBar.setTitle("1");
        //actionBar设置子标题
//        actionBar.setSubtitle("2");

        //toolbar设置标题
        toolbar.setTitle("通知设置");
        //toolbar设置标题颜色
        toolbar.setTitleTextColor(Color.WHITE);
        //toolbar设置子标题
        //toolbar.setSubtitle("周/月/年");
        //toolbar设置子标题颜色
        toolbar.setSubtitleTextColor(Color.BLACK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case R.id.action_back:
                intent=new Intent(NotifySettingActivity.this,MainActivity.class);
                intent.putExtra("nav_num",nav_num);
                intent.putExtra("username",username);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if ("MenuBuilder".equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
