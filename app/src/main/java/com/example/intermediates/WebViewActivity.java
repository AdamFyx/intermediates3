package com.example.intermediates;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;


public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        //获得控件
        WebView webView = (WebView) findViewById(R.id.wv_webview);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        //设置本地调用对象及其接口
        //webView.addJavascriptInterface(new JavascriptObject(),"myObj");
        //访问网页
        webView.loadUrl("file:///android_asset/web/main.html");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });


    }

}