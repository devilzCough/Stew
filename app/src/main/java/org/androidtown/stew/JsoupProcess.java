package org.androidtown.stew;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by limjeonghyun on 2017. 12. 4..
 */

public class JsoupProcess {

    public static final int MODE_USER = 0;
    public static final int MODE_MYLIST = 1;
    public static final int MODE_LISTDETAIL = 2;


    private String htmlPageUrl; //파싱할 홈페이지의 URL주소
    private String cookies;
    private String userID,userName;
    private String selectQuery;
    private String bookID;

    private int mode;
    private boolean endFlag = false;


    ArrayList<String> myBookList;
    ArrayList<String> myBookIdList;


    public JsoupProcess(){
    }

    public void userInfo() {

        htmlPageUrl = "https://library.sejong.ac.kr/mylibrary/UserDetail.ax";
        selectQuery = "div.contents td";
        mode = MODE_USER;

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }
    public void mylistInfo(){

        htmlPageUrl = "https://library.sejong.ac.kr/studyroom/Main.ax";
        selectQuery = "table.tb01";
        mode = MODE_MYLIST;

        myBookIdList = new ArrayList<String>();


        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }

    public void bookListDetail(){

        selectQuery = "table.tb02 td";
        mode = MODE_LISTDETAIL;

        myBookList = new ArrayList<String>();

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }

    public boolean getEndFlag() {
        return endFlag;
    }

    public ArrayList<String> getListString() {
        return myBookList;
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void result) {
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {

                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                    public X509Certificate[] getAcceptedIssuers(){return new X509Certificate[0];}
                    public void checkClientTrusted(X509Certificate[] certs, String authType){}
                    public void checkServerTrusted(X509Certificate[] certs, String authType){}
                }};

                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                cookies = AppManager.getInstance().getCookies();
                Document document = Jsoup.connect(htmlPageUrl).header("cookie",cookies).get();
                Elements elements= document.select(selectQuery);

                if(mode == MODE_USER) {
                    userName = elements.get(0).text();
                    userID = elements.get(3).text();
                    AppManager.getInstance().setUserName(userName);
                }
                else if(mode == MODE_MYLIST){
                    elements = elements.get(1).select("td a");

                    for(int i=0;i<elements.size();i=i+2){
                        String[] tempArray = elements.get(i).attr("href").split("\'");
                        String bookID = tempArray[1];
                        myBookIdList.add(bookID);
                    }
                    bookListDetail();
                }
                else if(mode==MODE_LISTDETAIL){
                    for(String id : myBookIdList) {
                        htmlPageUrl = "https://library.sejong.ac.kr/studyroom/BookingDetailPrint.ax?bookingId="+id;
                        document = Jsoup.connect(htmlPageUrl).header("cookie",cookies).get();
                        elements= document.select(selectQuery);

                        String bookInfo = elements.get(0).text() + "/" + elements.get(5).text() + "/" + elements.get(7).text();
                        myBookList.add(bookInfo);
                    }
                    endFlag = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
