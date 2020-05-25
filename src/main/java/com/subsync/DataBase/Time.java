package com.subsync.DataBase;

public class Time {
    private int hour;
    private int minute;
    private int second;
    private int miliSecond;

    public Time(int h, int m , int s, int ms) {
        hour = h;
        minute = m;
        second = s;
        miliSecond = ms;
    }

    public Time(String h, String m , String s, String ms) {
        hour = Integer.parseInt(h);
        minute = Integer.parseInt(m);
        second = Integer.parseInt(s);
        miliSecond = Integer.parseInt(ms);
    }

    private void timeChecker() throws InvalidTimeException {
        while (miliSecond >= 1000 || miliSecond < 0) {
            if (miliSecond >= 1000) {
                miliSecond -= 1000;
                second++;
            } else if (miliSecond < 0){
                miliSecond += 1000;
                second--;
            }
        }

        while (second >= 60 || second < 0) {
            if (second >= 60) {
                second -= 60;
                minute++;
            } else if (second < 0){
                second += 60;
                minute--;
            }
        }

        while (minute >= 60 || minute < 0) {
            if (minute >= 60) {
                minute -= 60;
                hour++;
            } else if (minute < 0){
                minute += 60;
                hour--;
            }
        }

        if (hour < 0){
            throw new InvalidTimeException();
        }
    }

    public void increaseTime(int h, int m , int s, int ms) throws InvalidTimeException {
        hour += h;
        minute += m;
        second += s;
        miliSecond += ms;

        timeChecker();
    }

    public void decreaseTime(int h, int m , int s, int ms) throws InvalidTimeException {
        hour -= h;
        minute -= m;
        second -= s;
        miliSecond -= ms;

        timeChecker();
    }

    public String toStringWithComma(){
        return String.format("%02d:%02d:%02d,%03d", hour, minute, second, miliSecond);
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getSecond() {
        return second;
    }

    public int getMiliSecond() {
        return miliSecond;
    }
}
