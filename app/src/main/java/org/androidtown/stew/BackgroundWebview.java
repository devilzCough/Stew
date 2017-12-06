package org.androidtown.stew;

import android.content.Context;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

/**
 * Created by limjeonghyun on 2017. 11. 30..
 */

public class BackgroundWebview extends WebView {

    public final String url_login = "https://library.sejong.ac.kr/identity/Login.ax?url=%2Fstudyroom%2FMain.ax";
    public final String url_study = "http://library.sejong.ac.kr/studyroom/Main.ax";
    String url_reservation = "http://library.sejong.ac.kr/studyroom/Request.ax?roomId=";
    private static final String TAG = "<< Webview :: LOGIN >>";

    String userID, userPW;
    int loadCheck ,loginFlag;
    String currentURL,cookies;

    int userNum;
    String studentId, studentName;
    boolean reservFlag;

    int nUsers;
    int year, month, day, startHour, hours;
    String strPurpose;
    ArrayList<CustomReservCard> items;

    public BackgroundWebview(Context context) {

        super(context);
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);

        this.setWebViewClient(new myWebClientView());
    }

    // Login
    public void web_login(String id, String pw) {

        userID = id;
        userPW = pw;

        loadCheck = 0;
        loginFlag = -1;
        loadUrl(url_login);
    }

    public int getLoginflag(){
        return loginFlag;
    }

    // Reservation
    public void load_reservationPage(int roomId) {

        reservFlag = false;
        url_reservation += roomId;
        loadUrl(url_reservation);
    }

    public void userCheck(int num, String id, String name) {

        userNum = num;
        studentId = id;
        studentName = name;
        url_reservation += "11";
        loadUrl(url_reservation);
    }

    public void web_reservation(int y, int m, int d, int sH, int h, String purpose, ArrayList<CustomReservCard> users) {

        reservFlag = true;
        year = y;
        month = m;
        day = d;
        startHour = sH;
        hours = h;
        strPurpose = purpose;

        items = users;
        nUsers = items.size();
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


            if (url.equals(url_reservation) && !reservFlag) {
                /*String script = "javascript:function afterLoad() {"
                        + "document.getElementById('altPid" + userNum + "').value = '" + studentId + "';"
                        + "document.getElementById('name" + userNum + "').value = '" + studentName + "';"
                        + "studyroom.goStudyRoomUserInfo(" + userNum + ",'Y')"
                        + "};"
                        + "afterLoad();";*/
                String script = "javascript:function afterLoad() {"
                        + "document.getElementById('altPid').value = '" + studentId + "';"
                        + "document.getElementById('name').value = '" + studentName + "';"
                        + "document.getElementById('year').value = '2017';"
                        + "document.getElementById('month').value = '12';"
                        + "document.getElementById('day').value = '09';"
                        + "studyroom.goStudyRoomUserInfo(" + userNum + ",'Y')"
                        + "};"
                        + "afterLoad();";
                view.loadUrl(script);
            } else {
                String script = "javascript:function afterLoad() {"
                        + "document.getElementById('year').value = '" + 2017 + "';"
                        + "document.getElementById('month').value = '" + 12 + "';"
                        + "document.getElementById('day').value = '" + 9 + "';"
                        + "document.getElementById('startHour').value = '" + 10 + "';"
                        + "document.getElementById('hours').value = '" + 1 + "';"
                        + "document.getElementById('mode').value = 'INSERT';";

                for (int i = 0; i < nUsers; i++) {
                    CustomReservCard item = items.get(i);
                    String tmp =  "document.getElementById('altPid" + (i + 1) + "').value = '" + item.getStrStudentId() + "';"
                            + "document.getElementById('name" + (i + 1) + "').value = '" + item.getStrStudentName() + "';";

                    script += tmp;
                }

                String tmp = "document.getElementById('purpose').value = '" + strPurpose + "';"
                        + "};"
                        + "afterLoad();";

                script += tmp;
                view.loadUrl(script);
            }
        }


    }
}
