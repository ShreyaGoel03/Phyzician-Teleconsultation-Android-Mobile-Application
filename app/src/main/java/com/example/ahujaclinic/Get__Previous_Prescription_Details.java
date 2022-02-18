package com.example.ahujaclinic;

public class Get__Previous_Prescription_Details {

    private String name,date,time,phone_no;

    public Get__Previous_Prescription_Details() {
    }

    public Get__Previous_Prescription_Details(String name, String date, String time, String phone_no) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.phone_no = phone_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
