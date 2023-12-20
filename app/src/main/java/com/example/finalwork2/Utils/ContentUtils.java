package com.example.finalwork2.Utils;

import com.example.finalwork2.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ContentUtils {
    final public static String Schedule_Priority[]={"不重要不紧急","不重要紧急","重要紧急","重要且紧急"};
    final public static String Schedule_Category[]={"工作","生活","购物","旅行","学习"};
    final public static int Schedule_Priority_Number[]={1,2,3,4};
    final public static String Schedule_State[]={"创建","完成","逾期"};
    final public static String DEFAULT_CATEGORY="学习";
    final public static String DEFAULT_REMIND_TIME="9时0分";
    final public static String DEFAULT_PRIORITY="不重要不紧急";
    final public static String DEFAULT_START_TIME="8时0分";
    final public static String DEFAULT_END_TIME="22时0分";
    final public static String DEFAULT_TIPS="写下你对此日程的备注吧！";
    final public static String CLASS_WEEKS[]={"第1周","第2周","第3周","第4周","第5周","第6周","第7周","第8周","第9周","第10周","第11周","第12周","第13周","第14周","第15周","第16周","第17周","第18周","第19周","第20周"};
    final public static String DEFAULT_USERNAME="ljhanddx";
    final public static String DEFAULT_PASSWORD="20211120";
    final public static String lc_app_id="D1Hh3pfwL1LXvcmt7W6DD9Iy-gzGzoHsz";
    final public static String lc_app_key= "6EQg2RCngsRFHtWIWboH1fEn";
    final public static String lc_web="https://d1hh3pfw.lc-cn-n1-shared.com";
    // LeanCloud.initialize(this, "D1Hh3pfwL1LXvcmt7W6DD9Iy-gzGzoHsz", "6EQg2RCngsRFHtWIWboH1fEn", "https://d1hh3pfw.lc-cn-n1-shared.com");
    final public static ArrayList<Integer> imgs;
    static {
        imgs=new ArrayList<>();
        imgs.add(R.mipmap.pic01);
        imgs.add(R.mipmap.pic02);
        imgs.add(R.mipmap.pic03);
        imgs.add(R.mipmap.pic04);
        imgs.add(R.mipmap.pic05);
        imgs.add(R.mipmap.pic06);
        imgs.add(R.mipmap.pic07);
        imgs.add(R.mipmap.pic08);
        imgs.add(R.mipmap.pic09);
        imgs.add(R.mipmap.pic10);
        imgs.add(R.mipmap.pic11);
        imgs.add(R.mipmap.pic12);
        imgs.add(R.mipmap.pic13);
        imgs.add(R.mipmap.pic14);
        imgs.add(R.mipmap.pic15);
        imgs.add(R.mipmap.pic16);
        imgs.add(R.mipmap.pic17);
        imgs.add(R.mipmap.pic18);
        imgs.add(R.mipmap.pic19);
        imgs.add(R.mipmap.pic20);
        imgs.add(R.mipmap.pic21);
    };
    final public static HashMap<String,String> colors;
    static {
        colors=new HashMap<>();
        colors.put("1","#DCBB86FC");
        colors.put("2","#DCEA7BA8");
        colors.put("3","#DC12D4EC");
        colors.put("4","#DCF1D209");
        colors.put("5","#DC504D4D");
        colors.put("6","#DC00FFFF");
        colors.put("7","#DC00FF00");
        colors.put("8","#DC7FFF00");
        colors.put("9","#DC00C957");
        colors.put("10","#DC308014");
        colors.put("11","#DC2E8B57");
        colors.put("12","#DCBDFCC9");
        colors.put("13","#DC7adfb8");
        colors.put("14","#DCD50202");
        colors.put("15","#DC3700B3");
        colors.put("16","#DCEA7BA8");
        colors.put("17","#DCC52F6B");
        colors.put("18","#DC817676");
        colors.put("19","#DC3700B3");
        colors.put("20","#DCECBFD2");
    };
    final public static String get_nowtime(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }
    final public static String Caculate_days(String time1,String time2){
        int result = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date1=sdf.parse(time1);
            Date date2=sdf.parse(time2);


            //将Date类型转换为Calendar类型
            Calendar beforeCalendar = Calendar.getInstance();
            beforeCalendar.setTime(date1);
            Calendar afterCalendar = Calendar.getInstance();
            afterCalendar.setTime(date2);

            //获取日期的DayOfYear（这一天在是这一年的第多少天）
            int beforeDayOfYear = beforeCalendar.get(Calendar.DAY_OF_YEAR);
            int afterDayOfYear = afterCalendar.get(Calendar.DAY_OF_YEAR);

            //获取日期的年份
            int beforeYear = beforeCalendar.get(Calendar.YEAR);
            int afterYear = afterCalendar.get(Calendar.YEAR);

            if (beforeYear == afterYear) {
                //同一年
                result = afterDayOfYear - beforeDayOfYear;
            } else {
                //不同一年
                int timeDistance = 0;
                for (int i = beforeYear; i < afterYear; i++) {
                    if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                        //闰年
                        timeDistance += 366;
                    } else {
                        //不是闰年
                        timeDistance += 365;
                    }
                }
                result = timeDistance + (afterDayOfYear - beforeDayOfYear);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(result);
    }
    final public static String List_to_String(List<Integer> list){
        String res="";
        for(int index=0;index<list.size();index++){
            if(index==list.size()-1){
                res=res+list.get(index);
            }else{
                res=res+list.get(index)+",";
            }
        }
        return res;
    }
    final public static String From_Time_Get_Hour(String s){
        String res="";
        for(int index=0;index<s.length();index++){
            if(s.charAt(index)!='时'){
                res=res+s.charAt(index);
            }else{
                break;
            }
        }
        return res;
    }
    final public static String From_Time_Get_Min(String s){
        String res="";
        boolean sign=false;
        for(int index=0;index<s.length();index++){
            if(s.charAt(index)=='时'){
                sign=true;
                continue;
            }
            if(sign){
                if(s.charAt(index)!='分'){
                    res=res+s.charAt(index);
                }else{
                    break;
                }
            }
        }
        return res;
    }

    final public static String From_Date_Get_Year(String s){
        String res="";
        for(int index=0;index<s.length();index++){
            if(s.charAt(index)!='年'){
                res=res+s.charAt(index);
            }else{
                break;
            }
        }
        return res;
    }

    final public static String From_Date_Get_Month(String s){
        String res="";
        boolean sign=false;
        for(int index=0;index<s.length();index++){
            if(s.charAt(index)=='年'){
                sign=true;
                continue;
            }
            if(sign){
                if(s.charAt(index)!='月'){
                    res=res+s.charAt(index);
                }else{
                    break;
                }
            }
        }
        return res;
    }

    final public static String From_Date_Get_Day(String s){
        String res="";
        boolean sign=false;
        for(int index=0;index<s.length();index++){
            if(s.charAt(index)=='月'){
                sign=true;
                continue;
            }
            if(sign){
                if(s.charAt(index)!='日'){
                    res=res+s.charAt(index);
                }else{
                    break;
                }
            }
        }
        return res;
    }
    public final static String CaculateWeekday(int year, int month, int day){
        if(month==1||month==2){
            month=month+12;
            year--;
        }
        int week=(day+2*month+3*(month+1)/5+year+year/4-year/100+year/400+1)%7;
        String weekstr=new String();
        switch (week){
            case 1:
                weekstr="星期一";
                break;
            case 2:
                weekstr="星期二";
                break;
            case 3:
                weekstr="星期三";
                break;
            case 4:
                weekstr="星期四";
                break;
            case 5:
                weekstr="星期五";
                break;
            case 6:
                weekstr="星期六";
                break;
            case 0:
                weekstr="星期日";
                break;
        }
        return weekstr;
    }
//A
    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座",
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "摩羯座" };

    public final static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }

    public final static String getYear(int year) {
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        String[] years = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪" };
        return years[(year - start) % years.length];
    }
    //A
    //Schedule(String title, boolean finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips)
    final public static String encrypByMd5(String context) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(context.getBytes());//update处理
            byte [] encryContext = md.digest();//调用该方法完成计算

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < encryContext.length; offset++) {//做相应的转化（十六进制）
                i = encryContext[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return  null;
        }
    }
}
//<color name="purple_200">#FFBB86FC</color>
//<color name="purple_500">#FF6200EE</color>
//<color name="purple_700">#FF3700B3</color>
//<color name="pink_200">#DCEA7BA8</color>
//<color name="pink_700">#DCC52F6B</color>
//<color name="black">#FF000000</color>
//<color name="white">#FFFFFFFF</color>
//<color name="pink_500">#DCC52F6B</color>
//<color name="pink_300">#DCEA7BA8</color>
//<color name="pink_800">#DCC52F6B</color>
//<color name="blue">#12D4EC</color>
//<color name="purple_100">#B80ED6</color>
//<color name="yellow">#F1D209</color>
//<color name="black_bg">#070404</color>
//<color name="white_bg">#FFFFFF</color>
//<color name="pink_">#DCEA7BA8</color>
//<color name="pink_30">#E4ADC5</color>
//<color name="pink_750">#DCD55B8C</color>
//<color name="pink_1000">#DCC52F6B</color>
//<color name="pink_25">#ECBFD2</color>
//<color name="pink_50">#DA768D</color>
//<color name="white_100">#F4EEEF</color>
//<color name="grey_100">#504D4D</color>
//<color name="grey_200">#817676</color>
//<color name="qingse">#00FFFF</color>
//<color name="bilvse">#7FFFD4</color>
//<color name="qinglvse">#40E0D0</color>
//<color name="green">#00FF00</color>
//<color name="huanglvse">#7FFF00</color>
//<color name="cuilvse">#00C957</color>
//<color name="anlvse">#308014</color>
//<color name="hailvse">#2E8B57</color>
//<color name="bohese">#BDFCC9</color>
//<color name="bohelv">#7adfb8</color>
//<color name="red">#D50202</color>

