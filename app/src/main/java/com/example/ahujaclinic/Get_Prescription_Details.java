package com.example.ahujaclinic;

public class Get_Prescription_Details {

    private String text,date,time,dr,phone;
    private int flag;

    public Get_Prescription_Details() {
    }

    public Get_Prescription_Details(String text, String date, String time, String dr, int flag, String phone) {
        this.text = text;
        this.date = date;
        this.time = time;
        this.dr = dr;
        this.flag = flag;
        this.phone = phone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
