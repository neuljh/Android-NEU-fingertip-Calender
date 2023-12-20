package com.example.finalwork2.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.finalwork2.Fragment.CalenderFragment;
import com.example.finalwork2.Fragment.MineFragment;
import com.example.finalwork2.Fragment.ScheduleFragment;
import com.example.finalwork2.Fragment.WorldFragment;
import com.example.finalwork2.R;
import com.example.finalwork2.Service.AlarmReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private int nav_num;
    private ScheduleFragment scheduleFragment;
    private CalenderFragment calenderFragment;
    private WorldFragment worldFragment;
    private MineFragment mineFragment;
    private Fragment mFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private boolean refresh;
    private String username;

    private PendingIntent mAlarmIntent;
    private long oneDay = 24*3600*1000;
    private long firstTime;


//    public void click(View v){
//        Toast.makeText(MainActivity.this, "创建定时提醒.", Toast.LENGTH_SHORT).show();
//
//        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY,16);
//        startTime.set(Calendar.MINUTE,21);
//        startTime.set(Calendar.SECOND, 0);
//        //判断是今天还是要等到明天
//        Calendar now = Calendar.getInstance();
//        if (now.before(startTime)){
//            firstTime = startTime.getTimeInMillis();
//        }else {
//            startTime.add(Calendar.DATE,1);
//        }
//        //设置提醒
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,firstTime,oneDay,mAlarmIntent);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //mAlarmIntent = PendingIntent.getBroadcast(this,0,new Intent(this,AlarmReceiver.class),0);
        //setReminder(true);

        nav_num=getIntent().getIntExtra("nav_num",0);
        refresh=getIntent().getBooleanExtra("refresh",false);
        username=getIntent().getStringExtra("username");




        init_view();

        //将refresh传出到各fragment中
        Bundle bundle = new Bundle();
        bundle.putBoolean("refresh",refresh);
        bundle.putString("username",username);
        scheduleFragment.setArguments(bundle);
        calenderFragment.setArguments(bundle);
        worldFragment.setArguments(bundle);
        mineFragment.setArguments(bundle);

        BottomNavigationBar();


    }

    public void init_view(){
        //去除默认标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        scheduleFragment=new ScheduleFragment();
        calenderFragment=new CalenderFragment();
        worldFragment=new WorldFragment();
        mineFragment=new MineFragment();
        transaction=getSupportFragmentManager().beginTransaction();

        switch (nav_num){
            case 0:
                transaction.add(R.id.layFrame,scheduleFragment).commit();
                mFragment = scheduleFragment;
                break;
            case 1:
                transaction.add(R.id.layFrame,calenderFragment).commit();
                mFragment = calenderFragment;
                break;
            case 2:
                transaction.add(R.id.layFrame,worldFragment).commit();
                mFragment = worldFragment;
                break;
            case 3:
                transaction.add(R.id.layFrame,mineFragment).commit();
                mFragment = mineFragment;
                break;
        }

    }

    /**
     * 初始化底部导航栏样式
     */
    private void BottomNavigationBar(){
//        bottomNavigationBar.setActiveColor(R.color.colorAccent)
//                .setInActiveColor(R.color.colorPrimary)
//                .setBarBackgroundColor("#FFFFFF");
        bottomNavigationBar=findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //背景样式
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_schedule_main, "日程").setActiveColorResource(R.color.bohelv))
                .addItem(new BottomNavigationItem(R.drawable.ic_calender_main, "日历").setActiveColorResource(R.color.bohelv))
                .addItem(new BottomNavigationItem(R.drawable.ic_world_main, "世界").setActiveColorResource(R.color.bohelv))
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_main,"我的")).setActiveColor(R.color.bohelv)
                .setFirstSelectedPosition(nav_num)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    /**
     * TAB被点击时的切换fragment
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch(position){
            case 0:
                switchFragment(scheduleFragment);
                break;
            case 1:
                switchFragment(calenderFragment);
                break;
            case 2:
                switchFragment(worldFragment);
                break;
            case 3:
                switchFragment(mineFragment);
                break;
            default:
                switchFragment(scheduleFragment);
                break;
        }
        transaction.commit();
    }


    @Override
    public void onTabUnselected(int position) { }

    @Override
    public void onTabReselected(int position) { }

    /**
     * 切换fragment的方法
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if(mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.layFrame,fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

    //+_+_+_+_+_+设置提醒+_+_+_+_+
    private void setReminder(boolean b) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_YEAR),calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE));
        AlarmManager am= (AlarmManager) getSystemService(ALARM_SERVICE);

        // 创建将执行广播的PendingIntent
        PendingIntent pi= PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(this, AlarmReceiver.class), 0);
        if(b){
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pi);
        }
        else{
            // cancel current alarm
            am.cancel(pi);
        }

    }
}
