package com.samsoft.xpendify.activity.miscellaneous;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.samsoft.xpendify.R;

/**
 * Created by Fred on 23-Oct-15.
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView webView = (WebView) findViewById(R.id.help);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/help.html");

    }
}
