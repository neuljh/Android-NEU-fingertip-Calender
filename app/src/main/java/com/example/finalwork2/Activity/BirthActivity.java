package com.example.finalwork2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork2.Database.DBOpenHelperBirth;
import com.example.finalwork2.JavaClass.Birth;
import com.example.finalwork2.JavaClass.RecyclerAdapterBirth;
import com.example.finalwork2.R;

import java.util.ArrayList;

public class BirthActivity extends AppCompatActivity implements RecyclerAdapterBirth.OnItemClickListener,View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerAdapterBirth recyclerAdapterbirth;
    private ArrayList<Birth> births;
    private DBOpenHelperBirth dbOpenHelperBirth;
    private ImageView iv_back,iv_add;
    private Intent intent;
    private int nav_num=3;
    private String id;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth);
        dbOpenHelperBirth=new DBOpenHelperBirth(this);
        username=getIntent().getStringExtra("username");

        init_view();
        init_data();
        init_recycler();
    }

    public void init_view(){
        iv_back=findViewById(R.id.birth_iv_back);
        iv_back.setOnClickListener(this);

        iv_add=findViewById(R.id.birth_iv_add);
        iv_add.setOnClickListener(this);
    }

    public void init_data(){
        births=new ArrayList<>();
        if(dbOpenHelperBirth.getAllbirths().size()==0){
            dbOpenHelperBirth.add(R.mipmap.pic21,"树德的生日","1929年12月06日");
        }else{
            births=dbOpenHelperBirth.getAllbirths();
        }
    }

    public void init_recycler(){
        recyclerView = findViewById(R.id.rv_birth);
        // 线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BirthActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 用于描述item的适配器
        recyclerAdapterbirth= new RecyclerAdapterBirth(births);

        recyclerView.setAdapter(recyclerAdapterbirth);
//        DividerItemDecoration mDivider = new
//                DividerItemDecoration(TimelineActivity.this,DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(mDivider);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        recyclerAdapterbirth.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        id=births.get(position).getId();
        intent=new Intent(BirthActivity.this,BirthinforActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.birth_iv_back:
                intent=new Intent(BirthActivity.this,MainActivity.class);
                intent.putExtra("nav_num",nav_num);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.birth_iv_add:
                intent=new Intent(BirthActivity.this,AddBirthActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);

                break;

        }
    }
}
