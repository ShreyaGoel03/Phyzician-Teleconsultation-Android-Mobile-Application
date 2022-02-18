package com.example.ahujaclinic;

public class Admin_Appointments {

    private String Date,Time,Doctor,Patient_phone ;

    public Admin_Appointments() {
    }

    public Admin_Appointments(String date, String time, String doctor, String patient_phone) {
        Date = date;
        Time = time;
        Doctor = doctor;
        Patient_phone = patient_phone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getPatient_phone() {
        return Patient_phone;
    }

    public void setPatient_phone(String patient_phone) {
        Patient_phone = patient_phone;
    }
}
