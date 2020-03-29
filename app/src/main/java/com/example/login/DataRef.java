package com.example.login;

public class DataRef {
    public String name;
    public String roll;
    public String codename;
    public String email;
    public String reason;
    public String time;
    public String lectruAssigned;
    public Boolean acceptance;


    public DataRef(){}
    public DataRef(String name, String roll, String codename, String reason, String time  , Boolean acceptance,String email) {
        this.name = name;
        this.roll = roll;
        this.codename = codename;
        this.reason = reason;
        this.time = time;
        this.acceptance = acceptance;
        this.email = email;
    }


    public DataRef(String name, String roll, String codename , String email) {
        this.name = name;
        this.roll = roll;
        this.codename = codename;
        this.email = email;
    }
}
