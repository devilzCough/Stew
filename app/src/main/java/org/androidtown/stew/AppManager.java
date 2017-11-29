package org.androidtown.stew;

import android.webkit.WebView;

/**
 * Created by limjeonghyun on 2017. 11. 29..
 */

public class AppManager {

    private static AppManager appManager;
    private BackgroundWebview webView;

    public static AppManager getInstance(){
        if(appManager == null) appManager = new AppManager();
        return appManager;
    }
    WebView getWebView(){
        return webView;
    }
    void setWebView(BackgroundWebview webV){
        webView = webV;
    }

}
