package com.example.finalwork2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalwork2.Database.DBOpenHelperQQ;
import com.example.finalwork2.JavaClass.QQ;
import com.example.finalwork2.R;

import java.util.ArrayList;

public class InforActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back,iv_img;
    private TextView tv_head,tv_nickname,tv_id,tv_vxqq,tv_phonenum,tv_contact;
    private Intent intent;
    private int nav_num=3;
    private String username;
    private ArrayList<QQ> qqs;
    private DBOpenHelperQQ dbOpenHelperQQ;

    public void init_view(){
        iv_back=findViewById(R.id.iv_infor_back);
        tv_head=findViewById(R.id.tv_infor_head);
        tv_nickname=findViewById(R.id.tv_infor_nickname);
        tv_id=findViewById(R.id.tv_infor_id);
        tv_vxqq=findViewById(R.id.tv_infor_vxqq);
        tv_phonenum=findViewById(R.id.tv_infor_phonenum);
        iv_img=findViewById(R.id.iv_qq_infor);
        tv_contact=findViewById(R.id.tv_infor_contact);

        iv_back.setOnClickListener(this);
        tv_head.setOnClickListener(this);
        tv_nickname.setOnClickListener(this);
        tv_id.setOnClickListener(this);
        tv_vxqq.setOnClickListener(this);
        tv_phonenum.setOnClickListener(this);
    }

    public void init_data(){
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
        String num="5215555554";
        tv_phonenum.setText(num);
        tv_nickname.setText(nickname);
        tv_vxqq.setText(qqnum);
        tv_contact.setText(num);
        tv_id.setText(app_id);
        //Glide设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(100);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(20, 20);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(InforActivity.this)
                .load(image_url)
                .error(getResources().getDrawable(R.mipmap.qq_left))
                .apply(options)
                .into(iv_img);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        username=getIntent().getStringExtra("username");
        dbOpenHelperQQ=new DBOpenHelperQQ(this);

        init_view();
        init_data();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_infor_back:
                intent=new Intent(InforActivity.this,MainActivity.class);
                intent.putExtra("nav_num",nav_num);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
        }
    }
}
