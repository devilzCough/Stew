package org.androidtown.stew;

/**
 * Created by iseungjin on 2017. 12. 1..
 */


public class CustomBookCard {

    String bookInfoRoom;
    String bookInfoDate;
    String bookInfoTime;
    String bookInfoUser;
    int btnMoreFlag;

    public CustomBookCard (String room,String date,String time,String user) {

        bookInfoRoom = room;
        bookInfoDate = date;
        bookInfoTime = time;
        bookInfoUser = user;
        btnMoreFlag = 0;
    }
    

    public String getBookInfoDate() {
        return bookInfoDate;
    }

    public String getBookInfoRoom() {
        return bookInfoRoom;
    }

    public String getBookInfoTime() {
        return bookInfoTime;
    }

    public String getBookInfoUser() {
        return bookInfoUser;
    }
    public int getBtnMoreFlag(){
        return btnMoreFlag;
    }
    public void setBtnMoreFlag(int flag){
        btnMoreFlag = flag;
    }
}
