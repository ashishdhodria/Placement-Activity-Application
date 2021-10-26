package com.example.placementactivityapp.POSO;

public class Company {
    String name, email, location, industry, position, requirement;

    public Company(String name, String email, String location, String industry, String position, String requirement){
        this.name = name;
        this.email = email;
        this.location = location;
        this.industry = industry;
        this.position = position;
        this.requirement = requirement;
    }

    public String getName(){
        return  name;
    }
    public String getEmail(){
        return email;
    }
    public String getLocation(){
        return location;
    }
    public String getIndustry(){
        return industry;
    }
    public String getPosition(){
        return position;
    }
    public String getRequirement(){
        return requirement;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setIndustry(String industry){
        this.industry = industry;
    }
    public void setPosition(String position){
        this.position = position;
    }
    public void setRequirement(String requirement){
        this.requirement = requirement;
    }
}
