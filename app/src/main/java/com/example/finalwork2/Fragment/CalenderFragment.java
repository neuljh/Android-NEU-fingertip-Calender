package com.example.finalwork2.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalwork2.Activity.LocateActivity;
import com.example.finalwork2.Activity.MapActivity;
import com.example.finalwork2.Activity.UpgradeDialogActivity;
import com.example.finalwork2.Database.DBOpenHelperClass;
import com.example.finalwork2.Database.DBOpenHelperQQ;
import com.example.finalwork2.JavaClass.QQ;
import com.example.finalwork2.JavaClass.SimpleNEUClass;
import com.example.finalwork2.Popwindow.ClassInforActivity;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;
import com.example.finalwork2.Weather.WeatherActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalenderFragment extends Fragment implements View.OnTouchListener{
    private View view;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView iv_menu,iv_search,iv_head;
    private TextView tv_name;
    private ArrayList<SimpleNEUClass> simpleNEUClasses;
    private DBOpenHelperClass dbOpenHelperClass;
    private String weeks[];
    private Random random=new Random();
    private Intent intent;
    private int nav_num=1;
    private int week_number=1;
    private String username;
    private ArrayList<QQ> qqs;
    private DBOpenHelperQQ dbOpenHelperQQ;

    public void init_view(){
        //获取MainActivity的内容
        Bundle bundle=getArguments();
        username=bundle.getString("username");

        drawerLayout=view.findViewById(R.id.calender_drawer_layout);
        navigationView=view.findViewById(R.id.calender_navigation_view);
        iv_menu=view.findViewById(R.id.calender_iv_menu);
        iv_search=view.findViewById(R.id.calender_iv_search);


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
                    case R.id.item_calender_todayweather:
                        startActivity(new Intent(getActivity(), UpgradeDialogActivity.class));
                        break;
                    case R.id.item_calender_weather:
                        startActivity(new Intent(getActivity(), WeatherActivity.class));
                        break;
                    case R.id.item_calender_city:
                        startActivity(new Intent(getActivity(), MapActivity.class));
                        break;
                    case R.id.item_calender_loc:
                        startActivity(new Intent(getActivity(), LocateActivity.class));
                        break;
                }
                return true;
            }
        });

    }

    public void init_data(){
        View headview=navigationView.getHeaderView(0);
        iv_head=headview.findViewById(R.id.calender_menu_img);

        tv_name=headview.findViewById(R.id.calender_menu_name);
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
        tv_name.setText(nickname);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_calender, container, false);
        dbOpenHelperQQ=new DBOpenHelperQQ(getContext());
        dbOpenHelperClass=new DBOpenHelperClass(getContext());
        simpleNEUClasses=dbOpenHelperClass.getAllneuClass();

        init_view();
        init_data();
        init_spinner();

        framework();

        //set_default_class();

//        for(int times=0;times<simpleNEUClasses.size();times++){
//            test_set_class(times,1);
//        }




        return view;
    }

    public void framework() {

        //创建一个GridLayout对象
        GridLayout gridLayout;
        //定义每个框的id，之后会动态改变id值
        int id = 1;

        //渲染每一列（周）
        for (int i = 1; i < 8; i++) {

            //注入GridLayout对应的列，根据星期几调用LayoutColumn方法
            gridLayout = LayoutColumn(i);

            //渲染每一行（节课）
            for (int j = 1; j < 12; j +=2) {
                //声明一个新的TextView
                TextView textView1 = new TextView(getContext());

                //给TextView设置style样式
                textView1.setId(id++);
                textView1.setText("");
                textView1.setMaxLines(5);
                textView1.setEllipsize(TextUtils.TruncateAt.END);
                textView1.setTextColor(getResources().getColor(R.color.white_bg));
                textView1.setBackground(getResources().getDrawable(R.drawable.corners_bg));
//                textView1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                textView1.setGravity(Gravity.CENTER);

                //GridLayout.LayoutParams设置在此gridLayout列中TextView布局
                GridLayout.LayoutParams params1 = new GridLayout.LayoutParams();
                params1.setMargins(5,10,5,10);
                params1.width = GridLayout.LayoutParams.MATCH_PARENT;
                params1.height = 0;
                //设置在gridLayout中的方位，参数1：在第几行。参数2：占几行。参数3：权值
                //这个权值是根据xml中第一个gridLayout节课权值设定的。
                params1.rowSpec = GridLayout.spec( j, 2,1);

                //把TextView和布局样式添加到此gridLayout中
                gridLayout.addView(textView1, params1);
            }

        }

    }

    public GridLayout LayoutColumn(int i) {

        //防止空指针操作
        GridLayout gridLayout = view.findViewById(R.id.d1);

        //参数i：星期几。根据i寻找xml对应的GridLayout并注入。
        switch (i) {
            case 1: {
                gridLayout = view.findViewById(R.id.d1);
                break;
            }
            case 2: {
                gridLayout = view.findViewById(R.id.d2);
                break;
            }
            case 3: {
                gridLayout = view.findViewById(R.id.d3);
                break;
            }
            case 4: {
                gridLayout = view.findViewById(R.id.d4);
                break;
            }
            case 5: {
                gridLayout = view.findViewById(R.id.d5);
                break;
            }
            case 6: {
                gridLayout = view.findViewById(R.id.d6);
                break;
            }
            case 7: {
                gridLayout = view.findViewById(R.id.d7);
                break;
            }
        }
        return gridLayout;
    }

    public void test_set_class(int test_num,int week_num){
        simpleNEUClasses=dbOpenHelperClass.getAllneuClass();
        int weekday;
        String class_name;
        String class_room;
        String teacher;
        List<Integer> sections;
        List<Integer> weeks;

        int sum=0;

        ArrayList<Integer> put_pos=new ArrayList<>();
        sections=new ArrayList<>();
        weeks=new ArrayList<>();

        weekday=simpleNEUClasses.get(test_num).getDay();
        class_name=simpleNEUClasses.get(test_num).getName();
        class_room=simpleNEUClasses.get(test_num).getPosition();
        teacher=simpleNEUClasses.get(test_num).getTeacher();
        sections=simpleNEUClasses.get(test_num).getSections();
        weeks=simpleNEUClasses.get(test_num).getWeeks();

        for(int j=0;j<sections.size();j++){
            sum=sum+sections.get(j);
        }
        switch (sum){
            case 3:
                put_pos.add(1);
                break;
            case 6:
            case 10:
                put_pos.add(1);
                put_pos.add(2);
                break;
            case 11:
                put_pos.add(3);
                break;
            case 18:
            case 26:
                put_pos.add(3);
                put_pos.add(4);
                break;
            case 19:
                put_pos.add(5);
                break;
            case 30:
            case 42:
                put_pos.add(5);
                put_pos.add(6);
                break;
            case 23:
                put_pos.add(6);
                break;
            case 15:
                put_pos.add(4);
                break;
            case 7:
                put_pos.add(2);
                break;
        }
        for(int k=0;k<weeks.size();k++){
            if(week_num==weeks.get(k)){
                for(int j=0;j<put_pos.size();j++){
                    TextView tv_class=view.findViewById((weekday-1)*6+put_pos.get(j));
                    tv_class.setOnTouchListener(this);//周六的课表无法显示？
                    tv_class.setText(class_name+"\n&&\n"+class_room);
                    tv_class.setTextColor(getResources().getColor(R.color.white_bg));
                    tv_class.setBackground(getResources().getDrawable(R.drawable.corners_bg));
//                    tv_class.setTextColor(Color.WHITE);

                    tv_class.setBackgroundColor(Color.parseColor((ContentUtils.colors.get(String.valueOf(random.nextInt(20)+1)))));
                }//这里注释掉只是暂时的！！
            }
        }
        put_pos.clear();



    }

    public void clear_class(){
        for(int i=1;i<43;i++){
            TextView tv_class=view.findViewById(i);
            tv_class.setText("");
            tv_class.setBackground(getResources().getDrawable(R.drawable.corners_bg));
        }
    }



    public void init_spinner(){
        weeks= ContentUtils.CLASS_WEEKS;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(getContext(),R.layout.item_select,weeks);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        Spinner sp = view.findViewById(R.id.spinner_weeks);
        //设置下拉框的标题，不设置就没有难看的标题了
        sp.setPrompt("请选择周数");
        //设置下拉框的数组适配器
        sp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        //sp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp.setOnItemSelectedListener(new MySelectedListener());
    }
    class MySelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            clear_class();
            Toast.makeText(getContext(),"您选择的是："+weeks[i],Toast.LENGTH_SHORT).show();
            for(int times=0;times<simpleNEUClasses.size();times++){
                test_set_class(times,(i+1));
            }
            week_number=i+1;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public boolean Match_weeks(int week_number,List<Integer> weeks){
        for(int index=0;index<weeks.size();index++){
            if(week_number==weeks.get(index)){
                return true;
            }
        }
        return false;
    }



    @SuppressLint("Range")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int weekday_touch;
        int number_touch=0;
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN: {
                String sections="";
                TextView textView = (TextView) view;
                int id=textView.getId();
                int id_temp=id;
                weekday_touch=1;
                while(id_temp>6){
                    id_temp=id_temp-6;
                    weekday_touch++;
                }
                switch (id_temp){
                    case 1:
                        sections="1,2";
                        break;
                    case 2:
                        sections="3,4";
                        break;
                    case 3:
                        sections="5,6";
                        break;
                    case 4:
                        sections="7,8";
                        break;
                    case 5:
                        sections="9,10";
                        break;
                    case 6:
                        sections="11,12";
                        break;
                }
                //Toast.makeText(getContext(),"有课啦1：",Toast.LENGTH_SHORT).show();
                simpleNEUClasses=dbOpenHelperClass.getAllneuClass();
                for(int index=0;index<simpleNEUClasses.size();index++){
                    if(simpleNEUClasses.get(index).getDay().equals(weekday_touch)&&ContentUtils.List_to_String(simpleNEUClasses.get(index).getSections()).contains(sections)&&Match_weeks(week_number,simpleNEUClasses.get(index).getWeeks())){
                        String weekday="星期"+String.valueOf(simpleNEUClasses.get(index).getDay());
                        String name=simpleNEUClasses.get(index).getName();
                        String classroom=simpleNEUClasses.get(index).getPosition();
                        //sections
                        String teacher=simpleNEUClasses.get(index).getTeacher();
                        String serialtime=simpleNEUClasses.get(index).getWeeks().get(0)+"--"+simpleNEUClasses.get(index).getWeeks().get(simpleNEUClasses.get(index).getWeeks().size()-1)+"周";
                        intent=new Intent(getContext(), ClassInforActivity.class);
                        intent.putExtra("classname",name);
                        intent.putExtra("classtime",weekday);
                        intent.putExtra("classsection",sections);
                        intent.putExtra("classteacher",teacher);
                        intent.putExtra("classserialtime",serialtime);
                        intent.putExtra("classroom",classroom);
                        //Toast.makeText(getContext(),"有课啦2：",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
//                        name=intent.getStringExtra("classname");
//                        classtime=intent.getStringExtra("classtime");
//                        section=intent.getStringExtra("classsection");
//                        teacher=intent.getStringExtra("classteacher");
//                        serialtime=intent.getStringExtra("classserialtime");
//                        room=intent.getStringExtra("classroom");

                    }
                }
                return true;
            }
//            case MotionEvent.ACTION_CANCEL: {
//                intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra("nav_num",nav_num);
//                startActivity(intent);
//                break;
//            }
        }

        return false;
    }




}
//    public void set_default_class(){
//        int week_num=1;
//        simpleNEUClasses=dbOpenHelperClass.getAllneuClass();
//        int weekday;
//        String class_name;
//        String class_room;
//        String teacher;
//        List<Integer> sections;
//        List<Integer> weeks=new ArrayList<>();
////        TextView tv_class;
//
//        int sum=0;
//        for(int index=0;index<simpleNEUClasses.size();index++){
//            ArrayList<Integer> put_pos=new ArrayList<>();
//            sections=new ArrayList<>();
//
//            weekday=simpleNEUClasses.get(index).getDay();
//            class_name=simpleNEUClasses.get(index).getName();
//            class_room=simpleNEUClasses.get(index).getPosition();
//            teacher=simpleNEUClasses.get(index).getTeacher();
//            sections=simpleNEUClasses.get(index).getSections();
//            weeks=simpleNEUClasses.get(index).getWeeks();
//
//            for(int j=0;j<sections.size();j++){
//                sum=sum+sections.get(j);
//            }
//            switch (sum){
//                case 3:
//                    put_pos.add(1);
//                    break;
//                case 6:
//                case 10:
//                    put_pos.add(1);
//                    put_pos.add(2);
//                    break;
//                case 11:
//                    put_pos.add(3);
//                    break;
//                case 18:
//                case 25:
//                    put_pos.add(3);
//                    put_pos.add(4);
//                    break;
//                case 19:
//                    put_pos.add(5);
//                    break;
//                case 30:
//                case 42:
//                    put_pos.add(5);
//                    put_pos.add(6);
//                    break;
//                case 23:
//                    put_pos.add(6);
//                    break;
//                case 15:
//                    put_pos.add(4);
//                    break;
//                case 7:
//                    put_pos.add(2);
//                    break;
//            }
//            for(int k=0;k<weeks.size();k++){
//                if(week_num==weeks.get(k)){
//                    for(int j=0;j<put_pos.size();j++){
//                        TextView tv_class=view.findViewById((weekday-1)*6+put_pos.get(j));
//                        tv_class.setText(class_name+"\n&&\n"+class_room);
//                        tv_class.setTextColor(Color.WHITE);
//                        tv_class.setOnTouchListener(this);
//                        tv_class.setBackgroundColor(Color.parseColor((ContentUtils.colors.get(String.valueOf(random.nextInt(20)+1)))));
//                    }
//                }
//            }
//            put_pos.clear();
//
//
//        }
//    }



//                DBHelper dbHelper = new DBHelper(MainActivity.this, DB_NAME, null, 1);
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 参数1：table_name
                // 参数2：columns 要查询出来的列名。相当于 select  *** from table语句中的 ***部分
                // 参数3：selection 查询条件字句，在条件子句允许使用占位符“?”表示条件值
                // 参数4：selectionArgs ：对应于 selection参数 占位符的值
                // 参数5：groupby 相当于 select *** from table where && group by ... 语句中 ... 的部分
                // 参数6：having 相当于 select *** from table where && group by ...having %%% 语句中 %%% 的部分
                // 参数7：orderBy ：相当于 select  ***from ？？  where&& group by ...having %%% order by@@语句中的@@ 部分，如： personid desc（按person 降序）
//        Cursor cursor = db.query(TABLE_NAME, null, "c_id=?", new String[]{String.valueOf(textView.getId())}, null, null, null);
//                Cursor cursor = db.query(TABLE_NAME, null, "c_id=?", new String[]{String.valueOf(textView.getId())}, null, null, null);

                // 将游标移到开头
//                cursor.moveToFirst();
//                if (!cursor.isAfterLast()) {
//                    Classes Class = new Classes();
//                    System.out.println(textView.getId());
//                    System.out.println(cursor.getString(cursor.getColumnIndex("c_name")));
//
//                    Intent intent = new Intent();
//                    intent.putExtra("name", cursor.getString(cursor.getColumnIndex("c_name")));
//                    intent.putExtra("time", cursor.getString(cursor.getColumnIndex("c_time")));
//                    intent.putExtra("day", cursor.getString(cursor.getColumnIndex("c_day")));
//                    intent.putExtra("teacher", cursor.getString(cursor.getColumnIndex("c_teacher")));
//                    intent.setClass(MainActivity.this, Detail.class);
//                    startActivity(intent);
//
//                }





//            int week_num=i+1;
//            simpleNEUClasses=dbOpenHelperClass.getAllneuClass();
//            int weekday;
//            String class_name;
//            String class_room;
//            String teacher;
//            List<Integer> sections;
//            List<Integer> weeks=new ArrayList<>();
//            TextView tv_class;
//
//            int sum=0;
//            for(int index=0;index<simpleNEUClasses.size();index++){
//                ArrayList<Integer> put_pos=new ArrayList<>();
//                sections=new ArrayList<>();
//
//                weekday=simpleNEUClasses.get(index).getDay();
//                class_name=simpleNEUClasses.get(index).getName();
//                class_room=simpleNEUClasses.get(index).getPosition();
//                teacher=simpleNEUClasses.get(index).getTeacher();
//                sections=simpleNEUClasses.get(index).getSections();
//                weeks=simpleNEUClasses.get(index).getWeeks();
//
//                for(int j=0;j<sections.size();j++){
//                    sum=sum+sections.get(j);
//                }
//                switch (sum){
//                    case 3:
//                        put_pos.add(1);
//                        break;
//                    case 6:
//                    case 10:
//                        put_pos.add(1);
//                        put_pos.add(2);
//                        break;
//                    case 11:
//                        put_pos.add(3);
//                        break;
//                    case 18:
//                    case 25:
//                        put_pos.add(3);
//                        put_pos.add(4);
//                        break;
//                    case 19:
//                        put_pos.add(5);
//                        break;
//                    case 30:
//                    case 42:
//                        put_pos.add(5);
//                        put_pos.add(6);
//                        break;
//                }
//                for(int k=0;k<weeks.size();k++){
//                    if(week_num==weeks.get(k)){
//                        for(int j=0;j<put_pos.size();j++){
//                            Random random=new Random();
//                            tv_class=view.findViewById((weekday-1)*6+put_pos.get(j));
//                            tv_class.setText(class_name+"&&"+class_room);
//                            tv_class.setBackgroundColor(Integer.parseInt(ContentUtils.colors.get(String.valueOf(random.nextInt(20)+1))));
//                        }
//                    }
//                }
//
//
//            }

