package com.example.finalwork2.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork2.Database.DBOpenHelper;
import com.example.finalwork2.JavaClass.Schedule;
import com.example.finalwork2.Popwindow.Schedule_popActivity;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleInforActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "tzbc";
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar= Calendar.getInstance();
    private String schedule_date;
    private String cate;
    private String remind_time;
    private String  priority;
    private String start_time;
    private String end_time;
    private String tips;
    private String id="-1";
    private boolean refresh=true;



    private int pri_checknum=-1,cate_checknum=-1;


    private boolean sign;
    private String title;
    private String date;
    private Intent intent;

    private TextView tv_date,tv_category,tv_remind,tv_priority,tv_starttime,tv_endtime,tv_finish,tv_riqi;
    private CheckBox cb_title;
    private EditText et_tips;
    private Button bt_delete,bt_popwindow;
    private ImageView iv_back;
    private DBOpenHelper dbOpenHelper;
    private ArrayList<Schedule> schedules;
    private String username;

    public static final String INTENT_ALARM_LOG = "com.example.finalwork2.Service.RECEIVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_infor);
        username=getIntent().getStringExtra("username");
        dbOpenHelper=new DBOpenHelper(this);

        init_data();
        set_data();
    }

//intent0.putExtra("sign",sign);
//        intent0.putExtra("title",title);
//        intent0.putExtra("date",registerdate);
//
//        intent0.putExtra("scheduledate",scheduledate);
//        intent0.putExtra("category",category);
//        intent0.putExtra("remindtime",remindtime);
//        intent0.putExtra("priority",priority);
//        intent0.putExtra("starttime",starttime);
//        intent0.putExtra("endtime",endtime);
//        intent0.putExtra("tips",tips);

    public void init_data(){
        intent=getIntent();
//        sign=intent.getBooleanExtra("sign",false);
//        title=intent.getStringExtra("title");
//        date=intent.getStringExtra("date");
//
//        schedule_date=intent.getStringExtra("scheduledate");
//        cate=intent.getStringExtra("category");
//        remind_time=intent.getStringExtra("remindtime");
//        priority=intent.getStringExtra("priority");
//        start_time=intent.getStringExtra("starttime");
//        end_time=intent.getStringExtra("endtime");
//        tips=intent.getStringExtra("tips");

        id=intent.getStringExtra("id");
        schedules=new ArrayList<>();
        schedules=dbOpenHelper.getAllschedules();
        for(int i=0;i<schedules.size();i++){
            if(id.equals(schedules.get(i).getId())){
                sign=schedules.get(i).isFinish();
                title=schedules.get(i).getTitle();
                date=schedules.get(i).getRegisterdate();
                schedule_date=schedules.get(i).getScheduledate();
                cate=schedules.get(i).getCategory();
                remind_time=schedules.get(i).getRemindtime();
                priority=schedules.get(i).getPriority();
                start_time=schedules.get(i).getStarttime();
                end_time=schedules.get(i).getEndtime();
                tips=schedules.get(i).getTips();
            }
        }



        cb_title=findViewById(R.id.scheduleinfor_cb_title);
        tv_date=findViewById(R.id.scheduleinfor_tv_time);
        tv_riqi=findViewById(R.id.scheduleinfor_tv_date);
        tv_category=findViewById(R.id.scheduleinfor_tv_category);
        tv_remind=findViewById(R.id.scheduleinfor_tv_remind);
        tv_priority=findViewById(R.id.scheduleinfor_tv_priority);
        tv_starttime=findViewById(R.id.scheduleinfor_tv_start);
        tv_endtime=findViewById(R.id.scheduleinfor_tv_end);
        bt_delete=findViewById(R.id.schedule_bt_delete);
        bt_popwindow=findViewById(R.id.schedule_bt_popwindow);
        tv_finish=findViewById(R.id.scheduleinfor_tv_finish);
        iv_back=findViewById(R.id.scheduleinfor_iv_back);
        et_tips=findViewById(R.id.scheduleinfor_et_tips);

        iv_back.setOnClickListener(this);
        tv_riqi.setOnClickListener(this);
        tv_remind.setOnClickListener(this);
        tv_starttime.setOnClickListener(this);
        tv_endtime.setOnClickListener(this);
        bt_popwindow.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        tv_priority.setOnClickListener(this);
        tv_category.setOnClickListener(this);
        tv_finish.setOnClickListener(this);

    }
    public void set_data(){
        cb_title.setText(title);
        cb_title.setChecked(sign);
        tv_date.setText(date);

        et_tips.setText(tips);


    }

    public void tips_cate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleInforActivity.this);
        builder.setIcon(R.drawable.ic_daojishi);
        builder.setTitle("日程分类");
        final String[] vitals = ContentUtils.Schedule_Category;

        builder.setSingleChoiceItems(vitals, cate_checknum, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你选择了" + vitals[which], Toast.LENGTH_SHORT).show();
                cate_checknum= which;
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(cate_checknum!=-1){
                    cate=vitals[cate_checknum];
                }else{
                    cate="NULL";
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }


    public void tips_priority() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleInforActivity.this);
        builder.setIcon(R.drawable.ic_daojishi);
        builder.setTitle("日程优先级");
        final String[] vitals = ContentUtils.Schedule_Priority;

        builder.setSingleChoiceItems(vitals, pri_checknum, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你选择了" + vitals[which], Toast.LENGTH_SHORT).show();
                pri_checknum= which;
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(pri_checknum!=-1){
                    priority=vitals[pri_checknum];
                }else{
                    priority="NULL";
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    private void showTimeDialog(int index,String time) {
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + "时" + minute + "分";
                Log.e(TAG, "time : " + time);
                switch (index){
                    case 0:
                        remind_time=time;
                        break;
                    case 1:
                        start_time=time;
                        break;
                    case 2:
                        end_time=time;
                        break;
                }
            }
        }, Integer.parseInt(ContentUtils.From_Time_Get_Hour(time)), Integer.parseInt(ContentUtils.From_Time_Get_Min(time)), true);
        timePickerDialog.show();
    }

    private void showCalenderDialog(String date) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String calender = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                Log.e(TAG, "calender : " + calender);

                schedule_date=calender;
            }
        }, Integer.parseInt(ContentUtils.From_Date_Get_Year(date)), Integer.parseInt(ContentUtils.From_Date_Get_Month(date)), Integer.parseInt(ContentUtils.From_Date_Get_Day(date)));
        datePickerDialog.show();
    }

    /**
     * 用一个Dialog来设置要发送的内容
     */
    private void clockSet(String time){
        //final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("定时提醒开启");
        builder.setMessage("定时提醒已经开启啦！主人可以等待日程提醒啦！");
        //builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = "日程: "+title+" 提醒时间到啦！";
                Log.v( TAG, input);
                clockSetting(input,time);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ScheduleInforActivity.this, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    /**
     * 调用AlarmManager服务来定时发送一条广播
     * 参考https://blog.csdn.net/kongqwesd12/article/details/78998151
     */
    private void clockSetting(final String string,String time){
        //final Calendar currentTime = Calendar.getInstance();
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
                PendingIntent pi = PendingIntent.getBroadcast(ScheduleInforActivity.this, 0, intent,
                        0);
                Calendar c = Calendar.getInstance();
                //set alarm time
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                String retime = hourOfDay + "时" + minute + "分";
                remind_time=retime;
                //get the system's AlarmManager
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                /*非必要不使用RTC_WAKEUP*/
                if(alarmManager != null) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                    Log.e(TAG, c.getTime().toString());
                }else {
                    Log.e(TAG, "not found");
                }
                Log.i(TAG, String.valueOf(c.getTimeInMillis()));
            }
        }, Integer.parseInt(ContentUtils.From_Time_Get_Hour(time)), Integer.parseInt(ContentUtils.From_Time_Get_Min(time)), true).show();

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scheduleinfor_tv_finish:
                tips=et_tips.getText().toString();
                sign=cb_title.isChecked();
                if(!id.equals("-1")){
                    dbOpenHelper.update(id,title,String.valueOf(sign),date,schedule_date,cate,remind_time,priority,start_time,end_time,tips);
                    Toast.makeText(getApplicationContext(), "编辑日程信息成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "编辑日程信息失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.scheduleinfor_iv_back:
                intent=new Intent(ScheduleInforActivity.this,MainActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.scheduleinfor_tv_date:
                showCalenderDialog(schedule_date);
                break;
            case R.id.scheduleinfor_tv_start:
                showTimeDialog(1,start_time);
                break;
            case R.id.scheduleinfor_tv_end:
                showTimeDialog(2,end_time);
                break;
            case R.id.scheduleinfor_tv_remind:
                //showTimeDialog(0,remind_time);
                clockSet(remind_time);//未完成
                break;
            case R.id.scheduleinfor_tv_priority:
                tips_priority();
                break;
            case R.id.scheduleinfor_tv_category:
                tips_cate();
                break;
            case R.id.schedule_bt_popwindow:
                intent=new Intent(ScheduleInforActivity.this, Schedule_popActivity.class);
                intent.putExtra("scheduledate",schedule_date);
                intent.putExtra("remindtime",remind_time);
                intent.putExtra("starttime",start_time);
                intent.putExtra("endtime",end_time);

                intent.putExtra("title",title);
                intent.putExtra("date",date);
                intent.putExtra("sign",sign);


                intent.putExtra("priority",priority);
                intent.putExtra("category",cate);

                startActivity(intent);
                break;
            case R.id.schedule_bt_delete:
                if(!id.equals("-1")){
                    dbOpenHelper.delete(id);
                    intent=new Intent(ScheduleInforActivity.this,MainActivity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("refresh",refresh);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "删除日程成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "删除日程失败", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
