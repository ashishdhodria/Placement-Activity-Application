package com.example.placementactivityapp.POSO;

public class Tpo {

    private String name, id, email, password, adkey;

    public Tpo(String name ,String id, String email, String password, String adkey){
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.adkey = adkey;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setAdkey(String adkey) {
        this.adkey = adkey;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getAdkey() {
        return adkey;
    }

}
