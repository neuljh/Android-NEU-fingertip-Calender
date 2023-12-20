package com.example.finalwork2.Popwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalwork2.R;

public class Schedule_popActivity extends Activity implements View.OnClickListener {
    private TextView tv_editdate,tv_finish,tv_scheduledate,tv_cate,tv_reminddate,tv_priority,tv_starttime,tv_endtime;
    private Button bt_cancel,bt_confirm;
    private String title,date,sign;
    private boolean finish;
    private Intent intent;
    private String schedule_date,cate,remind_time,priority,start_time,end_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_pop);

        init_view();
        init_data();
        set_view();
    }

    public void init_view(){
        tv_editdate=findViewById(R.id.tv_schedule_pop_editdate_infor);
        tv_finish=findViewById(R.id.tv_schedule_pop_finish_infor);
        tv_scheduledate=findViewById(R.id.tv_schedule_pop_infordate_infor);
        tv_cate=findViewById(R.id.tv_schedule_pop_cate_infor);
        tv_reminddate=findViewById(R.id.tv_schedule_pop_reminddate_infor);
        tv_priority=findViewById(R.id.tv_schedule_pop_priority_infor);
        tv_starttime=findViewById(R.id.tv_schedule_pop_starttime_infor);
        tv_endtime=findViewById(R.id.tv_schedule_pop_endtime_infor);

        bt_cancel=findViewById(R.id.bt_si_cancel);
        bt_confirm=findViewById(R.id.bt_si_confirm);
        bt_confirm.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    public void init_data(){
        intent=getIntent();
        title=intent.getStringExtra("title");
        date=intent.getStringExtra("date");
        finish=intent.getBooleanExtra("sign",false);
        if(finish==false){
            sign="未完成";
        }else{
            sign="已完成";
        }

        schedule_date=intent.getStringExtra("scheduledate");
        cate=intent.getStringExtra("category");
        remind_time=intent.getStringExtra("remindtime");
        priority=intent.getStringExtra("priority");
        start_time=intent.getStringExtra("starttime");
        end_time=intent.getStringExtra("endtime");

        tv_editdate.setText(date);
        tv_finish.setText(sign);
        tv_scheduledate.setText(schedule_date);
        tv_cate.setText(cate);
        tv_reminddate.setText(remind_time);
        tv_priority.setText(priority);
        tv_starttime.setText(start_time);
        tv_endtime.setText(end_time);

    }

    public void set_view(){
        /** 设置宽度为屏幕的0.9*/
        WindowManager windowManager = getWindowManager();
        /* 获取屏幕宽、高 */
        Display display = windowManager.getDefaultDisplay();
        /* 获取对话框当前的参数值 */
        WindowManager.LayoutParams p = getWindow().getAttributes();
        /* 宽度设置为屏幕的1 */
        p.width = (int) (display.getWidth() * 0.7);
        /* 设置透明度,0.0为完全透明，1.0为完全不透明 */
        p.alpha = 0.85f;
        /* 设置布局参数 */
        getWindow().setAttributes(p);

//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);

        /* 设置点击弹框外部不可消失 */
        setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_si_cancel:
                finish();
                break;
            case R.id.bt_si_confirm:
                finish();
                break;
        }
    }
}
