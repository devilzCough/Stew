package org.androidtown.stew;

/**
 * Created by iseungjin on 2017. 12. 1..
 */


public class CustomReservCard {

    int userNum;
    String strStudentId;
    String strStudentName;
    String strPid;

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
    public String getStrPid() { return strPid; }
    public boolean getUserFlag() { return userFlag; }

    public void setStrStudentId(String id){
        strStudentId = id;
    }
    public void setStrStudentName(String name){
        strStudentName = name;
    }
    public void setStrPid(String pid) { strPid = pid; }
    public void setUserFlag(boolean flag) { userFlag = flag; }
}
