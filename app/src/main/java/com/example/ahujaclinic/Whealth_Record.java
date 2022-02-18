package com.example.ahujaclinic;

public class Whealth_Record {

    private  String Start_Date,End_Date;

    public Whealth_Record() {
    }

    public Whealth_Record(String start_Date, String end_Date) {
        Start_Date = start_Date;
        End_Date = end_Date;
    }

    public String getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public String getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(String end_Date) {
        End_Date = end_Date;
    }
}
