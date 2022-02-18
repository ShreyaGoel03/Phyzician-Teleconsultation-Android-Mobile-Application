package com.example.ahujaclinic;

public class Retrieve_Appointments {
    private String phone,date,slot;

    public Retrieve_Appointments(String phone, String date, String slot) {
        this.phone = phone;
        this.date = date;
        this.slot = slot;
    }

    public Retrieve_Appointments() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}
