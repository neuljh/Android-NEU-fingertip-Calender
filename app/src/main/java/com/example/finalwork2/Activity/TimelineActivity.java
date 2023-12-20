package com.example.finalwork2.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork2.Database.DBOpenHelper;
import com.example.finalwork2.JavaClass.RecyclerAdapterTimeline;
import com.example.finalwork2.JavaClass.Schedule;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TimelineActivity extends AppCompatActivity implements RecyclerAdapterTimeline.OnItemClickListener{
    private Toolbar toolbar;
    private Intent intent;
    private int nav_num=0;
    private RecyclerView recyclerView;
    private RecyclerAdapterTimeline recyclerAdapterTimeline;
    private ArrayList<Schedule> schedules;
    //private ArrayList<Schedule> pre_schedules;
    private DBOpenHelper dbOpenHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        dbOpenHelper=new DBOpenHelper(this);
        username=getIntent().getStringExtra("username");

        init_toolbar();
        init_data();
        init_recycler();

    }

    public void init_data(){
        //pre_schedules=new ArrayList<>();
        schedules=new ArrayList<>();
        schedules=dbOpenHelper.getAllschedules();
//        for(Schedule schedule:schedules){
//            System.out.println(schedule.toString());
//        }
        //Collections.sort(schedules,new SortByDate());
//        System.out.println("*******************************");
        Collections.sort(schedules,new SortByDate());
//        for(Schedule schedule:schedules){
//            System.out.println(schedule.toString());
//        }
    }

    public void init_recycler(){
        recyclerView = findViewById(R.id.rv_timeline);
        // 线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TimelineActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 用于描述item的适配器
        recyclerAdapterTimeline = new RecyclerAdapterTimeline(schedules);

        recyclerView.setAdapter(recyclerAdapterTimeline);
//        DividerItemDecoration mDivider = new
//                DividerItemDecoration(TimelineActivity.this,DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(mDivider);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        recyclerAdapterTimeline.setOnItemClickListener(this);
    }

    public void init_toolbar(){
        toolbar=findViewById(R.id.toolbar_timeline);
        setSupportActionBar(toolbar);

        //使用actionbar自带的返回键图片
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使用actionbar，自定义返回键图片
        actionBar.setHomeAsUpIndicator(R.drawable.ic_calender_main);

        //使用toolbar自定义返回键图片
        //toolbar.setNavigationIcon(R.drawable.ic_addbox);

        //actioBar设置标题
        actionBar.setTitle("1");
        //actionBar设置子标题
//        actionBar.setSubtitle("2");

        //toolbar设置标题
        toolbar.setTitle("全部日程");
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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case R.id.action_back:
                intent=new Intent(TimelineActivity.this,MainActivity.class);
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


    @Override
    public void onItemClick(View view, int position) {
        String id=recyclerAdapterTimeline.getSchedules().get(position).getId();
        Intent intent0=new Intent(TimelineActivity.this, ScheduleInforActivity.class);
        intent0.putExtra("id",id);
        intent0.putExtra("username",username);

        startActivity(intent0);
    }

    class SortByDate implements Comparator<Schedule>{

        public boolean comparebydate(Schedule schedule1,Schedule schedule2){
            if(Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule1.getRegisterdate()))>Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule2.getRegisterdate()))){
                return true;
            }else{
                if((Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule1.getRegisterdate()))==Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule2.getRegisterdate())))&&(Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule1.getRegisterdate()))>Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule2.getRegisterdate())))){
                    return true;
                }else{
                    if((Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule1.getRegisterdate()))==Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule2.getRegisterdate())))&&(Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule1.getRegisterdate()))==Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule2.getRegisterdate())))&&(Integer.parseInt(ContentUtils.From_Date_Get_Day(schedule1.getRegisterdate()))>Integer.parseInt(ContentUtils.From_Date_Get_Day(schedule2.getRegisterdate())))){
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public int compare(Schedule schedule1, Schedule schedule2) {
            if(comparebydate(schedule1,schedule2)){
                return 1;
            }
            return -1;
        }
    }

    class SortByScheduleDate implements Comparator<Schedule>{

        public boolean comparebydate(Schedule schedule1,Schedule schedule2){
            if(Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule1.getScheduledate()))>Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule2.getScheduledate()))){
                return true;
            }else{
                if((Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule1.getScheduledate()))==Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule2.getScheduledate())))&&(Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule1.getScheduledate()))>Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule2.getScheduledate())))){
                    return true;
                }else{
                    if((Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule1.getScheduledate()))==Integer.parseInt(ContentUtils.From_Date_Get_Year(schedule2.getScheduledate())))&&(Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule1.getScheduledate()))==Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule2.getScheduledate())))&&(Integer.parseInt(ContentUtils.From_Date_Get_Day(schedule1.getScheduledate()))>Integer.parseInt(ContentUtils.From_Date_Get_Day(schedule2.getScheduledate())))){
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public int compare(Schedule schedule1, Schedule schedule2) {
            if(comparebydate(schedule1,schedule2)){
                return 1;
            }
            return -1;
        }
    }


}
