package com.example.ahujaclinic;

public class Patient_Appointment_Class {

    private String dname, email, date, time, specialist;
    private int payment_status, cancelled;

    public Patient_Appointment_Class() {
    }

    public Patient_Appointment_Class(String dname, String email, String date, String time, String specialist, int payment_status, int cancelled) {
        this.dname = dname;
        this.email = email;
        this.date = date;
        this.time = time;
        this.specialist = specialist;
        this.payment_status = payment_status;
        this.cancelled = cancelled;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }
}
