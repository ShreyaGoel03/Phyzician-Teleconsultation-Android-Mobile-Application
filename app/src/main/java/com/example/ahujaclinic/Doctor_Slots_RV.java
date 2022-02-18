package com.example.ahujaclinic;

public class Doctor_Slots_RV {

    private String date, time;
    private int slots_booked;
    private boolean showMenu = false;

    public Doctor_Slots_RV(){

    }

    public Doctor_Slots_RV(String date, String time, int slots_booked) {
        this.date = date;
        this.time = time;
        this.slots_booked = slots_booked;
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

    public int getSlots_booked() {
        return slots_booked;
    }

    public void setSlots_booked(int slots_booked) {
        this.slots_booked = slots_booked;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }
}
