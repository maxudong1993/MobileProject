package com.example.project.data.model;

public class Product {
    private Long id;
    private String name;
    private String description;
    private String picture;
    private String price;
    private int num_remain;
    private String location;
    private String address;
    private int num_like;
    private int is_personal;
    private String shop_name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public String getPrice() {
        return price;
    }

    public int getNum_remain() {
        return num_remain;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public int getNum_like() {
        return num_like;
    }

    public int getIs_personal() {
        return is_personal;
    }

    public String getShop_name() {
        return shop_name;
    }
}
