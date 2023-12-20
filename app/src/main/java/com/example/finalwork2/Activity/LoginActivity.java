package com.example.finalwork2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.finalwork2.Database.DBOpenHelperUser;
import com.example.finalwork2.JavaClass.User;
import com.example.finalwork2.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button Login;
    private Button register,getqq;
    private CheckBox remember;
    private DBOpenHelperUser mDBOpenHelper;
    private boolean sign=true;
    private TextView tv_forget;


    public void InitView(){
        usernameEdit=findViewById(R.id.et_username);
        passwordEdit=findViewById(R.id.et_password);
        Login=findViewById(R.id.bt_login);
        register=findViewById(R.id.bt_rg);
        remember=findViewById(R.id.cb_rpw);
        tv_forget=findViewById(R.id.tv_login_forgetpass);
        getqq=findViewById(R.id.bt_getqq);


        Login.setOnClickListener(this);
        register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        getqq.setOnClickListener(this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitView();//初始化界面
        mDBOpenHelper = new DBOpenHelperUser(this);

        SharedPreferences sp = getSharedPreferences("user_mes", MODE_PRIVATE);
        editor = sp.edit();
        if(sp.getBoolean("flag",false)) {
            String user_read = sp.getString("username", "");
            String psw_read = sp.getString("password", "");
            usernameEdit.setText(user_read);
            passwordEdit.setText(psw_read);
            remember.setChecked(true);
        }
        passwordEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Drawable drawable=passwordEdit.getCompoundDrawables()[2];
                if(drawable==null){
                    return false;
                }
                if(motionEvent.getAction()!=MotionEvent.ACTION_UP){
                    return false;
                }
                if(sign){

                    if(motionEvent.getX()>passwordEdit.getWidth()-passwordEdit.getPaddingRight()-drawable.getIntrinsicWidth()){
                        passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        sign=false;
                    }

                }else{
                    if(motionEvent.getX()>passwordEdit.getWidth()-passwordEdit.getPaddingRight()-drawable.getIntrinsicWidth()){
                        passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        //password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        //password.setText("hello");
                        sign=true;
                    }

                }
                return false;
            }
        });


//        preferences= PreferenceManager.getDefaultSharedPreferences(this);
//        usernameEdit=findViewById(R.id.et_username);
//        passwordEdit=findViewById(R.id.et_password);
//        remember=findViewById(R.id.cb_rpw);
//        Login=findViewById(R.id.bt_login);
//
//        boolean isRemember = preferences.getBoolean("remember_password",false);
//        if (isRemember){
//            String Name = preferences.getString("Name","");
//            String Password = preferences.getString("Password","");
//            usernameEdit.setText(Name);
//            passwordEdit.setText(Password);
//            remember.setChecked(true);
//        }
//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Name = usernameEdit.getText().toString();
//                String Password = passwordEdit.getText().toString();
//                if (TextUtils.isEmpty(usernameEdit.getText().toString()) || TextUtils.isEmpty(passwordEdit.getText().toString())) {
//                    Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
//                } else
//                if(Name.equals("ljh")&&Password.equals("12345678")){
//                    editor = preferences.edit();
//                    if(remember.isChecked()){
//                        editor.putBoolean("remember_password",true);
//                        editor.putString("Name",Name);
//                        editor.putString("Password",Password);
//                    }else{editor.clear();}
//                    editor.apply();
//                    Intent intent = new Intent(LoginActivity.this,InforActivity.class);
//                    intent.putExtra("Name",Name);
//                    intent.putExtra("Password",Password);
//                    startActivity(intent);
//                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
//                    finish();
//                }else {Toast.makeText(LoginActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();}
//            }
//        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_rg:
                Intent intent0=new Intent(LoginActivity.this,RegisteredActivity.class);
                startActivity(intent0);
                finish();
                break;
            case R.id.bt_getqq:
                if(usernameEdit.getText().toString().length()!=0){
                    for(User user:mDBOpenHelper.getAllData()){
                        if(usernameEdit.getText().toString().equals(user.getName())||usernameEdit.getText().toString().equals(user.getUsername())||usernameEdit.getText().toString().equals(user.getPhonenumber())){
                            Intent intent8=new Intent(LoginActivity.this,QQActivity.class);
                            intent8.putExtra("username",usernameEdit.getText().toString().trim());
                            startActivity(intent8);
                        }else{
                            Toast.makeText(LoginActivity.this,"未输入用户名或用户名不合法不可绑定QQ号！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"未输入用户名或用户名不合法不可绑定QQ号！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_login:
                String username=usernameEdit.getText().toString().trim();
                String password=passwordEdit.getText().toString().trim();
                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
                    ArrayList<User> users=mDBOpenHelper.getAllData();
                    boolean match1=false;
                    boolean match2=false;
                    for(int i=0;i<users.size();i++){
                        User user=users.get(i);
                        if((username.equals(user.getEmail())&&password.equals(user.getPassword()))||(username.equals(user.getPhonenumber())&&password.equals(user.getPassword())||(username.equals(user.getName())&&password.equals(user.getPassword())))){
                            match1=true;
                            if(remember.isChecked()){
                                editor.putString("username",username);
                                editor.putString("password",password);
                                editor.putBoolean("flag",true);
                                match2=true;
                                editor.apply();

                            }else{
                                editor.putString("username",username);
                                editor.putBoolean("flag",false);
                                editor.putString("password","");
                                match2=false;
                                editor.apply();
                            }
                        }else{
                            match1=false;
                        }
                        if(match1){
                            if(match2){
                                Toast.makeText(LoginActivity.this,"成功记住密码",Toast.LENGTH_SHORT).show();
                                remember.setChecked(true);
                            }
                            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();

//                            Intent intent1=new Intent(LoginActivity.this,InforActivity.class);
//                            intent1.putExtra("username",usernameEdit.getText().toString().trim());
//                            intent1.putExtra("password",passwordEdit.getText().toString().trim());
//                            startActivity(intent1);
//                            finish();

                            Runnable target;
                            final Thread thread=new Thread(){
                                @Override
                                public void  run(){
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            Intent intent1=new Intent(LoginActivity.this,MainActivity.class);
                                            intent1.putExtra("username",usernameEdit.getText().toString().trim());
                                            intent1.putExtra("password",passwordEdit.getText().toString().trim());
                                            startActivity(intent1);
                                        }
                                    },2000);


                                }
                            };
                            thread.start();
                            //break;

                        }else{
                            Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                            //break;
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;

        }



    }
}


