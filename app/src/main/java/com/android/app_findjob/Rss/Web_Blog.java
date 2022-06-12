package com.android.app_findjob.Rss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.app_findjob.R;

public class Web_Blog extends AppCompatActivity {
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_blog);


        web=findViewById(R.id.webview);
        Intent intent=getIntent();
        String link=intent.getStringExtra("LinkTinTuc");
        web.loadUrl(link);
        web.setWebViewClient(new WebViewClient());
    }
}