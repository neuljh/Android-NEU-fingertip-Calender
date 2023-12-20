package com.example.finalwork2.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalwork2.Fragment.SCmonthFragment;
import com.example.finalwork2.Fragment.SCweekFragment;
import com.example.finalwork2.Fragment.SCyearFragment;
import com.example.finalwork2.R;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Method;

public class ScheduleCountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SCweekFragment sCweekFragment;
    private SCmonthFragment sCmonthFragment;
    private SCyearFragment sCyearFragment;
    private Fragment mFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Intent intent;
    private int nav_num=3;
    private String username;


    public void init_toolbar(){
        toolbar=findViewById(R.id.schedule_count_toolbar);
        setSupportActionBar(toolbar);

        //使用actionbar自带的返回键图片
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使用actionbar，自定义返回键图片
        actionBar.setHomeAsUpIndicator(R.drawable.ic_calender_main);

        //使用toolbar自定义返回键图片
        //toolbar.setNavigationIcon(R.drawable.ic_addbox);

        //actioBar设置标题
        actionBar.setTitle("1");
        //actionBar设置子标题
        actionBar.setSubtitle("2");

        //toolbar设置标题
        toolbar.setTitle("日程统计");
        //toolbar设置标题颜色
        toolbar.setTitleTextColor(Color.WHITE);
        //toolbar设置子标题
        toolbar.setSubtitle("周/月/年");
        //toolbar设置子标题颜色
        toolbar.setSubtitleTextColor(Color.BLACK);
    }

    public void init_view(){
        sCweekFragment=new SCweekFragment();
        sCmonthFragment=new SCmonthFragment();
        sCyearFragment=new SCyearFragment();
        transaction=getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fl_schedulecount,sCweekFragment).commit();
        mFragment=sCweekFragment;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedulecount_toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_share:
                showSnackBar(item.getTitle().toString());
                return true;
            case R.id.action_week:
                switchFragment(sCweekFragment);
                showSnackBar(item.getTitle().toString());
                transaction.commit();
                return true;
            case R.id.action_month:
                switchFragment(sCmonthFragment);
                showSnackBar(item.getTitle().toString());
                transaction.commit();
                return true;
            case R.id.action_year:
                switchFragment(sCyearFragment);
                showSnackBar(item.getTitle().toString());
                transaction.commit();
                return true;
            case R.id.action_back:
                intent=new Intent(ScheduleCountActivity.this,MainActivity.class);
                intent.putExtra("nav_num",nav_num);
                intent.putExtra("username",username);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }
    //弹出一个snackBar
    public void showSnackBar(String string) {
        Snackbar.make(getDecorView(), string, Snackbar.LENGTH_LONG).show();
    }
    private View getDecorView() {
        return getWindow().getDecorView();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_count);
        username=getIntent().getStringExtra("username");

        init_toolbar();
        init_view();
    }

    /**
     * 切换fragment的方法
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if(mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.fl_schedulecount,fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }
}
