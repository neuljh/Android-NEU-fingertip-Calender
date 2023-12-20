package com.example.finalwork2.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.finalwork2.Database.DBOpenHelperUser;
import com.example.finalwork2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener{
    private Button etback;
    private Button etmodify;
    private DBOpenHelperUser mdbopenhelper;
    private EditText username;
    private EditText email;
    private EditText phonenum;
    private EditText password;
    private EditText repassword;
    private EditText  name;
    private EditText school;
    private RadioButton sexmale;
    private RadioButton sexfemale;
    private EditText et_yanzhengma;
    private Button bt_getnumber;
    private Random random=new Random();

    String messageContent = "";

    String[] permissions = new String[]{Manifest.permission.SEND_SMS};
    List<String> mPermissionList = new ArrayList<>();

    // private ImageView welcomeImg = null;
    private static final int PERMISSION_REQUEST = 1;


    public void Init(){
        etback=findViewById(R.id.bt_back);
        etmodify=findViewById(R.id.bt_r_r);
        username=findViewById(R.id.et_username_r);
        email=findViewById(R.id.et_email_r);
        phonenum=findViewById(R.id.et_phonenum_r);
        password=findViewById(R.id.et_password_r);
        repassword=findViewById(R.id.et_password_rr);
        name=findViewById(R.id.et_name);
        school=findViewById(R.id.et_school);
        sexmale=findViewById(R.id.rb_male);
        sexfemale=findViewById(R.id.rb_female);
        etback.setOnClickListener(this);
        etmodify.setOnClickListener(this);

        et_yanzhengma=findViewById(R.id.et_yanzhengma);
        bt_getnumber=findViewById(R.id.bt_getnumber);
        bt_getnumber.setOnClickListener(this);

    }

    public void send_numbers(){
        /*ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS} , 1);*/
        checkPermission();
        //获取资源
        //发送短信的响应
        bt_getnumber.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                //获取发送地址和发送内容
                String messageAddress = phonenum.getText().toString();

                for(int index=0;index<6;index++){
                    messageContent=messageContent+random.nextInt(10);
                }

                //构建一取得default instance的SmsManager对象
                SmsManager smsManager = SmsManager.getDefault();

                //检查输入内容是否为空,这里为了简单就没有判断是否是号码,短信内容长度的限制也没有做
                if(messageAddress.trim().length()!=0 && messageContent.trim().length()!=0)
                {
                    //设置短信长度
                    if(messageContent.length() > 70){
                        ArrayList<String> texts=smsManager.divideMessage(messageContent);
                        for (String text:texts){
                            smsManager.sendTextMessage(messageAddress, null, text, null, null);
                        }
                    }else {
                        smsManager.sendTextMessage(messageAddress, null, messageContent, null, null);
                    }



                    //提示发送成功
                    Toast.makeText(RegisteredActivity.this, "发送成功", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(RegisteredActivity.this, "发送手机号或者内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);


        Init();
        mdbopenhelper=new DBOpenHelperUser(this);
        send_numbers();

    }

    // 检查权限

    private void checkPermission() {
        mPermissionList.clear();
        //判断哪些权限未授予
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST);
        }
    }

    /**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_back:
                Intent intent0=new Intent(RegisteredActivity.this,LoginActivity.class);
                startActivity(intent0);
                finish();
                break;
            case R.id.bt_r_r:
                String username0=username.getText().toString().trim();
                String email0=email.getText().toString().trim();
                String phonenum0=phonenum.getText().toString().trim();
                String password0=password.getText().toString().trim();
                String repassowrd0=repassword.getText().toString().trim();
                String  name0=name.getText().toString().trim();
                String school0=school.getText().toString().trim();
                String yanzhengma=et_yanzhengma.getText().toString().trim();
                String sex;
                if(sexmale.isChecked()&&!sexfemale.isChecked()){
                    sex="男";
                }else if(sexfemale.isChecked()&&!sexmale.isChecked()){
                    sex="女";
                }else{
                    sex="暂无";
                }
                if(!TextUtils.isEmpty(repassowrd0)&&!TextUtils.isEmpty(username0)&&!TextUtils.isEmpty(phonenum0)&&!TextUtils.isEmpty(password0)&&!TextUtils.isEmpty(name0)&&!TextUtils.isEmpty(school0)&&sex.equals("男")||sex.equals("女")){
                    if(password0.equals(repassowrd0)&&messageContent.equals(yanzhengma)){
                        mdbopenhelper.add(username0,password0,email0,phonenum0,name0,school0,sex);
                        Intent intent2=new Intent(RegisteredActivity.this,LoginActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(RegisteredActivity.this,"验证通过，注册成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisteredActivity.this,"两次密码输入不一致，注册失败",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisteredActivity.this,"必填选项不能为空",Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }
}


