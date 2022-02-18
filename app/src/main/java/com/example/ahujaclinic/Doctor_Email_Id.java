package com.example.ahujaclinic;

public class Doctor_Email_Id {
    private String emailid, type;

    public Doctor_Email_Id(String emailid,  String type) {
        this.emailid = emailid;

        this.type = type;
    }

    public Doctor_Email_Id() {
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
