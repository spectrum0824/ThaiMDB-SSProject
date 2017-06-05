package com.sourcey.materiallogindemo;

/**
 * Created by spectrum on 5/31/2017 AD.
 */

public class User {

    public String id;
    public String name;
    public String password;
    public long relia;


    public long getRelia() {
        return relia;
    }

    public void setRelia(int relia) {
        this.relia = relia;
    }

    public User(String id, String name, String password, long relia) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.relia = relia;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

