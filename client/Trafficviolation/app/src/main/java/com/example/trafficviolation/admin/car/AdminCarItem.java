package com.example.trafficviolation.admin.car;

public class AdminCarItem {
    private String brand, code, model, username;

    public AdminCarItem(String brand,String code,String model, String username){
        this.brand = brand;
        this.code = code;
        this.model = model;
        this.username = username;
    }

    public String getBrand() {
        return brand;
    }

    public String getCode() {
        return code;
    }

    public String getModel() {
        return model;
    }

    public String getUsername() {
        return username;
    }
}
