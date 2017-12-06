package org.androidtown.stew;

/**
 * Created by iseungjin on 2017. 12. 7..
 */

public class ReservationInfo {

    int infoNum, roomId;
    int year, month, day, startHour, hours;
    String purpose;

    public ReservationInfo(int num) {
        infoNum = num;
    }

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    public int getStartHour() { return startHour; }
    public int getHours() { return hours; }
    public String getPurpose() { return purpose; }

    public void setYear(int _year) { year = _year; }
    public void setMonth(int _month) { month = _month; }
    public void setDay(int _day) { day = _day; }
    public void setStartHour(int _startHour) { startHour = _startHour; }
    public void setHours(int _hours) { hours = _hours; }
    public void setPurpose(String _purpose) { purpose = _purpose; }
}
