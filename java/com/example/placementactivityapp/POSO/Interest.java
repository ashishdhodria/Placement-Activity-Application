package com.example.placementactivityapp.POSO;

public class Interest {
    String sname, semail,scompany,cemail,position;

    public Interest(String sname, String semail, String scompany, String cemail, String position){
        this.cemail  = cemail;
        this.position = position;
        this.scompany = scompany;
        this.semail = semail;
        this.sname = sname;
    }

    public String getSname(){
        return sname;
    }
    public String getSemail(){
        return semail;
    }
    public String getScompany(){
        return scompany;
    }
    public String getCemail(){
        return cemail;
    }
    public String  getPositon(){
        return position;
    }
}
