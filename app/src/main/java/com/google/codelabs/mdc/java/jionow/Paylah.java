package com.google.codelabs.mdc.java.jionow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Paylah extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paylahview);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://www.dbs.com/sandbox/api/sg/v1/oauth/authorize?1939656310615659952&client_id=c8f3d2ba-9978-4a88-8f11-1c60fb3e87e3&response_type=code&scope=Read&redirect_uri= http://mytestapp/redirect_uri");
        }


    @Override
    protected void onPause() {
        super.onPause();
    }


}
