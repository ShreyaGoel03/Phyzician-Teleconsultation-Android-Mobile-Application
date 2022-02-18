package com.example.ahujaclinic;

public class BP_Record {
    String sys,dia,pulse;

    public BP_Record() {
    }

    public BP_Record(String sys, String dia, String pulse) {
        this.sys = sys;
        this.dia = dia;
        this.pulse = pulse;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }
}
