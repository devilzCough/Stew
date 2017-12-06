package org.androidtown.stew;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    public static final int MODE_TABLE = 3;


    private String htmlPageUrl; //파싱할 홈페이지의 URL주소
    private String cookies;
    private String userID,userName;
    private String selectQuery;
    private String bookID;

    private int mode,floor;

    ArrayList<String> myBookList;
    ArrayList<String> myBookIdList;

    ArrayList<String> reserveTableTime;
    ArrayList<String> reserveTableUser;


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

        htmlPageUrl = "https://library.sejong.ac.kr/studyroom/List.axa";
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

    public void tableInfo(int roomID,int f) {

        floor = f;
        htmlPageUrl = "http://library.sejong.ac.kr/studyroom/BookingTable.axa?roomId="+roomID;
        selectQuery = "table.tb05 th";
        mode = MODE_TABLE;

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

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
            if(mode == MODE_LISTDETAIL){
                AppManager.getInstance().getMyListFragment().createBookList(myBookList);
            }
            else if(mode ==MODE_TABLE){
                AppManager.getInstance().getFloorFragment().createTable(reserveTableTime,reserveTableUser);
            }
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

                    htmlPageUrl = "http://library.sejong.ac.kr/studyRoom/UserFind.axa";

                     document = Jsoup.connect(htmlPageUrl)
                            .header("Cookie", cookies)
                            .header("Origin", "http://library.sejong.ac.kr")
                            .header("User-Agent", "Mozilla/5.0")
                            .header("Referer", "http://library.sejong.ac.kr/studyroom/Request.ax?roomId=14")
                            .header("Content-type","application/x-www-json-urlencoded; charset=utf-8")
                            .data("altPid", userID)
                            .data("name",userName)
                            .data("userBlockUser","Y")
                            .data("year","2017")
                            .data("month","12")
                            .data("day","07")
                            .data("_","")
                            .post();

                    System.out.println(document.text());

                }
                else if(mode == MODE_MYLIST){
                    elements = elements.get(1).select("td a[href*=goStudyRoomBookingDetail]");

                    for(Element e : elements){
                        String[] tempArray = e.attr("href").split("\'");
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
                }
                else if(mode == MODE_TABLE){
                    SimpleDateFormat dataFormat = new SimpleDateFormat("d", Locale.KOREA);
                    String today = dataFormat.format(new Date());
                    int todayNum = Integer.parseInt(today);

                    reserveTableTime = new ArrayList<String>();
                    reserveTableTime.clear();
                    reserveTableUser = new ArrayList<String>();
                    reserveTableUser.clear();

                    for(Element e : elements){
                        if(e.text().equals("날짜/시간")){
                            reserveTableTime.add("시간/날짜");
                        }
                        else{
                            reserveTableTime.add(e.text());
                        }
                    }
                    selectQuery = "table.tb05 tr";
                    document = Jsoup.connect(htmlPageUrl).header("cookie",cookies).get();

                    elements= document.select(selectQuery);
                    for(int i=0;i<7;i++){
                        System.out.println(elements.get(i+todayNum).text());
                        reserveTableUser.add(elements.get(i+todayNum).text());
                    }
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
