package org.androidtown.stew;

/**
 * Created by iseungjin on 2017. 12. 1..
 */


public class CustomReservCard {

    int userNum;
    String strStudentId;
    String strStudentName;

    boolean userFlag;

    public CustomReservCard(int nUser) {

        userNum = nUser;
        userFlag = true;    // button text = "조회"
    }
    

    public int getUserNum() {
        return userNum;
    }
    public String getStrStudentId() {
        return strStudentId;
    }
    public String getStrStudentName() {
        return strStudentName;
    }
    public boolean getUserFlag() { return userFlag; }

    public void setStrStudentId(String id){
        strStudentId = id;
    }
    public void setStrStudentName(String name){
        strStudentName = name;
    }
    public void setUserFlag(boolean flag) { userFlag = flag; }
}
