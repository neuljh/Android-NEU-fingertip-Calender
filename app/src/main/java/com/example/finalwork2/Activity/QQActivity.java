package com.example.finalwork2.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalwork2.Database.DBOpenHelperQQ;
import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QQActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_back,bt_confirm;
    private EditText et_getqq,et_getname;
    private Intent intent;
    private String avatorUrl="";
    private boolean sign=false;
    private ImageView iv_getimag;
    private String username;
    private DBOpenHelperQQ dbOpenHelperQQ;

    @SuppressLint("HandlerLeak")
    private Handler nameHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            et_getname.setText((String) msg.obj);
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //Glide设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(100);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(20, 20);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(QQActivity.this)
                    .load((String) msg.obj)
                    .error(getResources().getDrawable(R.mipmap.image))
                    .apply(options)
                    .into(iv_getimag);
        }
    };

    public void getInfo(String qq) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://api.isoyu.com/qq/qq.asp?qq=" + qq).build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Log.e("test", "result: " + result);
                    JSONObject object = new JSONObject(result);
                    if (!object.has("code")) {
                        Message message = Message.obtain();
                        //将Bitmap对象与消息绑定
                        message.obj = object.getString("name");
                        //通过Handler发送消息
                        nameHandler.sendMessage(message);

                        Message message2 = Message.obtain();
                        message2.obj = object.getString("img");
                        avatorUrl = object.getString("img");
                        imageHandler.sendMessage(message2);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void init_view(){
        iv_getimag=findViewById(R.id.iv_getimg);
        bt_back=findViewById(R.id.bt_qq_back);
        bt_back.setOnClickListener(this);

        bt_confirm=findViewById(R.id.bt_qq_confirm);
        bt_confirm.setOnClickListener(this);

        et_getqq=findViewById(R.id.et_qq);
        et_getname=findViewById(R.id.et_getname);

        et_getqq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_getqq.getText().toString().length()>=6&&et_getqq.getText().toString().length()<15){
                    getInfo(et_getqq.getText().toString());
                    sign=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);

        dbOpenHelperQQ=new DBOpenHelperQQ(this);
        username=getIntent().getStringExtra("username");

        init_view();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_qq_back:
                startActivity(new Intent(QQActivity.this,LoginActivity.class));
                break;
            case R.id.bt_qq_confirm:
                String qqtext=et_getqq.getText().toString();
                String nametext=et_getname.getText().toString();
                if(qqtext.length()!=0&&nametext.length()!=0&&avatorUrl.length()!=0){
                    String app_idtext= ContentUtils.encrypByMd5(nametext);
                    dbOpenHelperQQ.add(username,qqtext,app_idtext,avatorUrl,nametext);
                    Toast.makeText(QQActivity.this,"绑定QQ成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(QQActivity.this,"绑定QQ失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
