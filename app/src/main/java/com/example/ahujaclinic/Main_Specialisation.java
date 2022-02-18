package com.example.ahujaclinic;

public class Main_Specialisation {

    private Integer specialisation_pic;
    private String specialisation_type;

    public Main_Specialisation() {
    }

    public  Main_Specialisation(Integer specialisation_pic, String specialisation_type){

        this.specialisation_pic=specialisation_pic;
        this.specialisation_type=specialisation_type;
    }

    public Integer getSpecialisation_pic() {
        return specialisation_pic;
    }

    public String getSpecialisation_type() {
        return specialisation_type;
    }

    public void setSpecialisation_pic(Integer specialisation_pic) {
        this.specialisation_pic = specialisation_pic;
    }

    public void setSpecialisation_type(String specialisation_type) {
        this.specialisation_type = specialisation_type;
    }
}
