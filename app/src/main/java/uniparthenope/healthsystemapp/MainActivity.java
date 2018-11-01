package uniparthenope.healthsystemapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.konstantinschubert.writeinterceptingwebview.WriteHandlingWebResourceRequest;
import com.konstantinschubert.writeinterceptingwebview.WriteHandlingWebViewClient;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String homepage;
    private Map<String, String> header = new HashMap<>();
    private WebView myWebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar2);

        Intent in = getIntent();
        homepage = "http://34.211.204.250";
        header.put("token", in.getStringExtra("token"));
        String urlresponse = in.getStringExtra("url");

        myWebView = findViewById(R.id.web);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                if(request.getUrl().toString().startsWith(homepage)){
                    view.loadUrl(request.getUrl().toString(),header);
                    return true;
                }
                return false;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(homepage+urlresponse, header);
    }
    @Override
    public void onBackPressed() {
        if (myWebView.isFocused() && myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                    .setMessage("Exiting app you must log in again. Sure?")
                    .setCancelable(false)
                    .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Nope", null)
                    .show();
        }
    }
}

