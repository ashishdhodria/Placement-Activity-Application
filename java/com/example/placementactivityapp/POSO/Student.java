package com.example.placementactivityapp.POSO;

public class Student {
    private String name, id, email, branch, cgpa, password, adkey;

    public Student(String name, String id, String email, String branch, String cgpa, String password, String adkey) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.branch = branch;
        this.cgpa = cgpa;
        this.password = password;
        this.adkey = adkey;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public  void setEmail(String email) {
        this.email = email;
    }
    public void  setBranch(String branch) {
        this.branch = branch;
    }
    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
    public void setPassword(String password) {
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
    public String getBranch() {
        return branch;
    }
    public String getCgpa() {
        return cgpa;
    }
    public String getPassword() {
        return password;
    }
    public String getAdkey() {
        return adkey;
    }
}
