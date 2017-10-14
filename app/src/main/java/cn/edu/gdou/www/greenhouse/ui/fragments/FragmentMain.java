package cn.edu.gdou.www.greenhouse.ui.fragments;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.gdou.httpwww.greenhouse.R;
import dmax.dialog.SpotsDialog;


public class FragmentMain extends BaseFragment {


    private String url;
    // 用于记录出错页面的url 方便重新加载
    private String mFailingUrl = null;

    private SpotsDialog mDialog;

    @BindView(R.id.webview_details)
    WebView mWebView;

    @Override
    protected View initView() {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View contentView = mInflater.inflate(R.layout.layout_main, null);
        mDialog = new SpotsDialog(getActivity(),"loading....");
        mDialog.show();
        return contentView;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        url = "https://open.iot.10086.cn/app/browse2?open_id=d60b3f1be8828aec788192bf65284760";
        initWebViewSettings();
        initWebView();
        return rootView;
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
        WebSettings ws = mWebView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);

        // 缩放比例 1
        mWebView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(true);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);


        if (Build.VERSION.SDK_INT < 19) {
            if (Build.VERSION.SDK_INT >8) {
                mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            }
        }
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);
    }

    private void initWebView() {
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("http://news.cctv.com/2017/08/21/ARTIXyi5Lt5GhgMR5bmfpP1e170821.shtml");
        Log.e("TAG","有毒啊。。。"+url);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mWebView.loadUrl("about:blank");
    }

    class MyWebViewClient extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 在webview加载下一页，而不会打开浏览器
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("TAG","开始加载中。。。");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();  // 接受所有网站的证书
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /*if(mDialog !=null && mDialog.isShowing())
                mDialog.dismiss( );*/
            Log.e("TAG","页面加载完成。。。");
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            mDialog.cancel();
        }
        @SuppressWarnings("deprecation")
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e("TAG","onReceivedError"+description);
            mFailingUrl = failingUrl;//记录失败的url

            //view.setLayoutParams();
            view.addJavascriptInterface(new AndroidAndJSInterface(), "Android");
            view.loadUrl("file:///android_asset/error.html");//添加显示本地文件
        }
    }
    class AndroidAndJSInterface{
        @JavascriptInterface
        public void reLoad(){
            // mWebView.loadUrl(url);
            Log.e("TAG", "reload.....");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //重新加载
                    if (mFailingUrl != null){
                        Log.e("TAG","在reload...");
                        mWebView.loadUrl(mFailingUrl);
                    }
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.clearCache(true);
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
    }

}
