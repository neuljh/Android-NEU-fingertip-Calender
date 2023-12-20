package com.example.finalwork2.Utils;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.finalwork2.Database.DBOpenHelperClass;
import com.example.finalwork2.JavaClass.SimpleNEUClass;
import com.example.finalwork2.R;

import java.util.List;

public class GetClassActivity extends AppCompatActivity {

    // 请先在AndroidManifest中加入网络权限
    // 在AndroidManifest.xml 中加入下面这句话。搞不懂请参考这个项目里面的AndroidManifest.xml
    // <uses-permission android:name="android.permission.INTERNET"/>

    // 请将以下代码复制到你的Activity
    // 这个Activity必须包含两个元素
    // ① Button按钮 其id为  @+id/webviewLoginSuccessButton
    // ② Webview组件 其id为 @+id/webview 请确保这个元素的长度和宽度足够大以方便操作
    // 你可以在activity_main.xml中直接复制这两个元素

    // 代码起始点 A --------------

    private WebView webView = null;
    private Button webViewLoginSuccessButton = null;
    private Boolean isClassPage = false;
    private Boolean isClassLoadSuccess = false;
    private String classDataString = null; //用于存储课程数据字符串，可以将其存储到文件，无需转换
    private List<SimpleNEUClass> classArray = null; //课程数组
    private DBOpenHelperClass dbOpenHelperClass;

    //忽略Java的XSS注入提示
    @SuppressLint("SetJavaScriptEnabled")

    // 代码终止点 A --------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建布局
        setContentView(R.layout.activity_get_class);
        dbOpenHelperClass=new DBOpenHelperClass(this);

        // 请将以下代码复制到你的Activity的onCreate方法中，需要放置在setContentView(.....)的后面
        // 代码起始点 B --------------

        //绑定元素
        webView = findViewById(R.id.webview);
        webViewLoginSuccessButton = findViewById(R.id.webviewLoginSuccessButton);
        //清除Cookie，避免重复登录
        CookieManager.getInstance().removeAllCookies(value -> {});
        //按钮点击事件
        webViewLoginSuccessButton.setOnClickListener(v -> {
            isClassPage = true;
            final byte[] postDataBytes = ("ignoreHead=1&showPrintAndExport=1&setting.kind=std&s" +
                    "tartWeek=&semester.id=57&ids=73929").getBytes();
            final String classPageUrl = "https://webvpn.neu.edu.cn/http/77726476706e69737468656" +
                    "265737421a2a618d275613e1e275ec7f8/eams/courseTableForStd!courseTable.a" +
                    "ction?vpn-12-o1-219.216.96.4";
            webView.postUrl(classPageUrl, postDataBytes);
        });
        //初始化Webview
        webView.loadUrl("https://webvpn.neu.edu.cn/http/77726476706e69737468656265737421a2a618d" +
                "275613e1e275ec7f8/eams/homeExt.action");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.setWebViewClient(new WebViewClient() {
            //拦截跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            //处理HTTPS
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }


            //处理POST页面加载完成，其他页面不搭理
            @Override
            public void onPageFinished(WebView view, String url) {
                int i = 0;
                if (isClassPage) {
                    isClassPage = false;//复原变量，以免处理失败用户重试的情况
                    //不要试图修改下面这一长串东西
                    final String javaScript = "(function scheduleHtmlParser(){let t=document.getE" +
                            "lementsByTagName(\"html\")[0].innerHTML,e=[];var i=/(?<=var[ \\t]" +
                            "*actTeachers[ \\t]*=.*name:[ \\t]*\\\").*?(?=\\\")/,n=/(?<=activi" +
                            "ty[\\t ]*=[ \\t]*new[\\t ]*TaskActivity\\(.*\\\"[0-9A-Za-z]{4,10}" +
                            "\\([0-9A-Za-z]{4,10}\\)\\\"[\\t ]*,[\\t ]*\\\").*?(?=\\\")/,a=/(?" +
                            "<=activity[\\t ]*=[\\t ]*new[\\t ]*TaskActivity\\(([^\"]*?\\\"){7" +
                            "})[^\"]*?(?=\\\")/,c=/(?<=\\\")[0-1]{53}(?=\\\")/,r=/index[\\t ]" +
                            "*=[\\t ]*[0-6][\\t ]*\\*[\\t ]*unitCount[\\t ]*\\+[\\t ]*[0-9][0" +
                            "-1]?[\\t ]*;/g,s=/(?<=index[\\t ]*=[\\t ]*)[0-6](?=[\\t ]*\\*)/,h=" +
                            "/(?<=index[\\t ]*=[\\t ]*.*?unitCount[\\t ]*\\+[\\t ]*)[0-9][0-1" +
                            "]?/,m=String(t).match(/var[ \\t]*teachers[\\t ]*=[\\t ]*[\\s\\S]*" +
                            "?(?=table0\\.activities\\[index\\]\\[table0\\.activities\\[index\\" +
                            "]\\.length\\][ \\t]*=[\\t ]*activity;[\\r\\n\\t ]*[vt])/g);for(l" +
                            "et t=0;t<m.length;t++){const o=m[t];let u={name:String,position:" +
                            "String,teacher:String,weeks:[],day:Number,sections:[]};u.name=o.m" +
                            "atch(n)[0],u.position=o.match(a)[0],u.teacher=o.match(i)[0],u.da" +
                            "y=Number(o.match(s)[0])+1;let g=o.match(c)[0];for(let t=0;t<g.le" +
                            "ngth;t++)\"1\"==g[t]&&u.weeks.push(t);var l=o.match(r);for(let t" +
                            "=0;t<l.length;t++){const e=l[t];u.sections.push(Number(e.match(h)" +
                            "[0])+1)}e.push(u)}return JSON.stringify(e)})()";
                    //开始获取
                    webView.evaluateJavascript(
                            javaScript,
                            value -> {
                                classDataString = value.substring(1, value.length() - 1)
                                        .replace("\\", "");
                                try {
                                    classArray =
                                            JSONObject.parseArray(
                                                    classDataString,
                                                    SimpleNEUClass.class
                                            );
                                }catch (Exception e){
                                    Toast.makeText(
                                            GetClassActivity.this,
                                            "失败！！！！！！！",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    return;
                                }

                                if (classArray != null){
                                    isClassLoadSuccess = true;
                                }else{
                                    Toast.makeText(
                                            GetClassActivity.this,
                                            "失败！！！！！！！",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    return;
                                }

                                // 请在下面写 获取到课程信息后的逻辑
                                // 课程信息文本 在 变量 classDataString (你可以直接将这个字符串存储到文件)
                                // 课程数组 在 变量 classArray
                                // 如果你的其他线程等待这个结果，你可以在你的线程中反复检查isClassLoadSuccess是否为true。看不懂不要管。

                                //遍历输出课程信息
                                for (SimpleNEUClass s :
                                        classArray) {
                                    Log.e("Class Information",
                                            "  " + System.lineSeparator() + s);
                                }
                                Toast.makeText(
                                        GetClassActivity.this,
                                        "获取成功",
                                        Toast.LENGTH_LONG
                                ).show();

                                for(SimpleNEUClass classes:classArray){
                                    String sections=new String();
                                    String weeks=new String();
                                    for(int index=0;index<classes.getSections().size();index++){
                                        sections=sections+classes.getSections().get(index);
                                        if(index!=classes.getSections().size()-1){
                                            sections=sections+",";
                                        }
                                    }
                                    for(int index=0;index<classes.getWeeks().size();index++){
                                        weeks=weeks+classes.getWeeks().get(index);
                                        if(index!=classes.getWeeks().size()-1){
                                            weeks=weeks+",";
                                        }
                                    }
                                    dbOpenHelperClass.add(String.valueOf(classes.getDay()),classes.getName(),classes.getPosition(),sections,classes.getTeacher(),weeks);
                                    //public SimpleNEUClass(Integer day, String name, String position, List<Integer> sections, String teacher, List<Integer> weeks)
                                }







                                //请在上面写你的逻辑，我是底线
                            }
                    );
                }
            }
        });

        // 代码终止点 B --------------

    }
}