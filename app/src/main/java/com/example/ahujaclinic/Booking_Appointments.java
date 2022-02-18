package com.example.ahujaclinic;

public class Booking_Appointments {

    private String phone;
    private int value;

    public Booking_Appointments() {
    }

    public Booking_Appointments(int value, String phone) {
        this.value = value;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
