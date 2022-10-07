package com.example.trafficviolation.admin.category;

public class AdminCategoryItem {
    private String name;
    private int price, id,edit;

    public AdminCategoryItem(String name,int price,int id,int edit){
        this.name = name;
        this.price = price;
        this.id = id;
        this.edit = edit;
    }

    public String getCategoryName() {
        return name;
    }

    public int getCategoryPrice() {
        return price;
    }

    public int getCategoryId() { return id; }
    public int getCategoryEdit() { return edit; }

}
