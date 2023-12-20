package com.example.finalwork2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.finalwork2.Database.DBOpenHelper;
import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.JavaClass.Schedule;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;
import com.example.finalwork2.Utils.Gua;

import java.util.ArrayList;


public class SCmonthFragment extends Fragment implements View.OnClickListener{
    private Gua mGua1,mGua2;
    private View view;
    private int monthday=0,monthday_schedule=0,month_total=0,month_finish=0;
    private CalendarView calendarView;
    private String date;
    private String month;
    private String year;
    private NowDateTime nowDateTime=new NowDateTime();
    private ArrayList<Schedule> schedules;
    private DBOpenHelper dbOpenHelper;
    private int[] schedule_day;
    private TextView tv_title,tv_amount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_scmonth, container, false);
        dbOpenHelper=new DBOpenHelper(getContext());


        init_view();
        init_data();



        return view;
    }

    public void init_view(){
        mGua1 =(Gua) view. findViewById(R.id.gua_monthf);
        mGua2=view.findViewById(R.id.gua_zhanbi);
        calendarView=view.findViewById(R.id.calendarView_month);
        tv_title=view.findViewById(R.id.tv_date_schedule);
        tv_amount=view.findViewById(R.id.tv_amount_schedule);
    }

    public void init_data(){
        date=nowDateTime.getNowDate();
        month= ContentUtils.From_Date_Get_Month(date);
        year=ContentUtils.From_Date_Get_Year(date);
        int int_year=Integer.parseInt(year);
        switch (Integer.parseInt(month)){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                monthday=31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                monthday=30;
                break;
            case 2:
                if((int_year%4==0&&int_year%100!=0)||int_year%400==0){
                    monthday=29;
                }else{
                    monthday=28;
                }
                break;
        }

        schedules=new ArrayList<>();
        schedules=dbOpenHelper.getAllschedules();
        schedule_day=new int[monthday];
        for(int index=0;index<monthday;index++){
            schedule_day[index]=0;
        }
        for(int index=0;index<schedules.size();index++){
            if(month.equals(ContentUtils.From_Date_Get_Month(schedules.get(index).getRegisterdate()))){
                String day=ContentUtils.From_Date_Get_Day(schedules.get(index).getRegisterdate());
                schedule_day[Integer.parseInt(day)-1]++;
                month_total++;
                boolean finish=schedules.get(index).isFinish();
                if(finish){
                    month_finish++;
                }
            }
        }
        for(int index=0;index<schedule_day.length;index++){
            if(schedule_day[index]>0){
                monthday_schedule++;
            }
        }
        if(month_total!=0){
            mGua1.setTargetPercent((int)(month_finish*100)/month_total);
        }else{
            mGua1.setTargetPercent(0);
        }
        mGua2.setTargetPercent((int)((monthday_schedule*100)/monthday));
        tv_amount.setText(String.valueOf(schedules.size()));



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String chosendate=year+"年"+nowDateTime.getT(month+1)+"月"+nowDateTime.getT(dayOfMonth)+"日";
                tv_title.setText(chosendate+"的日程数量");
                tv_amount.setText(String.valueOf(schedule_day[dayOfMonth-1]));
                Toast.makeText(getContext(),"你选择的是"+chosendate,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
