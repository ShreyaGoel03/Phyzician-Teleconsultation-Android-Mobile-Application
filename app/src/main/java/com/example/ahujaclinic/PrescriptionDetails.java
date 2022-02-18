package com.example.ahujaclinic;

import java.util.Date;

public class PrescriptionDetails {

    private String PatientName,PatientGender,Age,DoctorsAddress,PatientHeight,PatientWeight,ConsultationType,Consultation_Date,
            Previous_Lab_Report,Relevent_Point_from_history,Diagnosis_Information,Examinations,Instructions,Rx;
    private int flag;

    public PrescriptionDetails() {
    }

    public PrescriptionDetails(String patientName, String patientGender, String age, String doctorsAddress, String patientHeight, String patientWeight, String consultationType, String consultation_Date, String previous_Lab_Report, String relevent_Point_from_history, String diagnosis_Information, String examinations, String instructions, String rx, int flag) {
        PatientName = patientName;
        PatientGender = patientGender;
        Age = age;
        DoctorsAddress = doctorsAddress;
        PatientHeight = patientHeight;
        PatientWeight = patientWeight;
        ConsultationType = consultationType;
        Consultation_Date = consultation_Date;
        Previous_Lab_Report = previous_Lab_Report;
        Relevent_Point_from_history = relevent_Point_from_history;
        Diagnosis_Information = diagnosis_Information;
        Examinations = examinations;
        Instructions = instructions;
        Rx = rx;
        this.flag = flag;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPatientGender() {
        return PatientGender;
    }

    public void setPatientGender(String patientGender) {
        PatientGender = patientGender;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getDoctorsAddress() {
        return DoctorsAddress;
    }

    public void setDoctorsAddress(String doctorsAddress) {
        DoctorsAddress = doctorsAddress;
    }

    public String getPatientHeight() {
        return PatientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        PatientHeight = patientHeight;
    }

    public String getPatientWeight() {
        return PatientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        PatientWeight = patientWeight;
    }

    public String getConsultationType() {
        return ConsultationType;
    }

    public void setConsultationType(String consultationType) {
        ConsultationType = consultationType;
    }

    public String getConsultation_Date() {
        return Consultation_Date;
    }

    public void setConsultation_Date(String consultation_Date) {
        Consultation_Date = consultation_Date;
    }

    public String getPrevious_Lab_Report() {
        return Previous_Lab_Report;
    }

    public void setPrevious_Lab_Report(String previous_Lab_Report) {
        Previous_Lab_Report = previous_Lab_Report;
    }

    public String getRelevent_Point_from_history() {
        return Relevent_Point_from_history;
    }

    public void setRelevent_Point_from_history(String relevent_Point_from_history) {
        Relevent_Point_from_history = relevent_Point_from_history;
    }

    public String getDiagnosis_Information() {
        return Diagnosis_Information;
    }

    public void setDiagnosis_Information(String diagnosis_Information) {
        Diagnosis_Information = diagnosis_Information;
    }

    public String getExaminations() {
        return Examinations;
    }

    public void setExaminations(String examinations) {
        Examinations = examinations;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getRx() {
        return Rx;
    }

    public void setRx(String rx) {
        Rx = rx;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
