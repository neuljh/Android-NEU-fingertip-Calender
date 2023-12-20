package com.example.finalwork2.Popwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.finalwork2.R;

public class ClassInforActivity extends Activity {
    private TextView tv_name,tv_classtime,tv_section,tv_teacher,tv_serialtime,tv_classroom;
    private Intent intent;
    private String name,classtime,section,teacher,serialtime,room;

    public void init_view(){
        tv_classtime=findViewById(R.id.tv_classinfor_classtime);
        tv_name=findViewById(R.id.tv_classinfor_classname);
        tv_section=findViewById(R.id.tv_classinfor_classsection);
        tv_teacher=findViewById(R.id.tv_classinfor_classteacher);
        tv_serialtime=findViewById(R.id.tv_classinfor_serialtime);
        tv_classroom=findViewById(R.id.tv_classinfor_classroom);

        intent=getIntent();
        name=intent.getStringExtra("classname");
        classtime=intent.getStringExtra("classtime");
        section=intent.getStringExtra("classsection");
        teacher=intent.getStringExtra("classteacher");
        serialtime=intent.getStringExtra("classserialtime");
        room=intent.getStringExtra("classroom");

        tv_classtime.setText(classtime);
        tv_name.setText(name);
        tv_section.setText(section);
        tv_teacher.setText(teacher);
        tv_serialtime.setText(serialtime);
        tv_classroom.setText(room);


    }

    public void set_view(){
        /** 设置宽度为屏幕的0.9*/
        WindowManager windowManager = getWindowManager();
        /* 获取屏幕宽、高 */
        Display display = windowManager.getDefaultDisplay();
        /* 获取对话框当前的参数值 */
        WindowManager.LayoutParams p = getWindow().getAttributes();
        /* 宽度设置为屏幕的1 */
        p.width = (int) (display.getWidth() * 0.7);
        /* 设置透明度,0.0为完全透明，1.0为完全不透明 */
        p.alpha = 0.85f;
        /* 设置布局参数 */
        getWindow().setAttributes(p);

//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);

        /* 设置点击弹框外部不可消失 */
        setFinishOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_infor);

        init_view();
        set_view();
    }
}
