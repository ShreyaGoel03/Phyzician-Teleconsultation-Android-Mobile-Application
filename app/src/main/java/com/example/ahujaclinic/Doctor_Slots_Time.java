package com.example.ahujaclinic;

public class Doctor_Slots_Time {
    private String start_time;
    private String end_time;


    public Doctor_Slots_Time() {
    }

    public Doctor_Slots_Time(String start_time, String end_time) {
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
