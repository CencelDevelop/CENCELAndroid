package mx.com.cencel.comercial.cencel.activities.stores;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import mx.com.cencel.comercial.cencel.R;

public class MapStore extends ActionBarActivity {
   String var;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapsucur);

        Bundle extras = getIntent().getExtras();
         if (extras!= null)
        {
        var =(String) extras.get ("cordenada");

        }



        WebView webview = (WebView) findViewById(R.id.map);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);

        StringBuilder builder = new StringBuilder();
        builder.append("https://www.google.com/maps/?t=k&q=");
        builder.append(var);
        //builder.append(",12z");


        webview.loadUrl(builder.toString());
    }


}