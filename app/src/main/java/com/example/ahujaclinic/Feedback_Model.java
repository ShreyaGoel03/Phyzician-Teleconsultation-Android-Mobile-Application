package com.example.ahujaclinic;

public class Feedback_Model {
    private float rating;
    private String feedback;

    public Feedback_Model() {
    }

    public Feedback_Model(float rating, String feedback) {
        this.rating = rating;
        this.feedback = feedback;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
