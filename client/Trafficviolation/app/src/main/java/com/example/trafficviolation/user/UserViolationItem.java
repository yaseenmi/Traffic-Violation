package com.example.trafficviolation.user;

import android.widget.Button;

public class UserViolationItem {

    private String category;
    private String location;
    private String date;
    private int price;
    private int button;

    public UserViolationItem(String category, String location, String date, int price,int button){
        this.category = category;
        this.price = price;
        this.date = date;
        this.location = location;
        this.button=button;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public int getButton(){return button;}
}
