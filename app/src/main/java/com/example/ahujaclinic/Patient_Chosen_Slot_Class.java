package com.example.ahujaclinic;

public class Patient_Chosen_Slot_Class {
    private String time;
    private int payment,status;
    private String question,patient_name, transaction_id;


    public Patient_Chosen_Slot_Class() {
    }

    public Patient_Chosen_Slot_Class(String time, int payment, String question, String patient_name, int status, String transaction_id) {
        this.time = time;
        this.payment = payment;
        this.question = question;
        this.patient_name = patient_name;
        this.status = status;
        this.transaction_id = transaction_id;

    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}