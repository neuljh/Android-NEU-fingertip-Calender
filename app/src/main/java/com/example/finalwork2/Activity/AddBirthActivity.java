package com.example.finalwork2.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalwork2.Database.DBOpenHelperBirth;
import com.example.finalwork2.JavaClass.NowDateTime;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Random;

public class AddBirthActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Intent intent;
    private EditText et_name,et_birthday;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar=Calendar.getInstance();
    private NowDateTime nowDateTime=new NowDateTime();
    private DBOpenHelperBirth dbOpenHelperBirth;
    private Random random=new Random();
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birth);
        dbOpenHelperBirth=new DBOpenHelperBirth(this);
        username=getIntent().getStringExtra("username");

        init_view();
        init_toolbar();
    }

    private void showCalenderDialog() {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "年" + nowDateTime.getT(month + 1) + "月" + nowDateTime.getT(dayOfMonth) + "日";
                et_birthday.setText(date);
            }
        },Integer.parseInt(ContentUtils.From_Date_Get_Year(nowDateTime.getNowDate())), Integer.parseInt(ContentUtils.From_Date_Get_Month(nowDateTime.getNowDate())), Integer.parseInt(ContentUtils.From_Date_Get_Day(nowDateTime.getNowDate())));
        datePickerDialog.show();
    }

    public void init_view(){
        et_name=findViewById(R.id.et_name);
        et_birthday=findViewById(R.id.et_birthday);
//        et_birthday.setEnabled(false);
        et_birthday.setFocusable(false);
        et_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalenderDialog();
            }
        });
    }

    public void init_toolbar(){
        toolbar=findViewById(R.id.tb_add_birth);
        setSupportActionBar(toolbar);

        //使用actionbar自带的返回键图片
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使用actionbar，自定义返回键图片
        actionBar.setHomeAsUpIndicator(R.drawable.ic_cake);

        //使用toolbar自定义返回键图片
        //toolbar.setNavigationIcon(R.drawable.ic_addbox);

        //actioBar设置标题
        actionBar.setTitle("1");
        //actionBar设置子标题
//        actionBar.setSubtitle("2");

        //toolbar设置标题
        toolbar.setTitle("新建生日");
        //toolbar设置标题颜色
        toolbar.setTitleTextColor(Color.WHITE);
        //toolbar设置子标题
        //toolbar.setSubtitle("周/月/年");
        //toolbar设置子标题颜色
        toolbar.setSubtitleTextColor(Color.BLACK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_birth_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case R.id.action_back:
                intent=new Intent(AddBirthActivity.this,BirthActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                return true;
            case R.id.action_finsh:
                String name=et_name.getText().toString();
                String date=et_birthday.getText().toString();
                if(name.isEmpty()||date.isEmpty()){
                    Toast.makeText(this,"输入的信息不能为空！",Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    int image_id=random.nextInt(21);
                    dbOpenHelperBirth.add(ContentUtils.imgs.get(image_id),name,date);
                    Toast.makeText(this,"添加生日成功！",Toast.LENGTH_SHORT).show();
                }
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if ("MenuBuilder".equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
