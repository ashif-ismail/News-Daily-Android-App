package me.ashif.newsapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;


public class NewsInDetailActivity extends AppCompatActivity {

    public String _newsLink;
    WebView nweb;
    ProgressDialog pDailog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_in_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        nweb = (WebView) findViewById(R.id.nweb);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nweb.getSettings().setJavaScriptEnabled(true);
            _newsLink = extras.getString("url");
            showDialog();
            nweb.loadUrl(_newsLink);
        }


    }

    private void showDialog() {
        nweb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDailog = ProgressDialog.show(NewsInDetailActivity.this,null,"Reading News Story");
                pDailog.setCanceledOnTouchOutside(true);

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                pDailog.dismiss();
                super.onPageFinished(view, url);
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        if(pDailog.isShowing())
       {
            pDailog.dismiss();
        }
    }
}
