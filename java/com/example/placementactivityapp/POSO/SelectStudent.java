package com.example.placementactivityapp.POSO;

public class SelectStudent {
    String email, tkey,adkey, cemail;
    public SelectStudent(String email, String tkey, String adkey, String cemail){
        this.email = email;
        this.adkey = adkey;
        this.cemail =cemail;
        this.tkey = tkey;
    }

    public String getEmail(){
        return  email;
    }
    public String getTkey(){
        return  tkey;
    }
    public String getAdkey(){
        return adkey;
    }
    public String getCemail(){
        return cemail;
    }
}
