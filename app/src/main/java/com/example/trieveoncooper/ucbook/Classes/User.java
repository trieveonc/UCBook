package com.example.trieveoncooper.ucbook.Classes;

/**
 * Created by trieveoncooper on 11/14/17.
 */

public class User {
    private String email;
    private String password;
    private String name;
    private String year;
    private String bio;
    private boolean firstLogin = true;
    private User(){

    }
    public User(String email,String password){
        this.email = email;
        this.password = password;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public String year(){
        return year;
    }
    public void setFirstLogin(){
        firstLogin = true;
    }
    public void setName(String s){
        name = s;
    }
    public void setBio(String s){
        bio = s;
    }
    public boolean isFirstLogin(){
        return firstLogin;
    }
    public String getBio(){
        return bio;
    }

}
