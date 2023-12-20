package com.example.finalwork2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.finalwork2.Database.DBOpenHelper;
import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.JavaClass.Schedule;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;
import com.example.finalwork2.Utils.Gua;

import java.util.ArrayList;


public class SCyearFragment extends Fragment {
    private View view;
    private Gua gua_year;
    private Gua gua_1,gua_2,gua_3,gua_4,gua_5,gua_6,gua_7,gua_8,gua_9,gua_10,gua_11,gua_12;
    private int num1=0,num2=0,num3=0,num4=0,num5=0,num6=0,num7=0,num8=0,num9=0,num10=0,num11=0,num12=0;
    private int numf1=0,numf2=0,numf3=0,numf4=0,numf5=0,numf6=0,numf7=0,numf8=0,numf9=0,numf10=0,numf11=0,numf12=0;
    private NowDateTime nowDateTime=new NowDateTime();
    private ArrayList<Schedule> schedules;
    private DBOpenHelper dbOpenHelper;
    private TextView tv_year;
    private String date,year,month,day,schedule_date;
    private int year_dayf=0,year_day=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_scyear, container, false);
        dbOpenHelper=new DBOpenHelper(getContext());

        init_view();
        init_data();


        return view;
    }

    public void init_view(){
        tv_year=view.findViewById(R.id.tv_year);
        gua_year=view.findViewById(R.id.gua_year);
        gua_1=view.findViewById(R.id.gua_1m);
        gua_2=view.findViewById(R.id.gua_2m);
        gua_3=view.findViewById(R.id.gua_3m);
        gua_4=view.findViewById(R.id.gua_4m);
        gua_5=view.findViewById(R.id.gua_5m);
        gua_6=view.findViewById(R.id.gua_6m);
        gua_7=view.findViewById(R.id.gua_7m);
        gua_8=view.findViewById(R.id.gua_8m);
        gua_9=view.findViewById(R.id.gua_9m);
        gua_10=view.findViewById(R.id.gua_10m);
        gua_11=view.findViewById(R.id.gua_11m);
        gua_12=view.findViewById(R.id.gua_12m);
    }

    public void set_gua(int num,int numf,Gua gua){
        if(num!=0){
            gua.setTargetPercent((numf*100)/num);
        }else{
            gua.setTargetPercent(0);
        }
    }

    public void init_data(){
        date=nowDateTime.getNowDate();
        year= ContentUtils.From_Date_Get_Year(date);
        tv_year.setText("公元"+year+"年");
        schedules=new ArrayList<>();
        schedules=dbOpenHelper.getAllschedules();

        for(int index=0;index<schedules.size();index++){
            if(ContentUtils.From_Date_Get_Year(schedules.get(index).getRegisterdate()).equals(String.valueOf(year))){
                year_day++;
                if(schedules.get(index).isFinish()){
                    year_dayf++;
                }
            }
        }
        if(year_day!=0){
            gua_year.setTargetPercent((year_dayf*100)/year_day);
        }else{
            gua_year.setTargetPercent(0);
        }

        for(int index=0;index<schedules.size();index++){
            schedule_date=schedules.get(index).getRegisterdate();
            if(ContentUtils.From_Date_Get_Year(schedules.get(index).getRegisterdate()).equals(String.valueOf(year))){
                switch (Integer.parseInt(ContentUtils.From_Date_Get_Month(schedule_date))){
                    case 1:
                        num1++;
                        if(schedules.get(index).isFinish()){
                            numf1++;
                        }
                        break;
                    case 2:
                        num2++;
                        if(schedules.get(index).isFinish()){
                            numf2++;
                        }
                        break;
                    case 3:
                        num3++;
                        if(schedules.get(index).isFinish()){
                            numf3++;
                        }
                        break;
                    case 4:
                        num4++;
                        if(schedules.get(index).isFinish()){
                            numf4++;
                        }
                        break;
                    case 5:
                        num5++;
                        if(schedules.get(index).isFinish()){
                            numf5++;
                        }
                        break;
                    case 6:
                        num6++;
                        if(schedules.get(index).isFinish()){
                            numf6++;
                        }
                        break;
                    case 7:
                        num7++;
                        if(schedules.get(index).isFinish()){
                            numf7++;
                        }
                        break;
                    case 8:
                        num8++;
                        if(schedules.get(index).isFinish()){
                            numf8++;
                        }
                        break;
                    case 9:
                        num9++;
                        if(schedules.get(index).isFinish()){
                            numf9++;
                        }
                        break;
                    case 10:
                        num10++;
                        if(schedules.get(index).isFinish()){
                            numf10++;
                        }
                        break;
                    case 11:
                        num11++;
                        if(schedules.get(index).isFinish()){
                            numf11++;
                        }
                        break;
                    case 12:
                        num12++;
                        if(schedules.get(index).isFinish()){
                            numf12++;
                        }
                        break;
                }
            }
        }
        set_gua(num1,numf1,gua_1);
        set_gua(num2,numf2,gua_2);
        set_gua(num3,numf3,gua_3);
        set_gua(num4,numf4,gua_4);
        set_gua(num5,numf5,gua_5);
        set_gua(num6,numf6,gua_6);
        set_gua(num7,numf7,gua_7);
        set_gua(num8,numf8,gua_8);
        set_gua(num9,numf9,gua_9);
        set_gua(num10,numf10,gua_10);
        set_gua(num11,numf11,gua_11);
        set_gua(num12,numf12,gua_12);
    }



}
