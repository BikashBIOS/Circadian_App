package com.example.miniproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Google extends AppCompatActivity {
    WebView webView;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        getSupportActionBar().setTitle("Google");
        webView=findViewById(R.id.web);
        alert=new AlertDialog.Builder(this);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Google.this, "You chose to stay here", Toast.LENGTH_SHORT).show();
            }
        });
        WebSettings webSettings=webView.getSettings();
        webSettings.setAllowContentAccess(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://www.google.com");

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            AlertDialog dialog=alert.create();
            dialog.setMessage("Are you sure you want to exit ?");
            dialog.show();
        }

    }
}

