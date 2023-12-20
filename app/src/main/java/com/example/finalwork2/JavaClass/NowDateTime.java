package com.example.finalwork2.JavaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NowDateTime {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar=Calendar.getInstance();

    public String getT(int sign){
        if(sign>0 && sign<10){
            return "0"+String.valueOf(sign);
        }
        else return String.valueOf(sign);
    }
    private String mYear ;
    private String mMonth ; //获取日期的月
    private String mDay ; //获取日期的天
    private String nowDate;

    public NowDateTime(){
        mYear = String.valueOf(calendar.get(Calendar.YEAR));
        mMonth = getT(calendar.get(Calendar.MONTH) + 1); //获取日期的月
        mDay = getT(calendar.get(Calendar.DAY_OF_MONTH)); //获取日期的天
        nowDate=mYear+"年"+mMonth+"月"+mDay+"日";
    }

    public int getDays(String pastdate) throws ParseException {
        return (int)((simpleDateFormat.parse(nowDate).getTime()-simpleDateFormat.parse(pastdate).getTime())/(24*60*60*1000));
    }
    public String getNowDate(){
        return nowDate;
    }
}
