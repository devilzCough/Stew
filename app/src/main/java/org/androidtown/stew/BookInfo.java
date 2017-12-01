package org.androidtown.stew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iseungjin on 2017. 12. 1..
 */

public class BookInfo {

    int nUsers;
    String strDate, strTime, strRoom, strUsers;
    List<String> userList;

    public BookInfo() {
        userList = new ArrayList<>();
        // String[] strUser = new String[nUsers];
    }

    public String getStrDate() { return strDate; }
    public String getStrTime() { return strTime; }
    public String getStrRoom() { return strRoom; }

    public void setStrDate(String _date) {
        strDate = _date;
    }

    public void setStrTime(String _time) {
        strTime = _time;
    }

    public void setStrRoom(String _room) {
        strRoom = _room;
    }

    public void setStrUsers(String _users) {

    }

    /*CustomBookCard[] item=new CustomBookCard[nInfo];
        for (int i = 0; i < nInfo; i++) {
        item[i] = new CustomBookCard(bookInfo[i]);
        items.add(item[i]);
    }*/
}
