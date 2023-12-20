package com.example.finalwork2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalwork2.Activity.AboutAppActivity;
import com.example.finalwork2.Activity.BirthActivity;
import com.example.finalwork2.Activity.InforActivity;
import com.example.finalwork2.Activity.NotifySettingActivity;
import com.example.finalwork2.Activity.ScheduleCountActivity;
import com.example.finalwork2.Database.DBOpenHelper;
import com.example.finalwork2.Database.DBOpenHelperQQ;
import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.JavaClass.QQ;
import com.example.finalwork2.JavaClass.Schedule;
import com.example.finalwork2.R;

import java.util.ArrayList;

public class MineFragment extends Fragment implements View.OnClickListener{
    private LinearLayout ll_tongjischedule,ll_settings,ll_birthday,ll_aboutapp;
    private TextView tv_create,tv_finsih,tv_nonfinish;
    private ConstraintLayout cl_infor;
    private View view;
    private Intent intent;
    private DBOpenHelper dbOpenHelper;
    private ArrayList<Schedule> schedules;
    private NowDateTime nowDateTime=new NowDateTime();
    private int create=0,finish=0,overdue=0;
    private String username="";
    private ArrayList<QQ> qqs;
    private DBOpenHelperQQ dbOpenHelperQQ;
    private ImageView iv_img;
    private TextView tv_name;

    public void init_data(){
        //获取MainActivity的内容
        Bundle bundle=getArguments();
        username=bundle.getString("username");

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
        tv_name.setText(nickname);
        tv_name.setTextColor(getResources().getColor(R.color.white_bg));
        //Glide设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(100);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(20, 20);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(getContext())
                .load(image_url)
                .error(getResources().getDrawable(R.mipmap.qq_left))
                .apply(options)
                .into(iv_img);

        schedules=new ArrayList<>();
        schedules=dbOpenHelper.getAllschedules();
        for(Schedule schedule:schedules){
            create++;
            if(schedule.isFinish()){
                finish++;
            }
            if(!schedule.isFinish()&&schedule.getScheduledate().compareTo(nowDateTime.getNowDate())<0){
                overdue++;
            }
        }
        tv_create.setText(String.valueOf(create));
        tv_finsih.setText(String.valueOf(finish));
        tv_nonfinish.setText(String.valueOf(overdue));



    }

    public void init_view(){
        ll_tongjischedule=view.findViewById(R.id.mine_linerlay_tongjischedule);
        ll_tongjischedule.setOnClickListener(this);

        cl_infor=view.findViewById(R.id.cl_minefra_infor);
        cl_infor.setOnClickListener(this);

        ll_settings=view.findViewById(R.id.mine_linearlay_setting);
        ll_settings.setOnClickListener(this);

        ll_birthday=view.findViewById(R.id.mine_linerlay_birth);
        ll_birthday.setOnClickListener(this);

        ll_aboutapp=view.findViewById(R.id.mine_linearlay_aboutapp);
        ll_aboutapp.setOnClickListener(this);

        tv_create=view.findViewById(R.id.mine_tv_createtimes);
        tv_finsih=view.findViewById(R.id.mine_tv_finishtimes);
        tv_nonfinish=view.findViewById(R.id.mine_tv_nofinshtimes);
        iv_img=view.findViewById(R.id.mine_iv_img);
        tv_name=view.findViewById(R.id.mine_tv_name);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        dbOpenHelper=new DBOpenHelper(getContext());
        dbOpenHelperQQ=new DBOpenHelperQQ(getContext());

        init_view();
        init_data();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_linerlay_tongjischedule:
                intent=new Intent(getActivity(), ScheduleCountActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.cl_minefra_infor:
                intent=new Intent(getActivity(), InforActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.mine_linearlay_setting:
                intent=new Intent(getActivity(), NotifySettingActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.mine_linerlay_birth:
                intent=new Intent(getActivity(), BirthActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.mine_linearlay_aboutapp:
                intent=new Intent(getActivity(), AboutAppActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);

                break;
        }
    }
}
