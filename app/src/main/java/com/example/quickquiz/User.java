package com.example.quickquiz;

public class User {
    private String username;
    private String password;

    public String GetUsername(){
        return username;
    }
    public String GetPassword(){
        return password;
    }
    public void SetUsername(String username){
        this.username=username;
    }
    public void SetPassword(String password){
        this.password=password;
    }
}
