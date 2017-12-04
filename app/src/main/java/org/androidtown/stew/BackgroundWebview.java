package org.androidtown.stew;

import android.content.Context;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class BackgroundWebview extends WebView {

    public final String url_login = "https://library.sejong.ac.kr/identity/Login.ax?url=%2Fstudyroom%2FMain.ax";
    public final String url_study = "http://library.sejong.ac.kr/studyroom/Main.ax";
    private static final String TAG = "<< Webview :: LOGIN >>";

    String userID,userPW;
    int loadCheck ,loginFlag;
    String currentURL,cookies;

    public BackgroundWebview(Context context) {
        super(context);
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);

        this.setWebViewClient(new myWebClientView());
    }

    public void web_login(String id, String pw){
        userID = id;
        userPW = pw;

        loadCheck = 0;
        loginFlag = -1;
        loadUrl(url_login);
    }

    public int getLoginflag(){
        return loginFlag;
    }

    public class myWebClientView extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.equals(url_login) && loadCheck < 3) {
                Log.d("msg", "url load finished");
                String script = "javascript:function afterLoad() {"
                        + "document.getElementById('userID').value = '" + userID + "';"
                        + "document.getElementById('password').value = '" + userPW + "';"
                        + "identify.goLogin()"
                        + "};"
                        + "afterLoad();";
                view.loadUrl(script);
                loadCheck++;
            }
            if(url.equals(url_study) && loginFlag != 1) {
                Log.d(TAG,"login success "+loadCheck);
                currentURL = getUrl();
                CookieManager cookieManager = CookieManager.getInstance();
                cookies = cookieManager.getCookie(currentURL);
                AppManager.getInstance().setCookies(cookies);
                Log.d(TAG,"cookies : "+cookies);
                loginFlag = 1;
            }
            else if(url.equals(url_login) && loadCheck > 2){
                loginFlag = 0;
                Log.d(TAG,"login fail"+loadCheck);
            }
        }
    }
}
