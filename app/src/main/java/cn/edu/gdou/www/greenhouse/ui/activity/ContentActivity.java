package cn.edu.gdou.www.greenhouse.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.edu.gdou.httpwww.greenhouse.R;
import dmax.dialog.SpotsDialog;


/**
 * Created by Veyron on 2017/1/29.
 * Function：用于展示资讯详情
 */
public class ContentActivity extends AppCompatActivity {

    private WebView mWebView;
    private String url;
    private SpotsDialog mDialog;

    // 用于记录出错页面的url 方便重新加载
    private String mFailingUrl = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mDialog = new SpotsDialog(this,"loading....");



        url=getIntent().getStringExtra("url");
        mWebView = (WebView) findViewById(R.id.webview);
        initWebViewSettings();
        initWebview();
    }



    private void initWebViewSettings() {
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                            v.requestFocusFromTouch();
                        }
                        break;
                }
                return false;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.getSettings().setUseWideViewPort(true);// 这个很关键
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        //设置是否可缩放，会出现缩放工具（若为true则上面的设值也默认为true）
        mWebView.getSettings().setBuiltInZoomControls(true);
        //隐藏缩放工具
        mWebView.getSettings().setDisplayZoomControls(false);


        if (Build.VERSION.SDK_INT < 19) {
            if (Build.VERSION.SDK_INT >8) {
                mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            }
        }
    }
    private void initWebview() {
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(url);
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 在webview加载下一页，而不会打开浏览器
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(mDialog !=null && mDialog.isShowing())
                mDialog.dismiss( );
        }
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            mFailingUrl = failingUrl;//记录失败的url
            if(mDialog !=null && mDialog.isShowing())
                mDialog.dismiss( );
            //view.setLayoutParams();
            view.addJavascriptInterface(new AndroidAndJSInterface(), "Android");
            view.loadUrl("file:///android_asset/error.html");//添加显示本地文件
        }

    }

    class AndroidAndJSInterface {
        @JavascriptInterface
        public void reLoad(){
            // mWebView.loadUrl(url);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //重新加载
                    if (mFailingUrl != null){
                        mWebView.loadUrl(mFailingUrl);
                    }
                }
            });
        }
    }
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
            mWebView.resumeTimers();
        }
    }
    @Override
    public void onPause() {
        if (mWebView != null) {
            mWebView.onPause();
            mWebView.pauseTimers();
        }
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.clearCache(true);
        mWebView.destroy();
    }
}
