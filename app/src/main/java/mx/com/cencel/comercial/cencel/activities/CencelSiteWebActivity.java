package mx.com.cencel.comercial.cencel.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import mx.com.cencel.comercial.cencel.R;

/**
 * Created by iHouse on 01/08/15.
 */
public class CencelSiteWebActivity extends Activity {
    private WebView myWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcencel);

        // configurando el web view
        myWebView = (WebView) findViewById(R.id.cencel_site_webview);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        myWebView.loadUrl(getString(R.string.urlSitioCencel));

        myWebView.setWebViewClient(new WebViewClient() {
           // evita que los enlaces se abran fuera nuestra app en el navegador de android
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               return false;
           }

       });
    }
}
