package com.example.finalwork2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.finalwork2.Database.DBOpenHelper;
import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.JavaClass.Schedule;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;
import com.example.finalwork2.Utils.Gua;

import java.util.ArrayList;

public class SCweekFragment extends Fragment implements View.OnClickListener {

    private NowDateTime nowDateTime=new NowDateTime();
    private Gua mGua1;
//    private Button play;
//    private Button resele;
//    private Button dao;
    private View view;
    private SeekBar seekBar_mon,seekBar_tues,seekBar_wed,seekBar_thu,seekBar_fri,seekBar_sat,seekBar_sun;
    private TextView tv_mon_per,tv_tues_per,tv_wed_per,tv_thur_per,tv_fri_per,tv_sat_per,tv_sun_per;
    private int monday_num=0,tuesday_num=0,wednesday_num=0,thursday_num=0,friday_num=0,saturday_num=0,sunday_num=0;
    private int monday_numf=0,tuesday_numf=0,wednesday_numf=0,thursday_numf=0,friday_numf=0,saturday_numf=0,sunday_numf=0;
    private ArrayList<Schedule> schedules;
    private DBOpenHelper dbOpenHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_scweek, container, false);
        dbOpenHelper=new DBOpenHelper(getContext());

        init_view();
        init_data_about_seekbar();
        init_data();


        return view;
    }
    public void init_view(){
        mGua1 =(Gua) view. findViewById(R.id.Circle);
        mGua1.setTargetPercent(0);
//        play=(Button) view.findViewById(R.id.play);
//        resele=(Button) view.findViewById(R.id.resele);
//        dao=(Button) view.findViewById(R.id.dao);

        tv_sun_per=view.findViewById(R.id.tv_sun_percent);
        tv_mon_per=view.findViewById(R.id.tv_mon_percent);
        tv_tues_per=view.findViewById(R.id.tv_thus_percent);
        tv_wed_per=view.findViewById(R.id.tv_wed_percent);
        tv_thur_per=view.findViewById(R.id.tv_thur_percent);
        tv_fri_per=view.findViewById(R.id.tv_fri_percent);
        tv_sat_per=view.findViewById(R.id.tv_sat_percent);

        seekBar_sun=view.findViewById(R.id.sb_sun);
        seekBar_mon=view.findViewById(R.id.sb_mon);
        seekBar_tues=view.findViewById(R.id.sb_thus);
        seekBar_wed=view.findViewById(R.id.sb_wed);
        seekBar_thu=view.findViewById(R.id.sb_thur);
        seekBar_fri=view.findViewById(R.id.sb_fri);
        seekBar_sat=view.findViewById(R.id.sb_sat);
        seekBar_sun.setProgress(0);
        seekBar_mon.setProgress(0);
        seekBar_tues.setProgress(0);
        seekBar_wed.setProgress(0);
        seekBar_thu.setProgress(0);
        seekBar_fri.setProgress(0);
        seekBar_sat.setProgress(0);

        seekBar_sun.setEnabled(false);
        seekBar_mon.setEnabled(false);
        seekBar_tues.setEnabled(false);
        seekBar_wed.setEnabled(false);
        seekBar_thu.setEnabled(false);
        seekBar_fri.setEnabled(false);
        seekBar_sat.setEnabled(false);


//        play.setOnClickListener(this);
//        resele.setOnClickListener(this);
//        dao.setOnClickListener(this);
    }

    public void init_data_about_seekbar(){
        schedules=new ArrayList<>();
        schedules=dbOpenHelper.getAllschedules();
        for(int index=0;index<schedules.size();index++){
            String scheduledate=schedules.get(index).getScheduledate();
            int year= Integer.parseInt(ContentUtils.From_Date_Get_Year(scheduledate));
            int month=Integer.parseInt(ContentUtils.From_Date_Get_Month(scheduledate));
            int day=Integer.parseInt(ContentUtils.From_Date_Get_Day(scheduledate));
            boolean finish=schedules.get(index).isFinish();
            String weekday=ContentUtils.CaculateWeekday(year,month,day);
            switch (weekday){
                case "星期一":
                    monday_num++;
                    if(finish){
                        monday_numf++;
                    }
                    break;
                case "星期二":
                    tuesday_num++;
                    if(finish){
                        tuesday_numf++;
                    }
                    break;
                case "星期三":
                    wednesday_num++;
                    if(finish){
                        wednesday_numf++;
                    }
                    break;
                case "星期四":
                    thursday_num++;
                    if(finish){
                        thursday_numf++;
                    }
                    break;
                case "星期五":
                    friday_num++;
                    if(finish){
                        friday_numf++;
                    }
                    break;
                case "星期六":
                    saturday_num++;
                    if(finish){
                        saturday_numf++;
                    }
                    break;
                case "星期日":
                    sunday_num++;
                    if(finish){
                        sunday_numf++;
                    }
                    break;
            }
        }
    }

    public void init_data(){
        if(monday_num!=0){
            seekBar_mon.setProgress((monday_numf*100)/monday_num);
            tv_mon_per.setText((monday_numf*100)/monday_num+"%");
        }else{
            seekBar_mon.setProgress(0);
            tv_mon_per.setText(0+"%");
        }
        if(tuesday_num!=0){
            seekBar_tues.setProgress((tuesday_numf*100)/tuesday_num);
            tv_tues_per.setText((tuesday_numf*100)/tuesday_num+"%");
        }else{
            seekBar_tues.setProgress(0);
            tv_tues_per.setText("0%");
        }
        if(wednesday_num!=0){
            seekBar_wed.setProgress((wednesday_numf*100)/wednesday_num);
            tv_wed_per.setText((wednesday_numf*100)/wednesday_num+"%");
        }else{
            seekBar_wed.setProgress(0);
            tv_wed_per.setText("0%");
        }
        if(thursday_num!=0){
            seekBar_thu.setProgress((thursday_numf*100)/thursday_num);
            tv_thur_per.setText((thursday_numf*100)/thursday_num+"%");
        }else{
            seekBar_thu.setProgress(0);
            tv_thur_per.setText("0%");
        }
        if(friday_num!=0){
            seekBar_fri.setProgress((friday_numf*100)/friday_num);
            tv_fri_per.setText((friday_numf*100)/friday_num+"%");
        }else{
            seekBar_fri.setProgress(0);
            tv_fri_per.setText("0%");
        }
        if(saturday_num!=0){
            seekBar_sat.setProgress((saturday_numf*100)/saturday_num);
            tv_sat_per.setText((saturday_numf*100)/saturday_num+"%");
        }else{
            seekBar_sat.setProgress(0);
            tv_sat_per.setText("0%");
        }
        if(sunday_num!=0){
            seekBar_sun.setProgress((sunday_numf*100)/sunday_num);
            tv_sun_per.setText((sunday_numf*100)/sunday_num+"%");
        }else{
            seekBar_sun.setProgress(0);
            tv_sun_per.setText("0%");
        }

        float target=((monday_numf+tuesday_numf+wednesday_numf+thursday_numf+friday_numf+saturday_numf+sunday_numf)*100)/(monday_num+tuesday_num+wednesday_num+thursday_num+friday_num+saturday_num+sunday_num);
        mGua1.setTargetPercent(target);
        mGua1.invalidate();


    }

//    public void test(){
//        String date=nowDateTime.getNowDate();
//        int year= Integer.parseInt(ContentUtils.From_Date_Get_Year(date));
//        int month=Integer.parseInt(ContentUtils.From_Date_Get_Month(date));
//        int day=Integer.parseInt(ContentUtils.From_Date_Get_Day(date));
//        String weekday=ContentUtils.CaculateWeekday(year,month,day);
//        System.out.println(date);
//        System.out.println(year);
//        System.out.println(month);
//        System.out.println(day);
//        System.out.println(weekday);
//    }



    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.play:
//                float target=((monday_numf+tuesday_numf+wednesday_numf+thursday_numf+friday_numf+saturday_numf+sunday_numf)*100)/(monday_num+tuesday_num+wednesday_num+thursday_num+friday_num+saturday_num+sunday_num);
//                mGua1.setTargetPercent(target);
//                mGua1.invalidate();
//                break;
//            case R.id.resele:
//                //设置目标百分比为0
//                mGua1.setTargetPercent(0);
//                mGua1.invalidate();
//                break;
//            case R.id.dao:
//                //设置目标百分比为100
//                mGua1.setTargetPercent(0);
//                mGua1.invalidate();
//                break;
//        }
    }

}
