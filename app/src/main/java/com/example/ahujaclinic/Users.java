package com.example.ahujaclinic;

public class Users {
    private String name,email, status, User_Type;

    public Users() {
    }

    public Users(String name, String email, String status, String User_Type) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.User_Type = User_Type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getuser_type() {
        return User_Type;
    }

    public void setuser_type(String user_Type) {
        this.User_Type = user_Type;
    }
}
