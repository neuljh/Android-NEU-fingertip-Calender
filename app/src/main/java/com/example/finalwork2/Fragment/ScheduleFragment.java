package com.example.finalwork2.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalwork2.Activity.ScheduleInforActivity;
import com.example.finalwork2.Activity.TimelineActivity;
import com.example.finalwork2.Database.DBOpenHelper;
import com.example.finalwork2.Database.DBOpenHelperQQ;
import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.JavaClass.QQ;
import com.example.finalwork2.JavaClass.RecyclerAdapterSchedule;
import com.example.finalwork2.JavaClass.Schedule;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;
import com.example.finalwork2.WeekCalender.DateAdapter;
import com.example.finalwork2.WeekCalender.SpecialCalendar;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ScheduleFragment extends Fragment implements RecyclerAdapterSchedule.OnItemClickListener,View.OnClickListener, GestureDetector.OnGestureListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView iv_menu,iv_add,iv_category,iv_box;
    private ImageView iv_head;
    private TextView tv_name,tv_qqnum;
    private View view,dialog_view;
    private Intent intent;

    private RecyclerView recyclerView;
    private RecyclerAdapterSchedule recyclerAdapterSchedule;
    private ArrayList<Schedule> schedules;
    private ArrayList<Schedule> new_schedules;
    private NowDateTime nowDateTime=new NowDateTime();
    private String title;
    private EditText et_title;
    private DBOpenHelper dbOpenHelper;
    private boolean refresh=false;
    private String username=new String();
    private ArrayList<QQ> qqs;
    private DBOpenHelperQQ dbOpenHelperQQ;

    private String chosen_year,chosen_month,chosen_day,chosen_date;

    //日历部分声明
    private static String TAG = "MainActivity";
    private ViewFlipper flipper1 = null;
    private GridView gridView = null;
    private GestureDetector gestureDetector = null;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private int week_c = 0;
    private int week_num = 0;
    private String currentDate = "";
    private DateAdapter dateAdapter;
    private int daysOfMonth = 0;
    private int dayOfWeek = 0;
    private int weeksOfMonth = 0;
    private SpecialCalendar sc = null;
    private boolean isLeapyear = false;
    private int selectPostion = 0;
    private String dayNumbers[] = new String[7];
    private TextView tvDate;
    private int currentYear;
    private int currentMonth;
    private int currentWeek;
    private int currentDay;
    private int currentNum;
    //日历声明

    public ScheduleFragment(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date);
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        currentYear = year_c;
        currentMonth = month_c;
        currentDay = day_c;
        sc = new SpecialCalendar();
        getCalendar(year_c, month_c);
        week_num = getWeeksOfMonth();
        currentNum = week_num;
        if (dayOfWeek == 7) {
            week_c = currentDay / 7 + 1;
        } else {
            if (currentDay <= (7 - dayOfWeek)) {
                week_c = 1;
            } else {
                if ((currentDay - (7 - dayOfWeek)) % 7 == 0) {
                    week_c = (currentDay - (7 - dayOfWeek)) / 7 + 1;
                } else {
                    week_c = (currentDay - (7 - dayOfWeek)) / 7 + 2;
                }
            }
        }
        currentWeek = week_c;
        getCurrent();
    }



    public void init_view(){
        drawerLayout=view.findViewById(R.id.schedule_drawer_layout);
        navigationView=view.findViewById(R.id.navigation_view);
        iv_menu=view.findViewById(R.id.schedule_iv_menu);

        iv_add=view.findViewById(R.id.schedule_iv_add);
        iv_add.setOnClickListener(this);

        iv_category=view.findViewById(R.id.schedule_iv_cate);
        iv_category.setOnClickListener(this);

        iv_box=view.findViewById(R.id.schedule_iv_box);
        iv_box.setOnClickListener(this);

        //获取MainActivity的内容
        Bundle bundle=getArguments();
        refresh=bundle.getBoolean("refresh");
        username=bundle.getString("username");

//        View dialogview=LayoutInflater.from(getContext()).inflate(R.layout.add_dialog,null);
//        et_title=dialog_view.findViewById(R.id.et_message);


//        recyclerView=view.findViewById(R.id.schedule_rv);

    }

    public void init_recycler(){
        recyclerView = view.findViewById(R.id.schedule_rv);
        // 线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // 用于描述item的适配器
        recyclerAdapterSchedule = new RecyclerAdapterSchedule(schedules);
        recyclerAdapterSchedule.notifyDataSetChanged();

        recyclerView.setAdapter(recyclerAdapterSchedule);
        DividerItemDecoration mDivider = new
                DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        recyclerAdapterSchedule.setOnItemClickListener(this);
    }

    private void init_schedules(){
        String init_date=tvDate.getText().toString();
        schedules=new ArrayList<>();
        if(dbOpenHelper.getAllschedules().size()==0){
            addItem("默认标题");
        }
        for(int index=0;index<dbOpenHelper.getAllschedules().size();index++){
            if(init_date.equals(dbOpenHelper.getAllschedules().get(index).getRegisterdate())){
                schedules.add(dbOpenHelper.getAllschedules().get(index));
            }
        }
        //schedules=dbOpenHelper.getAllschedules();

//        schedules.add(new Schedule("schedule1",false,nowDateTime.getNowDate(),nowDateTime.getNowDate(), ContentUtils.DEFAULT_CATEGORY,ContentUtils.DEFAULT_REMIND_TIME,ContentUtils.DEFAULT_PRIORITY,ContentUtils.DEFAULT_START_TIME,ContentUtils.DEFAULT_END_TIME,ContentUtils.DEFAULT_TIPS));
//        schedules.add(new Schedule("schedule2",true,nowDateTime.getNowDate(),nowDateTime.getNowDate(), ContentUtils.DEFAULT_CATEGORY,ContentUtils.DEFAULT_REMIND_TIME,ContentUtils.DEFAULT_PRIORITY,ContentUtils.DEFAULT_START_TIME,ContentUtils.DEFAULT_END_TIME,ContentUtils.DEFAULT_TIPS));
    }

    public void main(){
        String qqnum="";
        String image_url="";
        String nickname="";
        String app_id="";
        qqs=dbOpenHelperQQ.getAllQQs();
        for(QQ qq:qqs){
            if(username.equals(qq.getUsername())){
                qqnum=qq.getQq();
                image_url=qq.getImage_url();
                nickname=qq.getNickname();
                app_id=qq.getApp_id();
            }
        }

        View headview=navigationView.getHeaderView(0);
        iv_head=headview.findViewById(R.id.userHeadImg);
        iv_head.setImageResource(R.mipmap.ic_launcher_round);
        tv_name=headview.findViewById(R.id.nickName);
        tv_name.setText(nickname);
        tv_qqnum=headview.findViewById(R.id.tv_qqnum);
        tv_qqnum.setText(qqnum);


        //Glide设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(100);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(20, 20);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(getContext())
                .load(image_url)
                .error(getResources().getDrawable(R.mipmap.qq_left))
                .apply(options)
                .into(iv_head);



        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
//                    case R.id.item_calender_todayweather:
//                            startActivity(new Intent(getActivity(), UpgradeDialogActivity.class));
//                            break;
                    case R.id.item_all_schedule:
                        intent=new Intent(getActivity(), TimelineActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        break;
//                    case R.id.item_personal://个人
//                        Toast.makeText(getContext(),"个人页面",Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(navigationView);
//                        break;
//                    case R.id.item_setting://设置
//                        Toast.makeText(getContext(),"设置页面",Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(navigationView);
//                        break;
                }
                return true;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_schedule, container, false);
        dbOpenHelper=new DBOpenHelper(getContext());
        dbOpenHelperQQ=new DBOpenHelperQQ(getContext());
//        dialog_view=inflater.inflate(R.layout.add_dialog,container,false);
        //get_Notify();
        init_calender();

        init_view();

        main();

        init_schedules();

        init_recycler();

        return view;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }




    public void tip_add(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.drawable.ic_daojishi);
        builder.setTitle("请输入日程名称");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_dialog, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
        final EditText title = (EditText)view.findViewById(R.id.et_adddialog_title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String a = title.getText().toString().trim();
                addItem(a);
                recyclerAdapterSchedule.notifyItemRangeChanged(0,recyclerAdapterSchedule.getItemCount());
                refresh=true;
//                recyclerAdapterSchedule.notifyDataSetChanged();
//                Toast.makeText(getContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }



    @Override
    public void onItemClick(View view, int position) {
        String id=recyclerAdapterSchedule.getSchedules().get(position).getId();
        boolean sign=recyclerAdapterSchedule.getSchedules().get(position).isFinish();
        String title=recyclerAdapterSchedule.getSchedules().get(position).getTitle();
        String registerdate=recyclerAdapterSchedule.getSchedules().get(position).getRegisterdate();
        String scheduledate=recyclerAdapterSchedule.getSchedules().get(position).getScheduledate();
        String category=recyclerAdapterSchedule.getSchedules().get(position).getCategory();
        String remindtime=recyclerAdapterSchedule.getSchedules().get(position).getRemindtime();
        String priority=recyclerAdapterSchedule.getSchedules().get(position).getPriority();
        String starttime=recyclerAdapterSchedule.getSchedules().get(position).getStarttime();
        String endtime=recyclerAdapterSchedule.getSchedules().get(position).getEndtime();
        String tips=recyclerAdapterSchedule.getSchedules().get(position).getTips();


        Intent intent0=new Intent(getActivity(), ScheduleInforActivity.class);
        intent0.putExtra("sign",sign);
        intent0.putExtra("title",title);
        intent0.putExtra("date",registerdate);

        intent0.putExtra("scheduledate",scheduledate);
        intent0.putExtra("category",category);
        intent0.putExtra("remindtime",remindtime);
        intent0.putExtra("priority",priority);
        intent0.putExtra("starttime",starttime);
        intent0.putExtra("endtime",endtime);
        intent0.putExtra("tips",tips);
        intent0.putExtra("id",id);

        intent0.putExtra("username",username);

        startActivity(intent0);
    }
//    private String title;
//    private boolean finish;
//    private String registerdate;
//    private String scheduledate;
//    private String category;
//    private String remindtime;
//    private String priority;
//    private String starttime;
//    private String endtime;
//    private String tips;
//public void addItem(){
//    dbOpenHelper.add(new Schedule(title,finish,registerdate,nowDateTime.getNowDate(), ContentUtils.DEFAULT_CATEGORY,ContentUtils.DEFAULT_REMIND_TIME,ContentUtils.DEFAULT_PRIORITY,ContentUtils.DEFAULT_START_TIME,ContentUtils.DEFAULT_END_TIME,ContentUtils.DEFAULT_TIPS));
//}

    public void addItem(String title){
        dbOpenHelper.add(title,"true",nowDateTime.getNowDate(),nowDateTime.getNowDate(), ContentUtils.DEFAULT_CATEGORY,ContentUtils.DEFAULT_REMIND_TIME,ContentUtils.DEFAULT_PRIORITY,ContentUtils.DEFAULT_START_TIME,ContentUtils.DEFAULT_END_TIME,ContentUtils.DEFAULT_TIPS);
        recyclerAdapterSchedule.addItem(recyclerAdapterSchedule.getItemCount(),title,true,nowDateTime.getNowDate());
        recyclerAdapterSchedule.notifyDataSetChanged();
    }

//    public void updata_data(){
//        new_schedules=new ArrayList<>();
//        new_schedules=dbOpenHelper.getAllschedules();
//        recyclerAdapterSchedule.updatedata(new_schedules);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.schedule_iv_add:
                tip_add();
                Toast.makeText(getActivity(), "添加成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //这里是自定义日历组件
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int gvFlag = 0;
        if (e1.getX() - e2.getX() > 80) {
            addGridView();
            currentWeek++;
            getCurrent();
            dateAdapter = new DateAdapter(getContext(), currentYear, currentMonth,currentWeek, currentWeek == 1 ? true : false);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);

            chosen_year=String.valueOf(dateAdapter.getCurrentYear(selectPostion));
            chosen_month=nowDateTime.getT(dateAdapter.getCurrentMonth(selectPostion));
            chosen_day=nowDateTime.getT(Integer.parseInt(dayNumbers[selectPostion]));
            chosen_date=chosen_year+"年"+chosen_month+"月"+chosen_day+"日";
            tvDate.setText(chosen_date);
            tvDate.setTextColor(getResources().getColor(R.color.white_bg));

            //tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "年"+ dateAdapter.getCurrentMonth(selectPostion) + "月"+ dayNumbers[selectPostion] + "日");
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            dateAdapter.setSeclection(selectPostion);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_left_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_left_out));
            this.flipper1.showNext();
            flipper1.removeViewAt(0);
            return true;

        } else if (e1.getX() - e2.getX() < -80) {
            addGridView();
            currentWeek--;
            getCurrent();
            dateAdapter = new DateAdapter(getContext(), currentYear, currentMonth,currentWeek, currentWeek == 1 ? true : false);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);

            chosen_year=String.valueOf(dateAdapter.getCurrentYear(selectPostion));
            chosen_month=nowDateTime.getT(dateAdapter.getCurrentMonth(selectPostion));
            chosen_day=nowDateTime.getT(Integer.parseInt(dayNumbers[selectPostion]));
            chosen_date=chosen_year+"年"+chosen_month+"月"+chosen_day+"日";
            tvDate.setText(chosen_date);
            tvDate.setTextColor(getResources().getColor(R.color.white_bg));

            //tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "年"+ dateAdapter.getCurrentMonth(selectPostion) + "月"+ dayNumbers[selectPostion] + "日");
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            dateAdapter.setSeclection(selectPostion);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_right_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_right_out));
            this.flipper1.showPrevious();
            flipper1.removeViewAt(0);
            return true;
        }
        return false;
    }

    public int getWeeksOfMonth(int year, int month) {
        int preMonthRelax = 0;
        int dayFirst = getWhichDayOfWeek(year, month);
        int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
        if (dayFirst != 7) {
            preMonthRelax = dayFirst;
        }
        if ((days + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (days + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (days + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;

    }


    public int getWhichDayOfWeek(int year, int month) {
        return sc.getWeekdayOfMonth(year, month);

    }


    public int getLastDayOfWeek(int year, int month) {
        return sc.getWeekDayOfLastMonth(year, month,
                sc.getDaysOfMonth(isLeapyear, month));
    }

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);
        dayOfWeek = sc.getWeekdayOfMonth(year, month);
    }

    public int getWeeksOfMonth() {
        int preMonthRelax = 0;
        if (dayOfWeek != 7) {
            preMonthRelax = dayOfWeek;
        }
        if ((daysOfMonth + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;
    }

    public void init_calender(){
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        tvDate.setText(year_c + "年" + nowDateTime.getT(month_c) + "月" + nowDateTime.getT(day_c) + "日");
        tvDate.setTextColor(getResources().getColor(R.color.white_bg));
        gestureDetector = new GestureDetector(this);
        flipper1 = (ViewFlipper) view.findViewById(R.id.flipper1);
        dateAdapter = new DateAdapter(getContext(), currentYear, currentMonth,currentWeek, currentWeek == 1 ? true : false);
        addGridView();
        dayNumbers = dateAdapter.getDayNumbers();
        gridView.setAdapter(dateAdapter);
        selectPostion = dateAdapter.getTodayPosition();
        gridView.setSelection(selectPostion);
        flipper1.addView(gridView, 0);
    }

    public void getCurrent() {
        if (currentWeek > currentNum) {
            if (currentMonth + 1 <= 12) {
                currentMonth++;
            } else {
                currentMonth = 1;
                currentYear++;
            }
            currentWeek = 1;
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
        } else if (currentWeek == currentNum) {
            if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
            } else {
                if (currentMonth + 1 <= 12) {
                    currentMonth++;
                } else {
                    currentMonth = 1;
                    currentYear++;
                }
                currentWeek = 1;
                currentNum = getWeeksOfMonth(currentYear, currentMonth);
            }

        } else if (currentWeek < 1) {
            if (currentMonth - 1 >= 1) {
                currentMonth--;
            } else {
                currentMonth = 12;
                currentYear--;
            }
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
            currentWeek = currentNum - 1;
        }
    }

    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        gridView = new GridView(getContext());
        gridView.setNumColumns(7);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i(TAG, "day:" + dayNumbers[position]);
                selectPostion = position;
                dateAdapter.setSeclection(position);
                dateAdapter.notifyDataSetChanged();
                chosen_year=String.valueOf(dateAdapter.getCurrentYear(selectPostion));
                chosen_month=nowDateTime.getT(dateAdapter.getCurrentMonth(selectPostion));
                chosen_day=nowDateTime.getT(Integer.parseInt(dayNumbers[position]));
                chosen_date=chosen_year+"年"+chosen_month+"月"+chosen_day+"日";
                tvDate.setText(chosen_date);
                tvDate.setTextColor(getResources().getColor(R.color.white_bg));

                schedules=new ArrayList<>();
                new_schedules=new ArrayList<>();
                new_schedules=dbOpenHelper.getAllschedules();
                for(int index=0;index<new_schedules.size();index++){
                    if(chosen_date.equals(new_schedules.get(index).getRegisterdate())){
                        schedules.add(new_schedules.get(index));
                    }
                }
                recyclerAdapterSchedule.updatedata(schedules);
//                if(recyclerView.getChildCount()>0){
//                    recyclerView.removeAllViews();
//                    recyclerAdapterSchedule.updatedata(schedules);
//                }
                //不懂？？？
//                new_schedules=new ArrayList<>();
//                for(int index=0;index<schedules.size();index++){
//                    if(chosen_date.equals(schedules.get(index).getRegisterdate())){
//                        new_schedules.add(schedules.get(index));
//                    }
//                }
//                recyclerAdapterSchedule.updatedata(new_schedules);
//                if(recyclerView.getChildCount()>0){
//                    //recyclerView.removeAllViews();
//                    recyclerAdapterSchedule.updatedata(new_schedules);
//                }

            }
        });
        gridView.setLayoutParams(params);
    }



}
