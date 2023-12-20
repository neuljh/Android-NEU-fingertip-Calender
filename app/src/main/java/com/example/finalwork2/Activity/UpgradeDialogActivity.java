package com.example.finalwork2.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalwork2.R;
import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;


public class UpgradeDialogActivity extends Activity implements View.OnClickListener{
    private final String TAG="weather";
    private Button bt_confirm,bt_cancel;
    private TextView tv_city,tv_weather,tv_temperature,tv_suggestion,tv_quality;
    private String cityID=new String();

    public void init_view(){
        bt_confirm=findViewById(R.id.bt_confirm);
        bt_cancel=findViewById(R.id.bt_cancel);
        tv_city=findViewById(R.id.tv_weather_city);
        tv_weather=findViewById(R.id.tv_weather_weather);
        tv_suggestion=findViewById(R.id.tv_weather_suggestion);
        tv_quality=findViewById(R.id.tv_weather_quality);

        bt_confirm.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_dialog);

        set_view();
        init_view();
        HeConfig.init("HE2205092026181330","878d2090a79b4c6dbe9bf4f0ebff5166");
        HeConfig.switchToDevService();

        QWeather.getWeatherNow(UpgradeDialogActivity.this, "CN101070101", new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
                System.out.println("Weather Now Error:" + new Gson());
            }

            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                String weather=null,temperature=null,city=null,district=null,cid=null;
                if (Code.OK.getCode().equalsIgnoreCase(weatherBean.getCode().toString())) {
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                    weather=now.getText();
                    temperature=now.getTemp();
                    city="沈阳市";
                    district="浑南区";
                    cid="CN101070101";
                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                    return;
                }
                cityID=cid;
                tv_city.setText(city+"市"+district);
                tv_temperature.setText(temperature+"摄氏度");
                tv_weather.setText(weather);
            }
        });
    }

//        /** 设置宽度为屏幕的0.9*/
//        WindowManager windowManager = getWindowManager();
//        /* 获取屏幕宽、高 */
//        Display display = windowManager.getDefaultDisplay();
//        /* 获取对话框当前的参数值 */
//        WindowManager.LayoutParams p = getWindow().getAttributes();
//        /* 宽度设置为屏幕的1 */
//        p.width = (int) (display.getWidth() * 0.9);
//        /* 设置透明度,0.0为完全透明，1.0为完全不透明 */
//        p.alpha = 0.75f;
//        /* 设置布局参数 */
//        getWindow().setAttributes(p);
//
////        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        /* 设置点击弹框外部不可消失 */
//        setFinishOnTouchOutside(false);




    public void set_view(){
        /** 设置宽度为屏幕的0.9*/
        WindowManager windowManager = getWindowManager();
        /* 获取屏幕宽、高 */
        Display display = windowManager.getDefaultDisplay();
        /* 获取对话框当前的参数值 */
        WindowManager.LayoutParams p = getWindow().getAttributes();
        /* 宽度设置为屏幕的1 */
        p.width = (int) (display.getWidth() * 0.9);
        /* 设置透明度,0.0为完全透明，1.0为完全不透明 */
        p.alpha = 0.75f;
        /* 设置布局参数 */
        getWindow().setAttributes(p);

//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);

        /* 设置点击弹框外部不可消失 */
        setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_confirm:
                finish();
                break;
            case R.id.bt_cancel:
                finish();
                break;
        }
    }
}
