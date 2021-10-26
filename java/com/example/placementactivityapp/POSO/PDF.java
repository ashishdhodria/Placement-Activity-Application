package com.example.placementactivityapp.POSO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PDF {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("pdf")
    @Expose
    private String pdf;

    @SerializedName("t")
    @Expose
    private long t;

    @SerializedName("adkey")
    @Expose
    private String adkey;

    public PDF(String title, String pdf,long t, String email,String adkey){
        this.title = title;
        this.adkey = adkey;
        this.pdf = pdf;
        this.t = t;
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public String getTitle(){
        return title;
    }
    public String getPdf(){
        return pdf;
    }
    public String getAdkey(){
        return adkey;
    }

}
