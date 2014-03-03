package me.naiyu.android.app.blog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	
	private static final String URL = "http://naiyu.me/";
	
	private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initWebView();
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) 
    private void initWebView() {
    	mWebView = (WebView) findViewById(R.id.webview_main);
    	mWebView.getSettings().setJavaScriptEnabled(true);
    	mWebView.getSettings().setBuiltInZoomControls(true);
    	mWebView.getSettings().setSupportZoom(true);
    	mWebView.getSettings().setUseWideViewPort(true);
    	mWebView.getSettings().setLoadWithOverviewMode(true);
    	mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
    	mWebView.setWebViewClient(new MyWebViewClient());
    	mWebView.loadUrl(URL);
    	mWebView.setInitialScale(100);
    	
    	if (Build.VERSION.SDK_INT > 11) {
    		mWebView.getSettings().setDisplayZoomControls(false);
    	}
    	
    }
    
    private class MyWebViewClient extends WebViewClient {
    	@Override
    	public void onReceivedError(WebView view, int errorCode,
    			String description, String failingUrl) {
    		super.onReceivedError(view, errorCode, description, failingUrl);
    	}
    	
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		view.loadUrl(url);
    		return true;
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
    		mWebView.goBack();
    		return true;
    	}
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle("退出");
    		builder.setMessage("是否退出？");
    		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
    		builder.setNegativeButton("否", null);
    		builder.create().show();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
}
