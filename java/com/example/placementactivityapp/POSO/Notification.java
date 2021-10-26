package com.example.placementactivityapp.POSO;

public class Notification {
    String title,description;
    public Notification(String title, String description){
        this.title = title;
        this.description = description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getTitle(){
        return title;
    }
    public String getDescription(){return description;
    }
}
