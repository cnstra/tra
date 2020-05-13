package com.example.testfirebase.Model;

public class User {
    private String Name;
    private String Pass;

    public User(String name, String pass) {
        Name = name;
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
