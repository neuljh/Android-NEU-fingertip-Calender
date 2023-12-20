package com.example.finalwork2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.JavaClass.PYQ;
import com.example.finalwork2.JavaClass.RecyclerAdapterPYQ;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.MyLeanCloudApp;

import java.util.ArrayList;

public class PYQActivity extends AppCompatActivity {
    private static ArrayList<PYQ> pyqs;
    private RecyclerView recyclerView;
    private RecyclerAdapterPYQ recyclerAdapterPYQ;
    private NowDateTime nowDateTime=new NowDateTime();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyq);

        imageView=findViewById(R.id.iv_pyq_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip_add();
            }
        });

        init_data();
        init_recycler();
    }
//    public PYQ(int image_id, String date, String time, String name, String content) {
//        this.image_id = image_id;
//        this.date = date;
//        this.time = time;
//        this.name = name;
//        this.content = content;
//    }
    public static void init_data(){
        //pre_schedules=new ArrayList<>();
        pyqs=new ArrayList<>();
        pyqs= MyLeanCloudApp.get_data();
        //pyqs.add(new PYQ(R.mipmap.neu,nowDateTime.getNowDate(),"19:25","开心的安卓开发者","躺平ing" ));
//        pyqs.add(new PYQ(R.mipmap.neu,nowDateTime.getNowDate(),"19:25","开心的安卓开发者","躺平ing" ));
//        pyqs.add(new PYQ(R.mipmap.neu,nowDateTime.getNowDate(),"19:25","开心的安卓开发者","躺平ing" ));
        //MyLeanCloudApp.add(R.mipmap.neu,nowDateTime.getNowDate(),"19:25","开心的安卓开发者","躺平ing" );
    }

    public void addItem(String content,String name){
        recyclerAdapterPYQ.addItem(recyclerAdapterPYQ.getItemCount(),content,name);
        recyclerAdapterPYQ.notifyDataSetChanged();
    }

    public void tip_add(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_daojishi);
        builder.setTitle("请输入发布内容");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(PYQActivity.this).inflate(R.layout.add_dialog, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
        final EditText title = (EditText)view.findViewById(R.id.et_adddialog_title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String a = title.getText().toString().trim();
                addItem(a,"安卓狂热粉丝");
//                recyclerAdapterSchedule.notifyDataSetChanged();
//                Toast.makeText(getContext(), a, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }

    public void init_recycler(){
        recyclerView = findViewById(R.id.rv_pyq);
        // 线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PYQActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 用于描述item的适配器
        recyclerAdapterPYQ = new RecyclerAdapterPYQ(pyqs);

        recyclerView.setAdapter(recyclerAdapterPYQ);
        DividerItemDecoration mDivider = new
                DividerItemDecoration(PYQActivity.this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

//        recyclerAdapterTimeline.setOnItemClickListener(this);
    }
}
