package com.example.finalwork2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork2.Database.DBOpenHelperBirth;
import com.example.finalwork2.JavaClass.Birth;
import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;

import java.util.ArrayList;

public class BirthinforActivity extends AppCompatActivity implements View.OnClickListener {
    private String id;
    private Intent intent;
    private ArrayList<Birth> births;
    private DBOpenHelperBirth dbOpenHelperBirth;
    private int image_id;
    private String title,birthday;

    private ImageView iv_img;
    private TextView tv_title,tv_birthday,tv_message,tv_days,tv_message2,tv_days2;
    private TextView tv_shengxiao,tv_xingzuo;
    private NowDateTime nowDateTime=new NowDateTime();
    private ImageView iv_delete;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthinfor);
        dbOpenHelperBirth=new DBOpenHelperBirth(this);
        username=getIntent().getStringExtra("username");

        init_view();
        init_data();
    }

    public void init_view(){
        iv_img=findViewById(R.id.birth_iv_img_infor);
        tv_title=findViewById(R.id.birth_tv_title_infor);
        tv_birthday=findViewById(R.id.brith_tv_birthday_infor);
        tv_message=findViewById(R.id.tv_birth_message_infor);
        tv_days=findViewById(R.id.tv_birth_day_infor);
        tv_message2=findViewById(R.id.tv_birth_infor_dansheng);
        tv_days2=findViewById(R.id.tv_virth_infor_julishengri);

        tv_shengxiao=findViewById(R.id.tv_shengxiao);
        tv_xingzuo=findViewById(R.id.tv_xingzuo);

        iv_delete=findViewById(R.id.iv_birthday_infor_delete);
        iv_delete.setOnClickListener(this);
    }

    public void init_data(){
        String time3;
        intent=getIntent();
        id=intent.getStringExtra("id");
        births=dbOpenHelperBirth.getAllbirths();
        for(int index=0;index<births.size();index++){
            if(id.equals(births.get(index).getId())){
                image_id=births.get(index).getImage_id();
                title=births.get(index).getTitle();
                birthday=births.get(index).getBirthday();
            }
        }
        iv_img.setImageResource(image_id);
        tv_title.setText(title);
        tv_birthday.setText(birthday);
        int ages=0;
        if(Integer.parseInt(ContentUtils.From_Date_Get_Month(nowDateTime.getNowDate()))>=Integer.parseInt(ContentUtils.From_Date_Get_Month(birthday))){
            if(Integer.parseInt(ContentUtils.From_Date_Get_Month(nowDateTime.getNowDate()))==Integer.parseInt(ContentUtils.From_Date_Get_Month(birthday))){
                if(Integer.parseInt(ContentUtils.From_Date_Get_Day(nowDateTime.getNowDate()))>=Integer.parseInt(ContentUtils.From_Date_Get_Day(birthday))){
                    ages=Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate()))-Integer.parseInt(ContentUtils.From_Date_Get_Year(birthday))+1;
                }else{
                    ages=Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate()))-Integer.parseInt(ContentUtils.From_Date_Get_Year(birthday));
                }
            }
            time3=(Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate()))+1)+"."+Integer.parseInt(ContentUtils.From_Date_Get_Month(birthday))+"."+Integer.parseInt(ContentUtils.From_Date_Get_Day(birthday));
            ages=Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate()))-Integer.parseInt(ContentUtils.From_Date_Get_Year(birthday))+1;
        }else{
            time3=(Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate())))+"."+Integer.parseInt(ContentUtils.From_Date_Get_Month(birthday))+"."+Integer.parseInt(ContentUtils.From_Date_Get_Day(birthday));
            ages=Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate()))-Integer.parseInt(ContentUtils.From_Date_Get_Year(birthday));
        }
        tv_message.setText("距离\""+title+"\"的"+ages+"岁生日还有");
        tv_message2.setText("距离\""+title+"\"诞生已经过去了");
        String time1=Integer.parseInt(ContentUtils.From_Date_Get_Year(birthday))+"."+Integer.parseInt(ContentUtils.From_Date_Get_Month(birthday))+"."+Integer.parseInt(ContentUtils.From_Date_Get_Day(birthday));
        String time2=Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate()))+"."+Integer.parseInt(ContentUtils.From_Date_Get_Month(nowDateTime.getNowDate()))+"."+Integer.parseInt(ContentUtils.From_Date_Get_Day(nowDateTime.getNowDate()));
        tv_days.setText(ContentUtils.Caculate_days(time1,time2)+"天");
        tv_days2.setText(ContentUtils.Caculate_days(time2,time3)+"天");
        tv_shengxiao.setText(ContentUtils.getYear(Integer.parseInt(ContentUtils.From_Date_Get_Year(birthday))));
        tv_xingzuo.setText(ContentUtils.getConstellation(Integer.parseInt(ContentUtils.From_Date_Get_Month(birthday)),Integer.parseInt(ContentUtils.From_Date_Get_Day(birthday))));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_birthday_infor_delete:
                dbOpenHelperBirth.delete(id);
                intent=new Intent(BirthinforActivity.this,BirthActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);

                break;
        }
    }
}
