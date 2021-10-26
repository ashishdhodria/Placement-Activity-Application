package com.example.placementactivityapp.POSO;

public class Admin {
    private String username, email, password;

    public Admin(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
