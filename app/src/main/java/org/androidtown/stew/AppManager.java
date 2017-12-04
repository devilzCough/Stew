package org.androidtown.stew;

import android.webkit.WebView;

/**
 * Created by limjeonghyun on 2017. 11. 29..
 */

public class AppManager {

    private static AppManager appManager;
    private BackgroundWebview webView;
    private String userID,userName,userPW;

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
    String getUserID(){return userID;}
    String getUserName(){return userName;}
    String getUserPW(){return  userPW;}

    void setUserID(String id){userID = id;}
    void setUserName(String name){userName = name;}
    void setUserPW(String pw){userPW = pw;}
}
